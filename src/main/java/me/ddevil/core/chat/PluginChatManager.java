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

import me.ddevil.core.CustomPlugin;
import me.ddevil.core.Manager;

/**
 *
 * @author Selma
 */
public class PluginChatManager extends BasicChatManager {

    private static PluginChatManager instance;

    public static PluginChatManager getInstance(CustomPlugin plugin) {
        if (instance == null) {
            instance = new PluginChatManager(plugin);
        }
        return instance;
    }
    private final CustomPlugin plugin;

    public PluginChatManager(CustomPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public Manager setup() {
        return this;
    }

}
