package me.lucko.extracontexts.calculators;

import me.lucko.luckperms.api.context.ContextCalculator;
import me.lucko.luckperms.api.context.MutableContextSet;

import org.bukkit.World;
import org.bukkit.entity.Player;

public class DimensionCalculator implements ContextCalculator<Player> {
    private static final String KEY = "dimension";

    @Override
    public MutableContextSet giveApplicableContext(Player subject, MutableContextSet accumulator) {
        World world = subject.getWorld();
        if (world != null) {
            accumulator.add(KEY, world.getEnvironment().name().toLowerCase());
        }
        return accumulator;
    }
}
