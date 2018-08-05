package me.lucko.extracontexts;

import me.lucko.extracontexts.calculators.DimensionCalculator;
import me.lucko.extracontexts.calculators.GamemodeCalculator;
import me.lucko.extracontexts.calculators.WorldGuardCalculator;
import me.lucko.luckperms.api.LuckPermsApi;
import me.lucko.luckperms.api.context.ContextCalculator;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.Supplier;

public class ExtraContextsPlugin extends JavaPlugin {
    private LuckPermsApi luckPerms;

    @Override
    public void onEnable() {
        this.luckPerms = getServer().getServicesManager().load(LuckPermsApi.class);
        if (this.luckPerms == null) {
            throw new IllegalStateException("LuckPerms API not loaded.");
        }

        saveDefaultConfig();

        register("worldguard", "WorldGuard", WorldGuardCalculator::new);
        register("gamemode", null, GamemodeCalculator::new);
        register("dimension", null, DimensionCalculator::new);
    }

    private void register(String option, String requiredPlugin, Supplier<ContextCalculator<Player>> calculatorSupplier) {
        if (getConfig().getBoolean(option, false)) {
            if (requiredPlugin != null && getServer().getPluginManager().getPlugin(requiredPlugin) == null) {
                getLogger().info(requiredPlugin + " not present. Skipping registration of '" + option + "'...");
            } else {
                getLogger().info("Registering '" + option + "' calculator.");
                this.luckPerms.registerContextCalculator(calculatorSupplier.get());
            }
        }
    }

}
