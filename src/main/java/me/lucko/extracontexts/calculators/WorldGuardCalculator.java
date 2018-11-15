package me.lucko.extracontexts.calculators;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import me.lucko.extracontexts.util.ThrowingSupplier;
import me.lucko.luckperms.api.context.ContextCalculator;
import me.lucko.luckperms.api.context.MutableContextSet;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Function;

public class WorldGuardCalculator implements ContextCalculator<Player> {
    private static final String KEY = "wg-region";

    private static final Function<World, RegionManager> REGION_MANAGER_FUNCTION = ThrowingSupplier.<Function<World, RegionManager>>of(() -> {
        // worldguard v6
        Class<?> worldGuardPluginClass = Class.forName("com.sk89q.worldguard.bukkit.WorldGuardPlugin");

        Method getRegionManagerMethod = worldGuardPluginClass.getMethod("getRegionManager", World.class);
        Method getInstanceMethod = worldGuardPluginClass.getMethod("inst");

        return world -> {
            try {
                Object plugin = getInstanceMethod.invoke(null);
                return (RegionManager) getRegionManagerMethod.invoke(plugin, world);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        };
    }).or(() -> {
        // worldguard v7
        Class<?> worldGuardClass = Class.forName("com.sk89q.worldguard.WorldGuard");
        Class<?> worldGuardPlatformClass = Class.forName("com.sk89q.worldguard.internal.platform.WorldGuardPlatform");
        Class<?> regionContainerClass = Class.forName("com.sk89q.worldguard.protection.regions.RegionContainer");
        Class<?> bukkitAdapterClass = Class.forName("com.sk89q.worldedit.bukkit.BukkitAdapter");

        Method getInstanceMethod = worldGuardClass.getMethod("getInstance");
        Method getPlatformMethod = worldGuardClass.getMethod("getPlatform");
        Method getRegionContainerMethod = worldGuardPlatformClass.getMethod("getRegionContainer");
        Method regionContainerGetMethod = regionContainerClass.getMethod("get", com.sk89q.worldedit.world.World.class);
        Method worldAdaptMethod = bukkitAdapterClass.getMethod("adapt", org.bukkit.World.class);

        return world -> {
            try {
                Object instance = getInstanceMethod.invoke(null);
                Object platform = getPlatformMethod.invoke(instance);
                Object regionContainer = getRegionContainerMethod.invoke(platform);
                Object adaptedWorld = worldAdaptMethod.invoke(null, world);
                return (RegionManager) regionContainerGetMethod.invoke(regionContainer, adaptedWorld);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        };
    }).get();

    @Override
    public MutableContextSet giveApplicableContext(Player subject, MutableContextSet accumulator) {
        World world = subject.getWorld();
        if (world == null) {
            return accumulator;
        }

        RegionManager regionManager = REGION_MANAGER_FUNCTION.apply(world);
        if (regionManager == null) {
            return accumulator;
        }

        Location location = subject.getLocation();
        ApplicableRegionSet ret = regionManager.getApplicableRegions(new Vector(location.getX(), location.getY(), location.getZ()));
        for (ProtectedRegion region : ret) {
            accumulator.add(KEY, region.getId());
        }
        return accumulator;
    }

}
