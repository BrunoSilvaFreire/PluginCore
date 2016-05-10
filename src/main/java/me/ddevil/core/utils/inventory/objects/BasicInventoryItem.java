/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ddevil.core.utils.inventory.objects;

import me.ddevil.core.utils.inventory.BasicInventoryMenu;
import org.bukkit.inventory.ItemStack;
import me.ddevil.core.utils.inventory.InventoryMenu;

/**
 *
 * @author HP
 */
public class BasicInventoryItem extends BasicInventoryObject {

    protected ItemStack icon;

    public BasicInventoryItem(InventoryMenu menu, ItemStack icon) {
        super(menu);
        this.icon = icon;
    }

    @Override
    public ItemStack getIcon() {
        return icon;
    }

    @Override
    public boolean hasItemStack() {
        return true;
    }

}
