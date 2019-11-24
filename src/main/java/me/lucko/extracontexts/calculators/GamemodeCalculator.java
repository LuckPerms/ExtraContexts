package me.lucko.extracontexts.calculators;

import net.luckperms.api.context.ContextCalculator;
import net.luckperms.api.context.ContextConsumer;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class GamemodeCalculator implements ContextCalculator<Player> {
    private static final String KEY = "gamemode";

    @Override
    public void calculate(Player player, ContextConsumer contextConsumer) {
        GameMode gm = player.getGameMode();
        if (gm != null) {
            contextConsumer.accept(KEY, gm.name().toLowerCase());
        }
    }
}
