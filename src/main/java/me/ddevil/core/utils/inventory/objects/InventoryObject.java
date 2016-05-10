/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ddevil.core.utils.inventory.objects;

import org.bukkit.inventory.ItemStack;
import me.ddevil.core.utils.inventory.InventoryMenu;

/**
 *
 * @author Selma
 */
public interface InventoryObject {

    public InventoryMenu getMenu();

    public boolean hasMultiSlots();

    public Integer[] getMultiSlots();

    public boolean hasItemStack();

    public ItemStack getIcon();

}
