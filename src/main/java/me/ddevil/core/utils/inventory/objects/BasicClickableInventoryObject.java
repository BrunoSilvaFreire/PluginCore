/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ddevil.core.utils.inventory.objects;

import me.ddevil.core.utils.inventory.objects.interfaces.InventoryObjectClickListener;
import org.bukkit.inventory.ItemStack;
import me.ddevil.core.utils.inventory.InventoryMenu;
import me.ddevil.core.utils.inventory.objects.interfaces.ClickableInventoryObject;

/**
 *
 * @author Selma
 */
public abstract class BasicClickableInventoryObject extends BasicInventoryItem implements ClickableInventoryObject {

    private final InventoryObjectClickListener interactListener;

    public BasicClickableInventoryObject(ItemStack itemStack, InventoryObjectClickListener interactListener, InventoryMenu menu) {
        super(menu, itemStack);
        this.interactListener = interactListener;
    }

    @Override
    public InventoryObjectClickListener getInteractListener() {
        return interactListener;
    }

}
