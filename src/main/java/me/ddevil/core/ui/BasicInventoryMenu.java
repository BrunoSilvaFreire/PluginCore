/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ddevil.core.ui;

import java.util.HashMap;
import me.ddevil.core.CustomPlugin;
import me.ddevil.core.events.inventory.InventoryObjectClickEvent;
import me.ddevil.core.utils.inventory.InventoryUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import me.ddevil.core.ui.objects.InventoryObject;
import me.ddevil.core.ui.objects.interfaces.ClickableInventoryObject;

/**
 *
 * @author Selma
 */
public abstract class BasicInventoryMenu implements InventoryMenu {

    protected final HashMap<Integer, InventoryObject> objects = new HashMap();
    protected final Inventory mainInventory;

    public void fill(ItemStack i) {
        for (int j = 0; j < mainInventory.getSize(); j++) {
            mainInventory.setItem(j, i);
        }
    }

    public void clear() {
        mainInventory.clear();
        objects.clear();
    }

    public void clearAndFill(ItemStack i) {
        clear();
        fill(i);
    }

    public BasicInventoryMenu(String name, int totalLanes) {
        this.mainInventory = InventoryUtils.createInventory(name, totalLanes);
    }

    public BasicInventoryMenu(Inventory bukkitInventory) {
        this.mainInventory = bukkitInventory;
    }

    @Override
    public HashMap<Integer, InventoryObject> getMenuMap() {
        return objects;
    }

    @Override
    public void registerInventoryObject(InventoryObject o, int initialSlot) throws IllegalArgumentException {
        if (hasObjectInSlot(initialSlot)) {
            throw new IllegalArgumentException("Slot " + initialSlot + " is already in use by " + getInventoryObject(initialSlot).getClass().getSimpleName() + "!");
        } else {
            objects.put(initialSlot, o);
            mainInventory.setItem(initialSlot, o.getIcon());
            if (o.hasMultiSlots()) {
                for (int slot : o.getMultiSlots()) {
                    if (slot != initialSlot) {
                        objects.put(slot, o);
                    }
                }
            }
        }
    }

    @Override
    public InventoryMenu initialSetup() {
        CustomPlugin.registerListener(this);
        setupItems();
        return this;
    }

    protected abstract void setupItems();

    @Override
    public Inventory getBukkitInventory() {
        return mainInventory;
    }

    @Override
    public void open(Player p) {
        update();
        p.openInventory(mainInventory);
    }

    @EventHandler
    public void onClick(InventoryObjectClickEvent e) {
        InventoryObject obj = e.getObject();
        if (obj.getMenu() != null) {
            if (obj.getMenu().equals(this)) {
                if (obj instanceof ClickableInventoryObject) {
                    ClickableInventoryObject c = (ClickableInventoryObject) obj;
                    c.getInteractListener().onInteract(e);
                }
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Inventory clickedInventory = e.getClickedInventory();
        if (clickedInventory != null) {
            if (mainInventory != null) {
                if (clickedInventory.equals(mainInventory)) {
                    e.setCancelled(true);
                    ItemStack item = e.getCurrentItem();
                    if (item != null) {
                        int slot = e.getSlot();
                        if (objects.containsKey(slot)) {
                            InventoryObjectClickEvent.InteractionType type;
                            if (e.isLeftClick()) {
                                type = InventoryObjectClickEvent.InteractionType.INVENTORY_CLICK_LEFT;
                            } else {
                                type = InventoryObjectClickEvent.InteractionType.INVENTORY_CLICK_RIGHT;
                            }
                            new InventoryObjectClickEvent(objects.get(slot), slot, (Player) e.getWhoClicked(), type, null, e.getCursor()).call();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void setItem(ItemStack itemStack, int slot) throws IllegalArgumentException {
        if (hasObjectInSlot(slot)) {
            throw new IllegalArgumentException("Slot " + slot + " is already in use by " + getInventoryObject(slot).getClass().getSimpleName() + "!");

        }
        mainInventory.setItem(slot, itemStack);
    }

    @Override
    public boolean hasObjectInSlot(int slot) {
        return objects.containsKey(slot);
    }

    @Override
    public InventoryObject getInventoryObject(int slot) {
        return objects.get(slot);
    }

    @Override
    public void disable() {
        CustomPlugin.unregisterListener(this);
    }

}
