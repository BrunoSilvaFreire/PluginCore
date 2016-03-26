/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ddevil.core.utils.inventory.objects;

import me.ddevil.core.utils.inventory.InventoryMenu;

/**
 *
 * @author Selma
 */
public abstract class BasicInventoryObject implements InventoryObject {

    private final InventoryMenu menu;

    public BasicInventoryObject(InventoryMenu menu) {
        this.menu = menu;
    }

    @Override
    public InventoryMenu getMenu() {
        return menu;
    }

}
