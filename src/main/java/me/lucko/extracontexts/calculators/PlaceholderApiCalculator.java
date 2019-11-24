package me.lucko.extracontexts.calculators;

import com.google.common.collect.ImmutableMap;
import me.clip.placeholderapi.PlaceholderAPI;
import net.luckperms.api.context.ContextCalculator;
import net.luckperms.api.context.ContextConsumer;
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
    public void calculate(Player target, ContextConsumer consumer) {
        for (Map.Entry<String, String> placeholder : this.placeholders.entrySet()) {
            String result = PlaceholderAPI.setPlaceholders(target, placeholder.getValue());
            if (result == null || result.trim().isEmpty()) {
                continue;
            }
            consumer.accept(placeholder.getKey(), result);
        }
    }

}
