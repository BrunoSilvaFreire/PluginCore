/*
 * Copyright (C) 2016 Selma
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package me.ddevil.core.chat;

import java.util.List;
import me.ddevil.core.CustomPlugin;
import org.bukkit.entity.Player;

/**
 *
 * @author Selma
 */
public abstract class BasicChatManager implements ChatManager {

    @Override
    public void sendMessage(Player p, String string) {
        p.sendMessage(CustomPlugin.messageManager.getPluginPrefix() + CustomPlugin.messageManager.getMessageSeparator() + string);
    }

    @Override
    public void sendMessage(Player p, String[] messages) {
        for (String usageMessage : messages) {
            sendMessage(p, usageMessage);
        }
    }

    @Override
    public void sendMessage(Player p, List<String> messages) {
        for (String usageMessage : messages) {
            sendMessage(p, usageMessage);
        }
    }

    public static String secondsToString(int pTime) {
        return String.format("%02d:%02d", pTime / 60, pTime % 60);
    }
}