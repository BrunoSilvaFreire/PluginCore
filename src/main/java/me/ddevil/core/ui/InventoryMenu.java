/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ddevil.core.ui;

import java.util.Map;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import me.ddevil.core.ui.objects.InventoryObject;

/**
 *
 * @author Selma
 */
public interface InventoryMenu extends Listener {

    public void open(Player p);

    public void disable();

    public InventoryMenu initialSetup();

    public Inventory getBukkitInventory();

    public Map<Integer, InventoryObject> getMenuMap();

    public void registerInventoryObject(InventoryObject o, int i) throws IllegalArgumentException;

    public void setItem(ItemStack itemStack, int slot);

    public void update();

    public boolean hasObjectInSlot(int slot);

    public InventoryObject getInventoryObject(int slot);
}
