package me.lucko.extracontexts;

import me.lucko.extracontexts.calculators.DimensionCalculator;
import me.lucko.extracontexts.calculators.GamemodeCalculator;
import me.lucko.extracontexts.calculators.HasPlayedBeforeCalculator;
import me.lucko.extracontexts.calculators.PlaceholderApiCalculator;
import me.lucko.extracontexts.calculators.TeamCalculator;
import me.lucko.extracontexts.calculators.WhitelistedCalculator;
import me.lucko.extracontexts.calculators.WorldGuardFlagCalculator;
import me.lucko.extracontexts.calculators.WorldGuardRegionCalculator;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.context.ContextCalculator;
import net.luckperms.api.context.ContextManager;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ExtraContextsPlugin extends JavaPlugin implements CommandExecutor {
    private ContextManager contextManager;
    private final List<ContextCalculator<Player>> registeredCalculators = new ArrayList<>();

    @Override
    public void onEnable() {
        LuckPerms luckPerms = getServer().getServicesManager().load(LuckPerms.class);
        if (luckPerms == null) {
            throw new IllegalStateException("LuckPerms API not loaded.");
        }
        this.contextManager = luckPerms.getContextManager();

        saveDefaultConfig();
        setup();
    }

    @Override
    public void onDisable() {
        unregisterAll();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        unregisterAll();
        reloadConfig();
        setup();
        sender.sendMessage(ChatColor.GREEN + "ExtraContexts configuration reloaded.");
        return true;
    }

    private void setup() {
        register("worldguard-region", "WorldGuard", WorldGuardRegionCalculator::new);
        register("worldguard-flag", "WorldGuard", WorldGuardFlagCalculator::new);
        register("gamemode", null, GamemodeCalculator::new);
        register("whitelisted", null, WhitelistedCalculator::new);
        register("dimension", null, DimensionCalculator::new);
        register("team", null, TeamCalculator::new);
        register("has-played-before", null, HasPlayedBeforeCalculator::new);
        register("placeholderapi", "PlaceholderAPI", () -> new PlaceholderApiCalculator(getConfig().getConfigurationSection("placeholderapi-placeholders")));
    }

    private void register(String option, String requiredPlugin, Supplier<ContextCalculator<Player>> calculatorSupplier) {
        if (getConfig().getBoolean(option, false)) {
            if (requiredPlugin != null && getServer().getPluginManager().getPlugin(requiredPlugin) == null) {
                getLogger().info(requiredPlugin + " not present. Skipping registration of '" + option + "'...");
            } else {
                getLogger().info("Registering '" + option + "' calculator.");
                ContextCalculator<Player> calculator = calculatorSupplier.get();
                this.contextManager.registerCalculator(calculator);
                this.registeredCalculators.add(calculator);
            }
        }
    }

    private void unregisterAll() {
        this.registeredCalculators.forEach(c -> this.contextManager.unregisterCalculator(c));
        this.registeredCalculators.clear();
    }

}
