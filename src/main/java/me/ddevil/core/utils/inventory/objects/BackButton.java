/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ddevil.core.utils.inventory.objects;

import me.ddevil.core.utils.inventory.objects.interfaces.InventoryObjectInteractListener;
import me.ddevil.core.events.inventory.InventoryObjectClickEvent;
import me.ddevil.core.utils.inventory.BasicInventoryMenu;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Selma
 */
public class BackButton extends BasicInventoryObject implements InventoryObjectInteractListener {

    private final BasicInventoryMenu returningMenu;

    public BackButton(BasicInventoryMenu returnMenu, ItemStack item) {
        super(item);
        this.returningMenu = returnMenu;
    }

    public BasicInventoryMenu getReturningMenu() {
        return returningMenu;
    }

    @Override
    public void onInteract(InventoryObjectClickEvent e) {
        if (e.getMenu() == menu) {
            returningMenu.open(e.getPlayer());
        }
    }

}
