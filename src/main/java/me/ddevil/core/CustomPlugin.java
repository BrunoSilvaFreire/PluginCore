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
package me.ddevil.core;

import me.ddevil.core.chat.MessageManager;
import java.io.File;
import java.lang.reflect.Field;
import me.ddevil.core.chat.ChatManager;
import me.ddevil.core.chat.ColorDesign;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class CustomPlugin extends JavaPlugin implements Listener {

    public static CustomPlugin instance;
    protected static CommandMap commandMap;
    public static ChatManager chatManager;
    public static MessageManager messageManager;
    public static ColorDesign defaultColorDesign;
    public int minimumDebugPriotity = 0;
    //Files
    public static File pluginFolder;
    public static File pluginConfigFile;
    private boolean allowBroadcastdebug;

    public abstract String getPluginName();

    public abstract FileConfiguration getMessagesConfig();

    public abstract void reload(Player p);

    public static String getVersion() {
        String name = Bukkit.getServer().getClass().getPackage().getName();
        String version = name.substring(name.lastIndexOf('.') + 1) + ".";
        return version;
    }

    public static Class<?> getNMSClass(String className) {
        String fullName = "net.minecraft.server." + getVersion() + className;
        Class<?> clazz = null;
        try {
            clazz = Class.forName(fullName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clazz;
    }

    public static Class<?> getOBCClass(String className) {
        String fullName = "org.bukkit.craftbukkit." + getVersion() + className;
        Class<?> clazz = null;
        try {
            clazz = Class.forName(fullName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clazz;
    }

    public static FileConfiguration pluginConfig;

    @Override
    public void onEnable() {
        instance = this;
        pluginFolder = getDataFolder();
        if (!pluginFolder.exists()) {
            debug("Plugin folder not found! Making one...", 3);
            pluginFolder.mkdir();
        }
        pluginConfigFile = new File(pluginFolder, "config.yml");
        if (!pluginConfigFile.exists()) {
            //Load from plugin
            debug("Config file not found! Making one...", 3);
            loadResource(pluginConfigFile, "config.yml");
        }
        pluginConfig = YamlConfiguration.loadConfiguration(pluginConfigFile);
        try {
            Class craftServerClass = getOBCClass("CraftServer");
            final Field f = craftServerClass.getDeclaredField("commandMap");
            f.setAccessible(true);
            commandMap = (CommandMap) f.get(Bukkit.getServer());
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    public static void registerCommand(Command cmd) {
        CustomPlugin.registerCommand(instance, cmd);
    }

    public static void registerCommand(Plugin pl, Command cmd) {
        CustomPlugin.registerCommand(pl.getName(), cmd);
    }

    private static void registerCommand(String pl, Command cmd) {
        commandMap.register(pl, cmd);
    }

    public static boolean isPermissionRegistered(String permission) {
        for (Permission p : Bukkit.getPluginManager().getPermissions()) {
            if (p.getName().equalsIgnoreCase(permission)) {
                return true;
            }
        }
        return false;
    }

    public static void registerPermission(String permission) {
        if (!isPermissionRegistered(permission)) {
            Bukkit.getPluginManager().addPermission(new Permission(permission));
        }
    }

    public static void registerListener(Listener l) {
        Bukkit.getPluginManager().registerEvents(l, instance);
        instance.debug("Listener " + l.getClass().getSimpleName() + "@" + l.hashCode() + " registered.", 2);
    }

    public static void unregisterListener(Listener l) {
        HandlerList.unregisterAll(l);
    }

    public CommandMap getCommandMap() {
        return commandMap;
    }

    public FileConfiguration loadConfig() {
        FileConfiguration fc = getConfig();
        saveConfig();
        return fc;
    }

    public FileConfiguration loadResource(File config, String resource) {
        if (!config.exists()) {
            //Load from plugin
            saveResource(resource, true);
        }
        return YamlConfiguration.loadConfiguration(config);
    }

    public void broadcastDebug(String msg) {
        broadcastDebug(msg, minimumDebugPriotity);
    }

    public void broadcastDebug(String msg, int priority) {
        if (allowBroadcastdebug) {
            if (priority >= minimumDebugPriotity) {
                StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
                StackTraceElement e = stackTraceElements[3];
                Bukkit.broadcastMessage("§c§l" + getPluginName() + "§c§lDebug§7-§e" + e.getClassName() + "@" + e.getMethodName() + "§o(" + e.getLineNumber() + ") §6§l> §7" + msg);
            }
        } else {
            debug(ChatColor.stripColor(msg), priority);
        }
    }

    /**
     * Prints a line on the console<br>
     * <b>Debug priority list</b>
     * <pre>
     * 0 = All (by myseeeeeeelf, together weeeeeee)
     * 1 = Secondary updates (Things that happen in a while with some relevance)
     * 2 = Priority updates (Things that eventually happen and have more relevance)
     * 3 = Loading/Unloading (Loads, Unloads, Reloads, Configs, etc)
     * 4 = Ultra mega power blaster master requires messages </pre>
     */
    public void debug() {
        debug(" ");
    }

    /**
     * Attempts to debug the given message with 0 priority<br>
     * <b>Debug priority list</b>
     * <pre>
     * 0 = All (by myseeeeeeelf, together weeeeeee)
     * 1 = Secondary updates (Things that happen in a while with some relevance)
     * 2 = Priority updates (Things that eventually happen and have more relevance)
     * 3 = Loading/Unloading (Loads, Unloads, Reloads, Configs, etc)
     * 4 = Ultra mega power blaster master requires messages </pre>
     *
     * @param msg The message to be debuged
     */
    public void debug(String[] msg) {
        for (String m : msg) {
            debug(m);
        }
    }

    /**
     * Attempts to debug the given messages with the given priority<br>
     * <b>Debug priority list</b>
     * <pre>
     * 0 = All (by myseeeeeeelf, together weeeeeee)
     * 1 = Secondary updates (Things that happen in a while with some relevance)
     * 2 = Priority updates (Things that eventually happen and have more relevance)
     * 3 = Loading/Unloading (Loads, Unloads, Reloads, Configs, etc)
     * 4 = Ultra mega power blaster master requires messages </pre>
     *
     * @param msg The message to be debuged
     * @param priority The message's priority
     *
     */
    public void debug(String[] msg, int priority) {
        for (String m : msg) {
            debug(m, priority);
        }
    }

    /**
     * Attempts to debug the given message with 0 priority<br>
     * <b>Debug priority list</b>
     * <pre>
     * 0 = All (by myseeeeeeelf, together weeeeeee)
     * 1 = Secondary updates (Things that happen in a while with some relevance)
     * 2 = Priority updates (Things that eventually happen and have more relevance)
     * 3 = Loading/Unloading (Loads, Unloads, Reloads, Configs, etc)
     * 4 = Ultra mega power blaster master requires messages </pre>
     *
     * @param msg The message to be debuged
     */
    public void debug(String msg) {
        debug(msg, 0);
    }

    /**
     * Attempts to debug the given message with the given priority<br>
     * <b>Debug priority list</b>
     * <pre>
     * 0 = All (by myseeeeeeelf, together weeeeeee)
     * 1 = Secondary updates (Things that happen in a while with some relevance)
     * 2 = Priority updates (Things that eventually happen and have more relevance)
     * 3 = Loading/Unloading (Loads, Unloads, Reloads, Configs, etc)
     * 4 = Ultra mega power blaster master requires messages </pre>
     *
     *
     * @param msg The message to be debuged
     * @param priority The message's priority
     */
    public void debug(String msg, int priority) {
        if (priority >= minimumDebugPriotity) {
            getLogger().info(msg);
        }
    }

    /**
     * Forces the debugging of the given messages if force is true<br>
     *
     * @param msg The message to be debuged
     * @param force Whether to force the display of the messages
     */
    public void debug(String msg, boolean force) {
        if (force) {
            getLogger().info(msg);
        } else {
            debug(msg);
        }
    }

    public void printException(String msg, Throwable t) {
        debug(msg, true);
        debug("Reason: ", true);
        debug(t.getMessage(), true);
        debug("--== Error ==--", true);
        t.printStackTrace();
        debug("--== Error ==--", true);
        debug();
    }
}
