package me.lucko.extracontexts.calculators;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownBlock;
import net.luckperms.api.context.ContextCalculator;
import net.luckperms.api.context.ContextConsumer;
import net.luckperms.api.context.ContextSet;
import net.luckperms.api.context.ImmutableContextSet;
import org.bukkit.entity.Player;

public class TownyNationCalculator implements ContextCalculator<Player> {
    private static final String IN_NATION_KEY = "towny:in-nation";
    private static final String NATION_KEY = "towny:nation";

    private final TownyAPI towny = TownyAPI.getInstance();

    @Override
    public void calculate(Player player, ContextConsumer contextConsumer) {
        final TownBlock townBlock = towny.getTownBlock(player.getLocation());
        if (townBlock == null) {
            contextConsumer.accept(IN_NATION_KEY, "false");
        } else {
            if (townBlock.hasTown()) {
                try {
                    final Town town = townBlock.getTown();
                    if (town.hasNation()) {
                        contextConsumer.accept(IN_NATION_KEY, "true");
                        try {
                            contextConsumer.accept(NATION_KEY, town.getNation().getName());
                        } catch (NotRegisteredException e) {
                            e.printStackTrace();
                        }
                    } else {
                        contextConsumer.accept(IN_NATION_KEY, "false");
                    }
                } catch (NotRegisteredException e) {
                    e.printStackTrace();
                }
            } else {
                contextConsumer.accept(IN_NATION_KEY, "false");
            }
        }
    }

    @Override
    public ContextSet estimatePotentialContexts() {
        final ImmutableContextSet.Builder builder = ImmutableContextSet.builder();
        for (String nationKey : towny.getDataSource().getNationsKeys()) {
            builder.add(NATION_KEY, nationKey);
        }
        builder.add(IN_NATION_KEY, "true");
        builder.add(IN_NATION_KEY, "false");
        return builder.build();
    }
}
