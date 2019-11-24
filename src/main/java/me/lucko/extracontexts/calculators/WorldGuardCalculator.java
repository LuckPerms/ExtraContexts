package me.lucko.extracontexts.calculators;

import net.luckperms.api.context.ContextCalculator;
import net.luckperms.api.context.ContextConsumer;

import org.bukkit.entity.Player;
import org.codemc.worldguardwrapper.WorldGuardWrapper;
import org.codemc.worldguardwrapper.region.IWrappedRegion;

import java.util.Set;

public class WorldGuardCalculator implements ContextCalculator<Player> {
    private static final String KEY = "wg-region";

    private final WorldGuardWrapper worldGuard = WorldGuardWrapper.getInstance();

    @Override
    public void calculate(Player player, ContextConsumer contextConsumer) {
        Set<IWrappedRegion> regions = this.worldGuard.getRegions(player.getLocation());
        for (IWrappedRegion region : regions) {
            contextConsumer.accept(KEY, region.getId());
        }
    }

}
