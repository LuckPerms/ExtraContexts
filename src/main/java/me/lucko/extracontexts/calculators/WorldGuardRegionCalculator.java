package me.lucko.extracontexts.calculators;

import net.luckperms.api.context.ContextCalculator;
import net.luckperms.api.context.ContextConsumer;
import net.luckperms.api.context.ContextSet;
import net.luckperms.api.context.ImmutableContextSet;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.codemc.worldguardwrapper.WorldGuardWrapper;
import org.codemc.worldguardwrapper.region.IWrappedRegion;

import java.util.Set;

public class WorldGuardRegionCalculator implements ContextCalculator<Player> {
    private static final String KEY = "worldguard:region";
    private static final String IN_REGION_KEY = "worldguard:in-region";

    private final WorldGuardWrapper worldGuard = WorldGuardWrapper.getInstance();

    @Override
    public void calculate(Player target, ContextConsumer consumer) {
        Set<IWrappedRegion> regions = this.worldGuard.getRegions(target.getLocation());

        if (regions.isEmpty()) {
            consumer.accept(IN_REGION_KEY, "false");
        } else {
            consumer.accept(IN_REGION_KEY, "true");
            for (IWrappedRegion region : regions) {
                consumer.accept(KEY, region.getId());
            }
        }
    }

    @Override
    public ContextSet estimatePotentialContexts() {
        ImmutableContextSet.Builder builder = ImmutableContextSet.builder();
        for (World world : Bukkit.getWorlds()) {
            for (IWrappedRegion region : this.worldGuard.getRegions(world).values()) {
                builder.add(KEY, region.getId());
            }
        }
        builder.add(IN_REGION_KEY, "true");
        builder.add(IN_REGION_KEY, "false");
        return builder.build();
    }

}
