package me.ddevil.core.chat;

import me.ddevil.core.CustomPlugin;
import org.bukkit.entity.Player;

/**
 * Created by BRUNO II on 21/06/2016.
 */
public class PluginMessageManager extends BasicMessageManager {
    public PluginMessageManager(CustomPlugin plugin) {
        super(plugin);
    }

    @Override
    public void postSetup() {

    }

    @Override
    public void sendMessage(Player p, String message) {
        p.sendMessage(getPluginPrefix() + getMessageSeparator() + translateAll(message));
    }
}
