package me.lucko.extracontexts.calculators;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import me.lucko.luckperms.api.context.ContextCalculator;
import me.lucko.luckperms.api.context.MutableContextSet;

import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class WorldGuardCalculator implements ContextCalculator<Player> {
    private static final String KEY = "wg-region";

    @Nonnull
    @Override
    public MutableContextSet giveApplicableContext(@Nonnull Player subject, @Nonnull MutableContextSet accumulator) {
        if (subject.getWorld() == null) {
            return accumulator;
        }

        RegionManager regionManager = WGBukkit.getPlugin().getRegionManager(subject.getWorld());
        if (regionManager == null) {
            return accumulator;
        }

        ApplicableRegionSet ret = regionManager.getApplicableRegions(subject.getLocation());
        for (ProtectedRegion region : ret) {
            accumulator.add(KEY, region.getId());
        }

        return accumulator;
    }

}
