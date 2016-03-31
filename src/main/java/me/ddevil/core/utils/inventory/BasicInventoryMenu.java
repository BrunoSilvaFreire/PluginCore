/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ddevil.core.utils.inventory;

import me.ddevil.core.utils.inventory.objects.interfaces.InventoryObject;
import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 *
 * @author Selma
 */
public abstract class BasicInventoryMenu implements InventoryMenu {

    protected final HashMap<Integer, InventoryObject> objects = new HashMap();
    protected final Inventory mainInventory;

    public BasicInventoryMenu(Inventory bukkitInventory) {
        this.mainInventory = bukkitInventory;
    }

    @Override
    public HashMap<Integer, InventoryObject> getMenuMap() {
        return objects;
    }

    public BasicInventoryMenu(String name, int totalLanes) {
        this.mainInventory = InventoryUtils.createInventory(name, totalLanes);
    }

    @Override
    public Inventory getBukkitInventory() {
        return mainInventory;
    }

    @Override
    public void open(Player p) {
        p.openInventory(mainInventory);
    }
}
