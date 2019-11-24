package me.lucko.extracontexts.calculators;

import net.luckperms.api.context.ContextCalculator;
import net.luckperms.api.context.ContextConsumer;

import org.bukkit.World;
import org.bukkit.entity.Player;

public class DimensionCalculator implements ContextCalculator<Player> {
    private static final String KEY = "dimension";

    @Override
    public void calculate(Player player, ContextConsumer contextConsumer) {
        World world = player.getWorld();
        if (world != null) {
            contextConsumer.accept(KEY, world.getEnvironment().name().toLowerCase());
        }
    }
}
