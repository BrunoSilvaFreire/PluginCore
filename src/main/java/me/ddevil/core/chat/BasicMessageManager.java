/*
 * Copyright (C) 2016 Selma
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY{} without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package me.ddevil.core.chat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.ddevil.core.CustomPlugin;
import me.ddevil.core.misc.Manager;
import me.ddevil.core.misc.ColorDesign;
import me.ddevil.core.utils.DebugLevel;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

/**
 * REMEMBER TO INITIALIZE THE VARIABLES IN THIS!
 *
 * @author Selma
 */
public abstract class BasicMessageManager implements MessageManager {

    //Global Messages
    public static String pluginPrefix;
    public static String header;
    public static String messageSeparator;
    //Colors
    private final ColorDesign colorDesign;
    private final CustomPlugin plugin;

    public BasicMessageManager(CustomPlugin plugin) {
        this.colorDesign = plugin.colorDesign;
        this.plugin = plugin;
    }

    @Override
    public Manager setup() {
        //Colors
        plugin.debug("Loading colors...", DebugLevel.NO_BIG_DEAL);
        plugin.debug(new String[]{
                "Colors set to:",
                colorDesign.toString()
        }, DebugLevel.NO_BIG_DEAL);
        //Global Messages
        FileConfiguration messagesConfig = plugin.pluginConfig;
        plugin.debug("Loading basic messages...", DebugLevel.NO_BIG_DEAL);
        messageSeparator = translateColors(messagesConfig.getString("messages.messageSeparator"));
        pluginPrefix = translateColors(messagesConfig.getString("messages.messagePrefix"));
        header = translateAll(messagesConfig.getString("messages.header"));
        plugin.debug("Messages loaded!", DebugLevel.NO_BIG_DEAL);
        plugin.debug(new String[]{
                "Basic messages:",
                "messageSeparator: " + messageSeparator,
                "pluginPrefix: " + pluginPrefix,
                "header: " + header}, DebugLevel.NO_BIG_DEAL);
        postSetup();
        return this;
    }

    public abstract void postSetup();

    @Override
    public boolean isValidColor(char c) {
        return c == '1' || c == '2' || c == '3' || c == '4';
    }

    @Override
    public char getColor(int i) {
        plugin.debug("Getting color for number " + i + "...");
        switch (i) {
            case 1:
                return colorDesign.primaryColor;
            case 2:
                return colorDesign.secondaryColor;
            case 3:
                return colorDesign.neutralColor;
            case 4:
                return colorDesign.warningColor;
            default:
                throw new IllegalArgumentException("Color identifier must be between 1 and 4");
        }
    }

    @Override
    public String translateColors(String input) {
        plugin.debug("Translating colors for message \"" + input + "\"");
        char[] b = input.toCharArray();
        //Iterate
        for (int i = 0; i < b.length - 1; i++) {
            /* Check if current character is $ and if the next
             character is a valid color
             */
            if (b[i] == '$' && isValidColor(b[i + 1])) {
                plugin.debug("Character " + b[i] + " and " + b[i + 1] + " are replacable!");
                b[i] = ChatColor.COLOR_CHAR;
                b[i + 1] = getColor(Character.getNumericValue(b[i + 1]));
                plugin.debug("Current status: " + Arrays.toString(b));
            }
        }
        return ChatColor.translateAlternateColorCodes(ColorDesign.REPLACE_COLOR_CHAR, new String(b));
    }

    @Override
    public String translateAll(String input) {
        return translateColors(translateTags(input));
    }

    @Override
    public List<String> translateTags(Iterable<String> input) {
        ArrayList<String> results = new ArrayList();
        for (String input1 : input) {
            results.add(translateTags(input1));
        }
        return results;
    }

    @Override

    public List<String> translateColors(Iterable<String> input) {
        ArrayList<String> results = new ArrayList();
        for (String input1 : input) {
            results.add(translateColors(input1));
        }
        return results;
    }

    @Override
    public List<String> translateAll(Iterable<String> input) {
        ArrayList<String> results = new ArrayList();
        for (String input1 : input) {
            results.add(translateAll(input1));
        }
        return results;
    }

    @Override
    public String getPluginPrefix() {
        return pluginPrefix;

    }

    @Override
    public void sendMessage(Player p, String... messages) {
        for (String s : messages) {
            sendMessage(p, s);
        }
    }

    @Override
    public String getMessageSeparator() {
        return messageSeparator;
    }
    @Override
    public String translateTags(String input) {
        input = input.replace("%prefix%", pluginPrefix);
        input = input.replace("%header%", header);
        return input;
    }
}
