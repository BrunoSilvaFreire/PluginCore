/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ddevil.core.utils.inventory.objects;

import me.ddevil.core.utils.inventory.objects.interfaces.InventoryObjectInteractListener;
import me.ddevil.core.utils.inventory.objects.interfaces.InventoryObject;
import me.ddevil.core.utils.inventory.BasicInventoryMenu;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Selma
 */
public abstract class BasicInventoryObject implements InventoryObject {

    protected BasicInventoryMenu menu;
    protected final ItemStack itemStack;
    protected InventoryObjectInteractListener interactListener;

    public BasicInventoryObject(ItemStack itemStack) {
        this.itemStack = itemStack;
        interactListener = null;
    }

    public InventoryObjectInteractListener getInteractListener() {
        return interactListener;
    }

    public BasicInventoryObject(ItemStack itemStack, InventoryObjectInteractListener interactListener) {
        this.itemStack = itemStack;
        this.interactListener = interactListener;
    }

    @Override
    public ItemStack getItemStack() {
        return itemStack;
    }

    @Override
    public BasicInventoryMenu getMenu() {
        return menu;
    }

    @Override
    public boolean hasInteraction() {
        return interactListener != null;
    }

}
