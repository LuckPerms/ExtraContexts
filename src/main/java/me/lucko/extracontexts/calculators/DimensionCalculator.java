package me.lucko.extracontexts.calculators;

import net.luckperms.api.context.ContextCalculator;
import net.luckperms.api.context.ContextConsumer;
import net.luckperms.api.context.ContextSet;
import net.luckperms.api.context.ImmutableContextSet;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class DimensionCalculator implements ContextCalculator<Player> {
    private static final String KEY = "dimension";

    @Override
    public void calculate(Player target, ContextConsumer consumer) {
        World world = target.getWorld();
        if (world != null) {
            consumer.accept(KEY, world.getEnvironment().name().toLowerCase());
        }
    }

    @Override
    public ContextSet estimatePotentialContexts() {
        ImmutableContextSet.Builder builder = ImmutableContextSet.builder();
        for (World.Environment dimension : World.Environment.values()) {
            builder.add(KEY, dimension.name().toLowerCase());
        }
        return builder.build();
    }

}
