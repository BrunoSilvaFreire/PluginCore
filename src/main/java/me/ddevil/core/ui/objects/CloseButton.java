/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ddevil.core.ui.objects;

import me.ddevil.core.events.inventory.InventoryObjectClickEvent;
import me.ddevil.core.ui.BasicInventoryMenu;
import me.ddevil.core.ui.objects.interfaces.InventoryObjectClickListener;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author HP
 */
public class CloseButton extends BasicClickableInventoryObject {

    public CloseButton(ItemStack item, BasicInventoryMenu menu) {
        super(item, new InventoryObjectClickListener() {

            @Override
            public void onInteract(InventoryObjectClickEvent e) {
                e.getPlayer().closeInventory();
            }
        }, menu
        );
    }
}
