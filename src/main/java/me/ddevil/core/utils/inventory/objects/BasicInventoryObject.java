/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ddevil.core.utils.inventory.objects;

import me.ddevil.core.utils.inventory.InventoryMenu;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Selma
 */
public abstract class BasicInventoryObject implements InventoryObject {

    protected final InventoryMenu menu;
    protected final ItemStack itemStack;
    protected InventoryObjectInteractListener interactListener;

    public BasicInventoryObject(ItemStack itemStack, InventoryMenu menu) {
        this.menu = menu;
        this.itemStack = itemStack;
        interactListener = null;
    }

    public InventoryObjectInteractListener getInteractListener() {
        return interactListener;
    }

    public BasicInventoryObject(ItemStack itemStack, InventoryMenu menu, InventoryObjectInteractListener interactListener) {
        this.menu = menu;
        this.itemStack = itemStack;
        this.interactListener = interactListener;
    }

    @Override
    public ItemStack getItemStack() {
        return itemStack;
    }

    @Override
    public InventoryMenu getMenu() {
        return menu;
    }

    @Override
    public boolean hasInteraction() {
        return interactListener != null;
    }

}
