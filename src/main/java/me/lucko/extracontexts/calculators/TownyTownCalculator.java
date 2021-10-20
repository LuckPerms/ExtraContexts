package me.lucko.extracontexts.calculators;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.TownBlock;
import net.luckperms.api.context.ContextCalculator;
import net.luckperms.api.context.ContextConsumer;
import net.luckperms.api.context.ContextSet;
import net.luckperms.api.context.ImmutableContextSet;
import org.bukkit.entity.Player;

public class TownyTownCalculator implements ContextCalculator<Player> {
    private static final String IN_TOWN_KEY = "towny:in-town";
    private static final String TOWN_KEY = "towny:town";

    private final TownyAPI towny = TownyAPI.getInstance();

    @Override
    public void calculate(Player player, ContextConsumer contextConsumer) {
        final TownBlock townBlock = towny.getTownBlock(player.getLocation());
        if (townBlock == null) {
            contextConsumer.accept(IN_TOWN_KEY, "false");
        } else {
            if (townBlock.hasTown()) {
                contextConsumer.accept(IN_TOWN_KEY, "true");
                try {
                    contextConsumer.accept(TOWN_KEY, townBlock.getTown().getName());
                } catch (NotRegisteredException e) {
                    e.printStackTrace();
                }
            } else {
                contextConsumer.accept(IN_TOWN_KEY, "false");
            }
        }
    }

    @Override
    public ContextSet estimatePotentialContexts() {
        final ImmutableContextSet.Builder builder = ImmutableContextSet.builder();
        for (String townKey : towny.getDataSource().getTownsKeys()) {
            builder.add(TOWN_KEY, townKey);
        }
        builder.add(IN_TOWN_KEY, "true");
        builder.add(IN_TOWN_KEY, "false");
        return builder.build();
    }
}
