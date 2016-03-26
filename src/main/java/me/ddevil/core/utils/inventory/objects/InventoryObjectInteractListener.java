/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ddevil.core.utils.inventory.objects;

import org.bukkit.event.inventory.InventoryClickEvent;

/**
 *
 * @author Selma
 */
public interface InventoryObjectInteractListener {

    public void onInteract(InventoryClickEvent e);
}
