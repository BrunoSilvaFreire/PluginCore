/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ddevil.core.utils.inventory.objects;

import java.util.HashMap;
import me.ddevil.core.events.inventory.InventoryObjectClickEvent;
import me.ddevil.core.utils.inventory.InventoryUtils;
import me.ddevil.core.utils.inventory.objects.interfaces.InventoryObjectClickListener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import me.ddevil.core.utils.inventory.InventoryMenu;
import me.ddevil.core.utils.inventory.objects.interfaces.ClickableInventoryObject;

/**
 *
 * @author Selma
 */
public class BasicInventoryContainer implements InventoryContainer {

    @Override
    public boolean hasItemStack() {
        return false;
    }

    @Override
    public ItemStack getIcon() {
        return null;
    }

    @Override
    public void removeItem(int slot) {
        if (hasObjectIn(slot)) {
            inventoryObjects.remove(slot);
        }
    }

    public class ClickHandler implements InventoryObjectClickListener {

        @Override
        public void onInteract(InventoryObjectClickEvent e) {
            int clickedSlot = e.getClickedSlot();
            if (inventoryObjects.containsKey(clickedSlot)) {
                InventoryObject obj = inventoryObjects.get(clickedSlot);
                if (obj instanceof ClickableInventoryObject) {
                    ClickableInventoryObject c = (ClickableInventoryObject) obj;
                    c.getInteractListener().onInteract(e);
                }
            }
        }
    }
    protected final int size;
    protected final int width;
    protected final int height;
    protected final Integer[] map;
    protected final Inventory inventory;
    protected final InventoryMenu menu;
    protected final HashMap<Integer, InventoryObject> inventoryObjects = new HashMap();
    private final InventoryObjectClickListener listener;

    public BasicInventoryContainer(InventoryMenu menu, int pos1, int pos2) {
        int containerSize = 0;
        this.menu = menu;
        this.inventory = menu.getBukkitInventory();
        this.map = InventoryUtils.getSquare(inventory, pos1, pos2);
        for (int pos : map) {
            if (inventory.getItem(pos) != null) {
                containerSize++;
            }
        }
        this.size = containerSize;
        this.listener = new ClickHandler();
        this.height = size / 9;
        this.width = size % 9;
    }

    public BasicInventoryContainer(InventoryMenu menu, int pos1, int pos2, InventoryObjectClickListener customListener) {
        int containerSize = 0;
        this.menu = menu;
        this.inventory = menu.getBukkitInventory();
        this.map = InventoryUtils.getSquare(inventory, pos1, pos2);
        for (int pos : map) {
            if (inventory.getItem(pos) != null) {
                containerSize++;
            }
        }
        this.size = containerSize;
        this.listener = customListener;
        this.height = size / 9;
        this.width = size % 9;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void setItem(int slot, ItemStack item) {
        if (map.length > slot) {
            inventory.setItem(map[slot], item);
        }
    }

    @Override
    public void setObject(int slot, InventoryObject item) {
        inventoryObjects.put(slot, item);
        boolean hasItemStack = item.hasItemStack();
        String msg = "Setting inventory object §a" + item.getClass().getSimpleName() + " §7in slot §b" + slot + " §7in inventory §e" + toString();
        if (hasItemStack) {
            ItemStack itemStack = item.getIcon();
            inventory.setItem(slot, itemStack);
        }
        if (item.hasMultiSlots()) {
            Integer[] slots = item.getMultiSlots();
            for (int i : slots) {
                if (i != slot) {
                    inventoryObjects.put(i, item);
                    if (hasItemStack) {
                        inventory.setItem(i, item.getIcon());
                    }
                }
            }
        }
    }

    @Override
    public void clear() {
        for (int i : map) {
            inventory.setItem(i, null);
            removeItem(i);
        }
    }

    public boolean hasObjectIn(int slot) {
        return inventoryObjects.containsKey(slot);
    }

    @Override
    public boolean canPlaceIn(int slot) {
        return !hasObjectIn(slot);
    }

    @Override
    public boolean containsSlot(int slot) {
        for (int m : map) {
            if (m == slot) {
                return true;
            }
        }
        return false;
    }

    @Override
    public InventoryMenu getMenu() {
        return menu;
    }

    @Override
    public void addObject(InventoryObject itemIcon) {
        for (int i : map) {
            if (canPlaceIn(i)) {
                setObject(i, itemIcon);
                break;
            }
        }
    }

    @Override
    public void addItem(ItemStack item) {
        for (int i : map) {
            if (canPlaceIn(i)) {
                setItem(i, item);
                break;
            }
        }
    }

    @Override
    public InventoryObjectClickListener getInteractListener() {
        return listener;
    }

    @Override
    public boolean hasMultiSlots() {
        return true;
    }

    @Override
    public Integer[] getMultiSlots() {
        return map;
    }

    @Override
    public String toString() {
        return inventory.getTitle() + " - " + getClass().getSimpleName() + "@" + hashCode();
    }

    @Override
    public void update() {
        for (int i : inventoryObjects.keySet()) {
            menu.getBukkitInventory().setItem(i, inventoryObjects.get(i).getIcon());
        }
    }
}
