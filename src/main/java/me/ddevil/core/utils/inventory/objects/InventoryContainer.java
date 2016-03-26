/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ddevil.core.utils.inventory.objects;

import me.ddevil.core.utils.inventory.InventoryUtils;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Selma
 */
public class InventoryContainer {
    
    protected final int size;
    protected final Integer[] map;
    protected final Inventory inventory;
    
    public InventoryContainer(Inventory inv, int pos1, int pos2) {
        int containerSize = 0;
        this.map = InventoryUtils.getSquare(inv, pos1, pos2);
        for (int pos : map) {
            if (inv.getItem(pos) != null) {
                containerSize++;
            }
        }
        this.size = containerSize;
        this.inventory = inv;
    }
    
    public int getSize() {
        return size;
    }
    
    public void setItem(int slot, ItemStack item) {
        if (map.length > slot) {
            inventory.setItem(map[slot], item);
        }
    }
    
    public void clear() {
        for (int i : map) {
            inventory.setItem(i, null);
        }
    }
    
    public boolean canPlaceIn(int slot) {
        return map.length > slot;
    }
    
    public boolean contains(int slot) {
        for (int m : map) {
            if (m == slot) {
                return true;
            }
        }
        return false;
    }
}
