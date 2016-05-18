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
import me.ddevil.core.Manager;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

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
    //Color char
    private static final char colorChar = '&';
    //Colors
    public static String primaryColor;
    public static String secondaryColor;
    public static String neutralColor;
    public static String warningColor;

    @Override
    public Manager setup() {
        //Colors
        CustomPlugin.instance.debug("Loading colors...", 3);
        FileConfiguration messagesConfig = CustomPlugin.instance.getMessagesConfig();
        if (!messagesConfig.contains("colors.primaryColor")) {
            messagesConfig.set("colors.primaryColor", CustomPlugin.defaultColorDesign.getPrimaryColor());
            primaryColor = String.valueOf(CustomPlugin.defaultColorDesign.getPrimaryColor());
        } else {
            primaryColor = messagesConfig.getString("colors.primaryColor");
        }
        if (!messagesConfig.contains("colors.secondaryColor")) {
            messagesConfig.set("colors.secondaryColor", CustomPlugin.defaultColorDesign.getPrimaryColor());
            secondaryColor = String.valueOf(CustomPlugin.defaultColorDesign.getPrimaryColor());
        } else {
            secondaryColor = messagesConfig.getString("colors.secondaryColor");
        }
        if (!messagesConfig.contains("colors.neutralColor")) {
            messagesConfig.set("colors.neutralColor", CustomPlugin.defaultColorDesign.getPrimaryColor());
            neutralColor = String.valueOf(CustomPlugin.defaultColorDesign.getPrimaryColor());
        } else {
            neutralColor = messagesConfig.getString("colors.neutralColor");
        }
        if (!messagesConfig.contains("colors.warningColor")) {
            messagesConfig.set("colors.warningColor", CustomPlugin.defaultColorDesign.getPrimaryColor());
            warningColor = String.valueOf(CustomPlugin.defaultColorDesign.getPrimaryColor());
        } else {
            warningColor = messagesConfig.getString("colors.warningColor");
        }
        CustomPlugin.instance.debug("Colors loaded!", 3);
        CustomPlugin.instance.debug(new String[]{
            "Colors set to:",
            "Primary: " + primaryColor,
            "Secondary: " + secondaryColor,
            "Neutral: " + neutralColor,
            "Warning: " + warningColor
        }, 2);
        //Global Messages
        CustomPlugin.instance.debug("Loading basic messages...", 3);
        messageSeparator = translateColors(messagesConfig.getString("messages.messageSeparator"));
        pluginPrefix = translateColors(messagesConfig.getString("messages.messagePrefix"));
        header = translateAll(messagesConfig.getString("messages.header"));
        CustomPlugin.instance.debug("Messages loaded!", 3);
        CustomPlugin.instance.debug(new String[]{
            "Basic messages:",
            "messageSeparator: " + messageSeparator,
            "pluginPrefix: " + pluginPrefix,
            "header: " + header}, 2);
        postSetup();
        return this;
    }

    public abstract void postSetup();

    protected static boolean isValidColor(char c) {
        return c == '1' || c == '2' || c == '3' || c == '4';
    }

    public static String getColor(int i) {
        CustomPlugin.instance.debug("Getting color for number " + i + "...");
        switch (i) {
            case 1:
                return primaryColor;
            case 2:
                return secondaryColor;
            case 3:
                return neutralColor;
            case 4:
                return warningColor;
            default:
                return null;
        }
    }

    @Override
    public String translateColors(String input) {
        CustomPlugin.instance.debug("Translating colors for message \"" + input + "\"");
        char[] b = input.toCharArray();
        //Iterate
        for (int i = 0; i < b.length - 1; i++) {
            /* Check if current character is $ and if the next
             character is a valid color
             */
            if (b[i] == '$' && isValidColor(b[i + 1])) {
                CustomPlugin.instance.debug("Character " + b[i] + " and " + b[i + 1] + " are replacable!");
                String s = getColor(Character.getNumericValue(b[i + 1]));
                if (s != null) {
                    b[i] = ChatColor.COLOR_CHAR;
                    b[i + 1] = s.charAt(0);
                    CustomPlugin.instance.debug("Current status: " + Arrays.toString(b));
                } else {
                    CustomPlugin.instance.debug("Could not find color for " + b[i] + b[i + 1] + "@ " + input + "! Are you sure you configured everything correctly?", 4);
                }
            }
        }
        return ChatColor.translateAlternateColorCodes(colorChar, new String(b));
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
    public String getMessageSeparator() {
        return messageSeparator;
    }
}
