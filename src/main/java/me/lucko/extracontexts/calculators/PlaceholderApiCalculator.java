package me.lucko.extracontexts.calculators;

import com.google.common.collect.ImmutableMap;

import me.clip.placeholderapi.PlaceholderAPI;
import me.lucko.luckperms.api.context.ContextCalculator;
import me.lucko.luckperms.api.context.MutableContextSet;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.Map;

import javax.annotation.Nonnull;

public class PlaceholderApiCalculator implements ContextCalculator<Player> {

    private final Map<String, String> placeholders;

    public PlaceholderApiCalculator(ConfigurationSection placeholders) {
        ImmutableMap.Builder<String, String> map = ImmutableMap.builder();
        for (String key : placeholders.getKeys(false)) {
            map.put(key, placeholders.getString(key));
        }
        this.placeholders = map.build();
    }

    @Nonnull
    @Override
    public MutableContextSet giveApplicableContext(@Nonnull Player subject, @Nonnull MutableContextSet accumulator) {
        for (Map.Entry<String, String> placeholder : this.placeholders.entrySet()) {
            String result = PlaceholderAPI.setPlaceholders(subject, placeholder.getValue());
            accumulator.add(placeholder.getKey(), result);
        }
        return accumulator;
    }
}
