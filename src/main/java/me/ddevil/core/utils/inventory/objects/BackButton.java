/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ddevil.core.utils.inventory.objects;

import me.ddevil.core.events.inventory.InventoryObjectClickEvent;
import me.ddevil.core.utils.inventory.InventoryMenu;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Selma
 */
public class BackButton extends BasicInventoryObject implements InventoryObjectInteractListener {

    private final InventoryMenu returningMenu;

    public BackButton(InventoryMenu ownerMenu, InventoryMenu returnMenu, ItemStack item) {
        super(item, ownerMenu);
        this.returningMenu = returnMenu;
    }

    public InventoryMenu getReturningMenu() {
        return returningMenu;
    }

    @Override
    public void onInteract(InventoryObjectClickEvent e) {
        if (e.getMenu() == menu) {
            returningMenu.open(e.getPlayer());
        }
    }

}
