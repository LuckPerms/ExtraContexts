package me.lucko.extracontexts.calculators;

import net.luckperms.api.context.ContextCalculator;
import net.luckperms.api.context.ContextConsumer;
import net.luckperms.api.context.ContextSet;
import net.luckperms.api.context.ImmutableContextSet;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class GamemodeCalculator implements ContextCalculator<Player> {
    private static final String KEY = "gamemode";

    @Override
    public void calculate(Player target, ContextConsumer consumer) {
        GameMode gm = target.getGameMode();
        if (gm != null) {
            consumer.accept(KEY, gm.name().toLowerCase());
        }
    }

    @Override
    public ContextSet estimatePotentialContexts() {
        ImmutableContextSet.Builder builder = ImmutableContextSet.builder();
        for (GameMode gameMode : GameMode.values()) {
            builder.add(KEY, gameMode.name().toLowerCase());
        }
        return builder.build();
    }

}
