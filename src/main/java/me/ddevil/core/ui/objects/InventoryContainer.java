/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ddevil.core.ui.objects;

import java.util.Map;
import org.bukkit.inventory.ItemStack;
import me.ddevil.core.ui.objects.interfaces.ClickableInventoryObject;

/**
 *
 * @author HP
 * @param <T>
 */
public interface InventoryContainer<T extends InventoryObject> extends InventoryObject, ClickableInventoryObject {

    public void setItem(int slot, ItemStack item);

    public void addItem(ItemStack item);

    public void setObject(int slot, T item);

    public void addObject(T item);

    public boolean containsSlot(int slot);

    public boolean canPlaceIn(int slot);

    public void removeObject(int slot);

    public void clear();

    public Map<Integer, T> getInventoryObjects();

    public int getSize();
}
