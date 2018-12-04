package me.lucko.extracontexts.calculators;

import me.lucko.luckperms.api.context.ContextCalculator;
import me.lucko.luckperms.api.context.MutableContextSet;

import org.bukkit.entity.Player;
import org.codemc.worldguardwrapper.WorldGuardWrapper;
import org.codemc.worldguardwrapper.region.IWrappedRegion;

import java.util.Set;

public class WorldGuardCalculator implements ContextCalculator<Player> {
    private static final String KEY = "wg-region";

    private final WorldGuardWrapper worldGuard = WorldGuardWrapper.getInstance();

    @Override
    public MutableContextSet giveApplicableContext(Player subject, MutableContextSet accumulator) {
        Set<IWrappedRegion> regions = this.worldGuard.getRegions(subject.getLocation());
        for (IWrappedRegion region : regions) {
            accumulator.add(KEY, region.getId());
        }
        return accumulator;
    }

}
