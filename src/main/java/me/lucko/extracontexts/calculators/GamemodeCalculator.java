package me.lucko.extracontexts.calculators;

import me.lucko.luckperms.api.context.ContextCalculator;
import me.lucko.luckperms.api.context.MutableContextSet;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class GamemodeCalculator implements ContextCalculator<Player> {
    private static final String KEY = "gamemode";

    @Override
    public MutableContextSet giveApplicableContext(Player subject, MutableContextSet accumulator) {
        GameMode gm = subject.getGameMode();
        if (gm != null) {
            accumulator.add(KEY, gm.name().toLowerCase());
        }
        return accumulator;
    }
}
