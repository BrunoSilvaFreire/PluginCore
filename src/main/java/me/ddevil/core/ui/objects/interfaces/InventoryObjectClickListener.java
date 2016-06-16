/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ddevil.core.ui.objects.interfaces;

import me.ddevil.core.events.inventory.InventoryObjectClickEvent;

/**
 *
 * @author Selma
 */
public interface InventoryObjectClickListener {

    public void onInteract(InventoryObjectClickEvent e);
}
