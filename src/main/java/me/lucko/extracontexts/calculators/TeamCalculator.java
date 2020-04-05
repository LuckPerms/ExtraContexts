package me.lucko.extracontexts.calculators;

import net.luckperms.api.context.ContextCalculator;
import net.luckperms.api.context.ContextConsumer;
import net.luckperms.api.context.ContextSet;
import net.luckperms.api.context.ImmutableContextSet;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

public class TeamCalculator implements ContextCalculator<Player> {
    private static final String KEY = "team";

    @Override
    public void calculate(Player target, ContextConsumer consumer) {
        Team team = Bukkit.getScoreboardManager().getMainScoreboard().getTeam(target.getName());
        if (team != null) {
            consumer.accept(KEY, team.getName());
        }
    }

    @Override
    public ContextSet estimatePotentialContexts() {
        ImmutableContextSet.Builder builder = ImmutableContextSet.builder();
        for (Team team : Bukkit.getScoreboardManager().getMainScoreboard().getTeams()) {
            builder.add(KEY, team.getName());
        }
        return builder.build();
    }

}
