/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ddevil.core.utils.inventory.objects;

import java.util.List;
import me.ddevil.core.utils.inventory.BasicInventoryMenu;
import org.bukkit.inventory.ItemStack;
import me.ddevil.core.utils.inventory.InventoryMenu;

/**
 *
 * @author Selma
 */
public abstract class BasicInventoryObject implements InventoryObject {

    protected final InventoryMenu menu;

    public BasicInventoryObject(InventoryMenu menu) {
        this.menu = menu;
    }

    @Override
    public InventoryMenu getMenu() {
        return menu;
    }

    @Override
    public boolean hasMultiSlots() {
        return false;
    }

    @Override
    public List<Integer> getMultiSlots() {
        return null;
    }

    @Override
    public boolean hasItemStack() {
        return false;
    }

    @Override
    public ItemStack getIcon() {
        return null;
    }

    @Override
    public void update() {
    }

}
