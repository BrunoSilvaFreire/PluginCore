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
import java.util.List;
import me.ddevil.core.CustomPlugin;
import me.ddevil.core.CustomListener;
import org.bukkit.ChatColor;

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
    public CustomListener setup() {
        //Colors
        primaryColor
                = CustomPlugin.instance.
                getMessagesConfig()
                .getString("messages.primaryColor");
        secondaryColor = CustomPlugin.instance.getMessagesConfig().getString("messages.secondaryColor");
        neutralColor = CustomPlugin.instance.getMessagesConfig().getString("messages.neutralColor");
        warningColor = CustomPlugin.instance.getMessagesConfig().getString("messages.warningColor");
        CustomPlugin.instance.debug(new String[]{
            "Colors set to:",
            "Primary: " + primaryColor,
            "Secondary: " + secondaryColor,
            "Neutral: " + neutralColor,
            "Warning: " + warningColor,
            "Colors loaded!"});
        //Global Messages
        messageSeparator = translateColors(CustomPlugin.instance.getMessagesConfig().getString("messages.messageSeparator"));
        pluginPrefix = translateColors(CustomPlugin.instance.getMessagesConfig().getString("messages.messagePrefix"));
        header = translateAll(CustomPlugin.instance.getMessagesConfig().getString("messages.header"));
        postSetup();
        return this;
    }

    public abstract void postSetup();

    private static boolean isValidNumber(char c) {
        return c == '1' || c == '2' || c == '3' || c == '4';
    }

    public static String getColor(int i) {
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
    public String translateColors(String trans) {
        char[] b = trans.toCharArray();
        for (int i = 0; i < b.length - 1; i++) {
            if (b[i] == '$' && isValidNumber(b[i + 1])) {
                int a = Character.getNumericValue(b[i + 1]);
                String s = getColor(a);
                if (s != null) {
                    b[i] = ChatColor.COLOR_CHAR;
                    b[i + 1] = s.charAt(0);
                } else {
                    CustomPlugin.instance.debug("Message \"" + trans + "\" is badly color coded! Remeber to only use $1 to $4 !");
                }
            }
        }
        return ChatColor.translateAlternateColorCodes(colorChar, new String(b));
    }

    @Override
    public String translateAll(String input) {
        return translateTags(translateColors(input));
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
