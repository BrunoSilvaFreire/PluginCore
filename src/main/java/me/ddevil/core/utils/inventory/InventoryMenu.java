/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ddevil.core.utils.inventory;

import java.util.Map;
import me.ddevil.core.utils.inventory.objects.interfaces.InventoryObject;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 *
 * @author Selma
 */
public interface InventoryMenu {

    public void open(Player p);

    public Inventory getBukkitInventory();

    public Map<Integer, InventoryObject> getMenuMap();

    public void update();
}
