/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ddevil.core.utils.inventory.objects;

import org.bukkit.inventory.ItemStack;
import me.ddevil.core.utils.inventory.objects.interfaces.ClickableInventoryObject;

/**
 *
 * @author HP
 */
public interface InventoryContainer extends InventoryObject, ClickableInventoryObject {

    public void setItem(int slot, ItemStack item);

    public void addItem(ItemStack item);

    public void setObject(int slot, InventoryObject item);

    public void addObject(InventoryObject item);

    public boolean containsSlot(int slot);

    public boolean canPlaceIn(int slot);

    public void removeItem(int slot);

    public void clear();

    public void update();

    public int getSize();
}
