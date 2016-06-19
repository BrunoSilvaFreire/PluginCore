/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ddevil.core.ui.objects;

import org.bukkit.inventory.ItemStack;
import me.ddevil.core.ui.menus.InventoryMenu;

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

    public void setIcon(ItemStack icon) {
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
