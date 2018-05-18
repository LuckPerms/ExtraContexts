package me.lucko.extracontexts.calculators;

import me.lucko.luckperms.api.context.ContextCalculator;
import me.lucko.luckperms.api.context.MutableContextSet;

import org.bukkit.World;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class DimensionCalculator implements ContextCalculator<Player> {
    private static final String KEY = "dimension";

    @Nonnull
    @Override
    public MutableContextSet giveApplicableContext(@Nonnull Player subject, @Nonnull MutableContextSet accumulator) {
        World world = subject.getWorld();
        if (world != null) {
            accumulator.add(KEY, world.getEnvironment().name().toLowerCase());
        }
        return accumulator;
    }
}
