package me.lucko.extracontexts.calculators;

import com.palmergames.bukkit.towny.TownyAPI;
import net.luckperms.api.context.ContextCalculator;
import net.luckperms.api.context.ContextConsumer;
import net.luckperms.api.context.ContextSet;
import net.luckperms.api.context.ImmutableContextSet;
import org.bukkit.entity.Player;

public class TownyWildsCalculator implements ContextCalculator<Player> {
    private static final String IN_WILDS_KEY = "towny:in-wilds";

    private final TownyAPI towny = TownyAPI.getInstance();

    @Override
    public void calculate(Player player, ContextConsumer contextConsumer) {
        contextConsumer.accept(IN_WILDS_KEY, towny.isWilderness(player.getLocation()) ? "true" : "false");
    }

    @Override
    public ContextSet estimatePotentialContexts() {
        final ImmutableContextSet.Builder builder = ImmutableContextSet.builder();
        builder.add(IN_WILDS_KEY, "true");
        builder.add(IN_WILDS_KEY, "false");
        return builder.build();
    }
}
