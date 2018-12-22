package me.lucko.extracontexts.calculators;

import com.google.common.collect.ImmutableMap;

import me.clip.placeholderapi.PlaceholderAPI;
import me.lucko.luckperms.api.context.ContextCalculator;
import me.lucko.luckperms.api.context.MutableContextSet;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.Map;

public class PlaceholderApiCalculator implements ContextCalculator<Player> {

    private final Map<String, String> placeholders;

    public PlaceholderApiCalculator(ConfigurationSection placeholders) {
        ImmutableMap.Builder<String, String> map = ImmutableMap.builder();
        for (String key : placeholders.getKeys(false)) {
            map.put(key, placeholders.getString(key));
        }
        this.placeholders = map.build();
    }

    @Override
    public MutableContextSet giveApplicableContext(Player subject, MutableContextSet accumulator) {
        for (Map.Entry<String, String> placeholder : this.placeholders.entrySet()) {
            String result = PlaceholderAPI.setPlaceholders(subject, placeholder.getValue());
            if (result == null || result.trim().isEmpty()) {
                continue;
            }
            accumulator.add(placeholder.getKey(), result);
        }
        return accumulator;
    }
}
