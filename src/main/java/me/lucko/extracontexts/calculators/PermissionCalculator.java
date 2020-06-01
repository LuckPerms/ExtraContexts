package me.lucko.extracontexts.calculators;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.cacheddata.CachedPermissionData;
import net.luckperms.api.context.ContextCalculator;
import net.luckperms.api.context.ContextConsumer;
import net.luckperms.api.context.ContextManager;
import net.luckperms.api.model.user.User;
import org.bukkit.entity.Player;

public class PermissionCalculator implements ContextCalculator<Player> {
    private static final String KEY = "permission";

    private final LuckPerms api;

    public PermissionCalculator(LuckPerms api) {
        this.api = api;
    }

    @Override
    public void calculate(Player target, ContextConsumer consumer) {
        User user = api.getPlayerAdapter(Player.class).getUser(target);
        ContextManager contextManager = api.getContextManager();

        CachedPermissionData permissionData = user.getCachedData().getPermissionData(contextManager.getStaticQueryOptions());
        permissionData.getPermissionMap().forEach((node, value) -> {
            if (value && permissionData.checkPermission(node).asBoolean()) consumer.accept(KEY, node);
        });
    }

}
