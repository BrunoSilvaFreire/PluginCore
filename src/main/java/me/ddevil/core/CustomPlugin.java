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
import me.ddevil.core.chat.PluginMessageManager;
import me.ddevil.core.misc.ColorDesign;
import me.ddevil.core.utils.DebugLevel;
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
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.lang.reflect.Field;

public abstract class CustomPlugin extends JavaPlugin implements Listener {

    //General info
    public ColorDesign colorDesign;
    public MessageManager messageManager;

    //<editor-fold desc="Techy and ugly stuff" defaultstate="collapsed">
    protected CommandMap commandMap;
    public int minimumDebugPriority = 0;
    private boolean allowBroadcastdebug;

    public CommandMap getCommandMap() {
        return commandMap;
    }


    @Override
    public final void onEnable() {
        pluginFolder = getDataFolder();
        if (!pluginFolder.exists()) {
            debug("Plugin folder not found! Making one...", DebugLevel.SHOULDNT_HAPPEN_BUT_WE_CAN_HANDLE_IT);
            pluginFolder.mkdir();
        }
        pluginConfigFile = new File(pluginFolder, "config.yml");
        if (!pluginConfigFile.exists()) {
            //Load from plugin
            debug("Config file not found! Making one...", DebugLevel.SHOULDNT_HAPPEN_BUT_WE_CAN_HANDLE_IT);
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
        initializeComponents();
        //Check for default components
        if (colorDesign == null) {
            colorDesign = ColorDesign.DEFAULT_COLOR_DESIGN;
            debug("Plugin didn't specify any Color Design, loading default one...", DebugLevel.SHOULDNT_HAPPEN_BUT_WE_CAN_HANDLE_IT);
        }
        if (messageManager == null) {
            messageManager = (MessageManager) new PluginMessageManager(this).setup();
            debug("Plugin didn't specify any Message Manager, loading default one...", DebugLevel.SHOULDNT_HAPPEN_BUT_WE_CAN_HANDLE_IT);
        }
        doSetup();
    }

    //</editor-fold>
    //<editor-fold desc="Files/Configs variables" defaultstate="collapsed">
    public File pluginFolder;
    public File pluginConfigFile;
    public FileConfiguration pluginConfig;
    //</editor-fold>
    //<editor-fold desc="NMS/OBC Reflection" defaultstate="collapsed">

    public String getVersion() {
        String name = Bukkit.getServer().getClass().getPackage().getName();
        String version = name.substring(name.lastIndexOf('.') + 1) + ".";
        return version;
    }

    public Class<?> getNMSClass(String className) {
        String fullName = "net.minecraft.server." + getVersion() + className;
        Class<?> clazz = null;
        try {
            clazz = Class.forName(fullName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clazz;
    }

    public Class<?> getOBCClass(String className) {
        String fullName = "org.bukkit.craftbukkit." + getVersion() + className;
        Class<?> clazz = null;
        try {
            clazz = Class.forName(fullName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clazz;
    }

    //</editor-fold>
    //<editor-fold desc="Abstract methods" defaultstate="collapsed">
    public abstract String getPluginName();

    public abstract FileConfiguration getMessagesConfig();

    protected abstract void initializeComponents();

    protected abstract void doSetup();

    protected abstract void doReload();

    //</editor-fold>
    //<editor-fold desc="Commands and permissions" defaultstate="collapsed">
    public void registerCommand(Command cmd) {
        commandMap.register(getPluginName(), cmd);
    }

    public boolean isPermissionRegistered(String permission) {
        for (Permission p : Bukkit.getPluginManager().getPermissions()) {
            if (p.getName().equalsIgnoreCase(permission)) {
                return true;
            }
        }
        return false;
    }

    public void registerPermission(String permission) {
        if (!isPermissionRegistered(permission)) {
            Bukkit.getPluginManager().addPermission(new Permission(permission));
        }
    }

    //</editor-fold>
    //<editor-fold desc="Debugging" defaultstate="collapsed">


    public void broadcastDebug(String msg) {
        broadcastDebug(msg, DebugLevel.OKAY_SOME_REAL_SHIT_HAPPENED);
    }

    public void broadcastDebug(String msg, DebugLevel level) {
        int priority = level.ordinal();
        if (allowBroadcastdebug) {
            if (priority >= minimumDebugPriority) {
                StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
                StackTraceElement e = stackTraceElements[3];
                Bukkit.broadcastMessage("§c§l" + getPluginName() + "§c§lDebug§7-§e" + e.getClassName() + "@" + e.getMethodName() + "§o(" + e.getLineNumber() + ") §6§l> §7" + msg);
            }
        } else {
            debug(ChatColor.stripColor(msg), level);
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
        debug(" ", DebugLevel.NO_BIG_DEAL);
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
     * @param msg      The message to be debuged
     * @param priority The message's priority
     */
    public void debug(String[] msg, DebugLevel priority) {
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
        debug(msg, DebugLevel.NO_BIG_DEAL);
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
     * @param msg   The message to be debuged
     * @param level The message's priority
     */
    public void debug(String msg, DebugLevel level) {
        int priority = level.ordinal();
        if (priority >= minimumDebugPriority) {
            getLogger().info(msg);
        }
    }

    /**
     * Forces the debugging of the given messages if force is true<br>
     *
     * @param msg   The message to be debuged
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

    //</editor-fold >

    public void reload(Player p) {
        messageManager.sendMessage(p, "Reloading...");
        long start = System.currentTimeMillis();
        doReload();
        long end = System.currentTimeMillis();
        long time = end - start;
        messageManager.sendMessage(p, "Reloaded! Took " + (time / 1000) + "seconds! " + colorDesign.secondaryColor + "(" + time + "ms)");
    }

    public void registerListener(Listener l) {
        Bukkit.getPluginManager().registerEvents(l, this);
        debug("Listener " + l.toString() + " registered.", DebugLevel.NO_BIG_DEAL);
    }

    public void unregisterListener(Listener l) {
        HandlerList.unregisterAll(l);
    }

    public FileConfiguration loadResource(File config, String resource) {
        if (!config.exists()) {
            //Load from plugin
            saveResource(resource, true);
        }
        return YamlConfiguration.loadConfiguration(config);
    }

}
