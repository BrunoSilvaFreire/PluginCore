/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ddevil.core.utils.inventory.objects;

import me.ddevil.core.CustomListener;
import me.ddevil.core.utils.inventory.InventoryMenu;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Selma
 */
public interface InventoryObject {

    public InventoryMenu getMenu();

    public ItemStack getItemStack();

    public boolean hasInteraction();

}