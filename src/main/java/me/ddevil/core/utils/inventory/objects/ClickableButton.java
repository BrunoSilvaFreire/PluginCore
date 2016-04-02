/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ddevil.core.utils.inventory.objects;

import me.ddevil.core.utils.inventory.objects.interfaces.ClickableInventoryObject;
import me.ddevil.core.utils.inventory.objects.interfaces.InventoryObjectClickListener;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Selma
 */
public class ClickableButton extends BasicInventoryObject implements ClickableInventoryObject {

    private final InventoryObjectClickListener interactListener;

    public ClickableButton(ItemStack itemStack, InventoryObjectClickListener interactListener) {
        super(itemStack);
        this.interactListener = interactListener;
    }

    @Override
    public InventoryObjectClickListener getInteractListener() {
        return interactListener;
    }

}
