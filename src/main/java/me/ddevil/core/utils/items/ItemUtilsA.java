/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ddevil.core.utils.items;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author BRUNO II
 */
public class ItemUtilsA {

    /**
     * Gets an item back from the Map created by {@link serialize()}
     *
     * @param map The map to deserialize from.
     * @return The deserialized item.
     * @throws IllegalAccessException Things can go wrong.
     * @throws IllegalArgumentException Things can go wrong.
     * @throws InvocationTargetException Things can go wrong.
     */
    public static ItemStack deserialize(Map<String, Object> map) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ItemStack i = ItemStack.deserialize(map);
        if (map.containsKey("meta")) {
            try {
                //  org.bukkit.craftbukkit.v1_8_R3.CraftMetaItem$SerializableMeta
                //  CraftMetaItem.SerializableMeta.deserialize(Map<String, Object>)
                if (ITEM_META_DESERIALIZATOR != null) {
                    ItemMeta im = (ItemMeta) DESERIALIZE.invoke(i, map.get("meta"));
                    i.setItemMeta(im);
                }
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                throw e;
            }
        }
        return i;
    }

    /**
     * Serializes an ItemStack and it's ItemMeta, use {@link deserialize()} to
     * get the item back.
     *
     * @param item Item to serialize
     * @return A HashMap with the serialized item
     */
    public static Map<String, Object> serialize(ItemStack item) {
        HashMap<String, Object> itemDocument = new HashMap(item.serialize());
        if (item.hasItemMeta()) {
            itemDocument.put("meta", new HashMap(item.getItemMeta().serialize()));
        }
        return itemDocument;
    }

    //Below here lays some crazy shit that make the above methods work :D yay!
    // <editor-fold desc="Some crazy shit" defaultstate="collapsed">
    /*
     * @return The string used in the CraftBukkit package for the version.
     */
    public static String getVersion() {
        String name = Bukkit.getServer().getClass().getPackage().getName();
        String version = name.substring(name.lastIndexOf('.') + 1) + ".";
        return version;
    }

    /**
     * 
     * @param className
     * @return
     */
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
    private static final Class ITEM_META_DESERIALIZATOR = getOBCClass("inventory.CraftMetaItem").getClasses()[0];
    private static final Method DESERIALIZE = getDeserialize();

    private static Method getDeserialize() {

        try {
            return ITEM_META_DESERIALIZATOR.getMethod("deserialize", new Class[]{Map.class});
        } catch (NoSuchMethodException | SecurityException ex) {
            return null;
        }
    }
    // </editor-fold>
}
