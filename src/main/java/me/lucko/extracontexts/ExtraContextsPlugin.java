package me.lucko.extracontexts;

import me.lucko.extracontexts.calculators.WorldGuardCalculator;
import me.lucko.luckperms.api.LuckPermsApi;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ExtraContextsPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        LuckPermsApi luckPerms = getServer().getServicesManager().load(LuckPermsApi.class);
        if (luckPerms == null) {
            throw new RuntimeException("LuckPerms API not loaded.");
        }

        saveDefaultConfig();
        FileConfiguration config = getConfig();

        // hook WorldGuard
        if (config.getBoolean("worldguard", false)) {
            if (!getServer().getPluginManager().isPluginEnabled("WorldGuard")) {
                getLogger().info("WorldGuard not present. Skipping...");
            } else {
                getLogger().info("Registering WorldGuard calculator.");
                luckPerms.registerContextCalculator(new WorldGuardCalculator());
            }
        }

    }

}
