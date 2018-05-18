package me.lucko.extracontexts.calculators;

import me.lucko.luckperms.api.context.ContextCalculator;
import me.lucko.luckperms.api.context.MutableContextSet;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class GamemodeCalculator implements ContextCalculator<Player> {
    private static final String KEY = "gamemode";

    @Nonnull
    @Override
    public MutableContextSet giveApplicableContext(@Nonnull Player subject, @Nonnull MutableContextSet accumulator) {
        GameMode gm = subject.getGameMode();
        if (gm != null) {
            accumulator.add(KEY, gm.name().toLowerCase());
        }
        return accumulator;
    }
}
