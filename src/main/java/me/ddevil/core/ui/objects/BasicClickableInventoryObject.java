/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ddevil.core.ui.objects;

import me.ddevil.core.ui.objects.interfaces.InventoryObjectClickListener;
import org.bukkit.inventory.ItemStack;
import me.ddevil.core.ui.menus.InventoryMenu;
import me.ddevil.core.ui.objects.interfaces.ClickableInventoryObject;

/**
 *
 * @author Selma
 */
public class BasicClickableInventoryObject extends BasicInventoryItem implements ClickableInventoryObject {

    protected InventoryObjectClickListener interactListener;

    public BasicClickableInventoryObject(ItemStack itemStack, InventoryObjectClickListener interactListener, InventoryMenu menu) {
        super(menu, itemStack);
        this.interactListener = interactListener;
    }

    public BasicClickableInventoryObject(ItemStack itemStack, InventoryMenu menu) {
        super(menu, itemStack);
    }

    public void setInteractListener(InventoryObjectClickListener interactListener) {
        this.interactListener = interactListener;
    }

    @Override
    public InventoryObjectClickListener getInteractListener() {
        return interactListener;
    }

}
