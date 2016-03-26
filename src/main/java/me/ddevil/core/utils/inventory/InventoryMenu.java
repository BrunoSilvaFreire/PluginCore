/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ddevil.core.utils.inventory;

import me.ddevil.core.utils.inventory.objects.InventoryObject;
import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 *
 * @author Selma
 */
public class InventoryMenu {

    private final HashMap<Integer, InventoryObject> objects = new HashMap();
    private final Inventory bukkitInventory;

    public InventoryMenu(Inventory bukkitInventory) {
        this.bukkitInventory = bukkitInventory;
    }

    public InventoryMenu(String name, int totalLanes) {
        this.bukkitInventory = InventoryUtils.createInventory(name, totalLanes);
    }

    public void addObject(InventoryObject o) {
    }

    public Inventory getBukkitInventory() {
        return bukkitInventory;
    }

    public void open(Player p) {
        p.openInventory(bukkitInventory);
    }
}
