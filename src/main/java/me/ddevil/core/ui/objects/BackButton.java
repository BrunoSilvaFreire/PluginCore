/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ddevil.core.ui.objects;

import me.ddevil.core.events.inventory.InventoryObjectClickEvent;
import me.ddevil.core.ui.objects.interfaces.InventoryObjectClickListener;
import me.ddevil.core.ui.BasicInventoryMenu;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Selma
 */
public class BackButton extends BasicClickableInventoryObject {

    private final BasicInventoryMenu returningMenu;

    public BackButton(final BasicInventoryMenu returnMenu, ItemStack item, BasicInventoryMenu menu) {
        super(item, new InventoryObjectClickListener() {

            private final BasicInventoryMenu returningMenu = returnMenu;

            @Override
            public void onInteract(InventoryObjectClickEvent e) {
                returningMenu.open(e.getPlayer());
            }
        }, menu);
        this.returningMenu = returnMenu;
    }

    public BasicInventoryMenu getReturningMenu() {
        return returningMenu;
    }

}
