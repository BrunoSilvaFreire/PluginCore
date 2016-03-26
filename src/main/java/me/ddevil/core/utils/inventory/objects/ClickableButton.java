/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ddevil.core.utils.inventory.objects;

import me.ddevil.core.utils.inventory.InventoryMenu;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Selma
 */
public class ClickableButton extends BasicInventoryObject {

    public ClickableButton(ItemStack itemStack, InventoryMenu menu, InventoryObjectInteractListener interactListener) {
        super(itemStack, menu, interactListener);
    }

}
