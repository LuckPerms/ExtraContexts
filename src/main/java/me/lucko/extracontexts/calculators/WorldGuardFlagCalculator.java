package me.lucko.extracontexts.calculators;

import net.luckperms.api.context.ContextCalculator;
import net.luckperms.api.context.ContextConsumer;
import net.luckperms.api.context.ContextSet;
import net.luckperms.api.context.ImmutableContextSet;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.codemc.worldguardwrapper.WorldGuardWrapper;
import org.codemc.worldguardwrapper.flag.IWrappedFlag;
import org.codemc.worldguardwrapper.flag.IWrappedStatusFlag;
import org.codemc.worldguardwrapper.flag.WrappedState;
import org.codemc.worldguardwrapper.region.IWrappedRegion;

import java.util.Map;

public class WorldGuardFlagCalculator implements ContextCalculator<Player> {
    private static final String KEY = "worldguard:flag-";

    private final WorldGuardWrapper worldGuard = WorldGuardWrapper.getInstance();

    @Override
    public void calculate(Player target, ContextConsumer consumer) {
        Map<IWrappedFlag<?>, Object> flags = this.worldGuard.queryApplicableFlags(target, target.getLocation());
        flags.forEach((flag, value) -> {
            if (invalidValue(value)) {
                return;
            }
            consumer.accept(KEY + flag.getName(), value.toString());
        });
    }

    @Override
    public ContextSet estimatePotentialContexts() {
        ImmutableContextSet.Builder builder = ImmutableContextSet.builder();
        for (World world : Bukkit.getWorlds()) {
            for (IWrappedRegion region : this.worldGuard.getRegions(world).values()) {
                Map<IWrappedFlag<?>, Object> flags = region.getFlags();
                flags.forEach((flag, value) -> {
                    if (flag instanceof IWrappedStatusFlag) {
                        for (WrappedState state : WrappedState.values()) {
                            builder.add(KEY + flag.getName(), state.toString());
                        }
                    } else {
                        if (!invalidValue(value)) {
                            builder.add(KEY + flag.getName(), value.toString());
                        }

                        Object defaultValue = flag.getDefaultValue().orElse(null);
                        if (!invalidValue(defaultValue)) {
                            builder.add(KEY + flag.getName(), defaultValue.toString());
                        }
                    }
                });
            }
        }
        return builder.build();
    }

    private static boolean invalidValue(Object value) {
        return value == null || value instanceof Location || value instanceof Vector;
    }

}
