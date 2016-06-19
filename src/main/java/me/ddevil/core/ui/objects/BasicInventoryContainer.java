/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ddevil.core.ui.objects;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.ddevil.core.CustomPlugin;
import me.ddevil.core.events.inventory.InventoryObjectClickEvent;
import me.ddevil.core.ui.objects.interfaces.InventoryContainer;
import me.ddevil.core.ui.objects.interfaces.InventoryObject;
import me.ddevil.core.utils.inventory.InventoryUtils;
import me.ddevil.core.ui.objects.interfaces.InventoryObjectClickListener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import me.ddevil.core.ui.menus.InventoryMenu;
import me.ddevil.core.ui.objects.interfaces.ClickableInventoryObject;
import me.ddevil.core.utils.items.ItemUtils;

/**
 * @author Selma
 */
public class BasicInventoryContainer implements InventoryContainer {

    protected final int pos2;
    protected final int pos1;
    protected final int size;
    protected final int width;
    protected final int height;
    protected final Integer[] map;
    protected final Inventory inventory;
    protected final InventoryMenu menu;
    protected final Map<Integer, InventoryObject> inventoryObjects = new HashMap();
    protected InventoryObjectClickListener listener;

    public BasicInventoryContainer(InventoryMenu menu, int pos1, int pos2) {
        this.menu = menu;
        this.inventory = menu.getBukkitInventory();
        this.map = InventoryUtils.getSquare(pos1, pos2);
        this.pos1 = map[0];
        this.pos2 = map[map.length - 1];
        this.size = map.length - 1;
        this.listener = new ContainerClickHandler();
        this.height = size / 9;
        this.width = size % 9;
    }

    public BasicInventoryContainer(InventoryMenu menu, int pos1, int pos2, boolean createCustomListener) {
        this.menu = menu;
        this.inventory = menu.getBukkitInventory();
        this.map = InventoryUtils.getSquare(pos1, pos2);
        this.pos1 = map[0];
        this.pos2 = map[map.length - 1];
        this.size = map.length - 1;
        if (createCustomListener) {
            this.listener = new ContainerClickHandler();
        }
        this.height = size / 9;
        this.width = size % 9;
    }

    public BasicInventoryContainer(InventoryMenu menu, int pos1, int pos2, InventoryObjectClickListener customListener) {
        this.menu = menu;
        this.inventory = menu.getBukkitInventory();
        this.map = InventoryUtils.getSquare(pos1, pos2);
        this.pos1 = map[0];
        this.pos2 = map[map.length - 1];
        this.size = map.length - 1;
        this.listener = customListener;
        this.height = size / 9;
        this.width = size % 9;
    }

    @Override
    public boolean hasItemStack() {
        return false;
    }

    @Override
    public ItemStack getIcon() {
        return null;
    }

    @Override
    public void removeObject(int slot) {
        if (hasObjectIn(slot)) {
            inventoryObjects.remove(slot);
        }
    }

    public void clearAndFill(ItemStack item) {
        clear();
        fill(item);
    }

    public void fill(ItemStack item) {
        for (int slot = 0; slot < map.length; slot++) {
            if (canPlaceIn(slot)) {
                CustomPlugin.instance.debug("Placing item " + ItemUtils.toString(item) + " in slot " + slot);
                setItem(slot, item);
            } else {
                CustomPlugin.instance.debug("Could not place item " + ItemUtils.toString(item) + " in slot " + slot + " while filling");
            }
        }
    }

    private class ContainerClickHandler implements InventoryObjectClickListener {

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
    public void setObject(int id, InventoryObject item) {
        if (map.length > id) {
            int slot = map[id];
            inventoryObjects.put(slot, item);
            boolean hasItemStack = item.hasItemStack();
            if (hasItemStack) {
                ItemStack itemStack = item.getIcon();
                inventory.setItem(slot, itemStack);
            }
            if (item.hasMultiSlots()) {
                List<Integer> slots = item.getMultiSlots();
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
    }

    @Override
    public void clear() {
        for (int i : map) {
            inventory.setItem(i, null);
            removeObject(i);
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
        for (int i = 0; i < map.length; i++) {
            if (canPlaceIn(map[i])) {
                setObject(i, itemIcon);
                break;
            }
        }
    }

    @Override
    public void addItem(ItemStack item) {
        for (int i = 0; i < map.length; i++) {
            if (canPlaceIn(map[i])) {
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
    public List<Integer> getMultiSlots() {
        return Arrays.asList(map);
    }

    @Override
    public String toString() {
        return inventory.getTitle() + " - " + getClass().getSimpleName() + "@" + hashCode();
    }

    @Override
    public void update() {
        for (int i : inventoryObjects.keySet()) {
            InventoryObject o = inventoryObjects.get(i);
            o.update();
            menu.getBukkitInventory().setItem(i, o.getIcon());
        }
    }

    @Override
    public Map<Integer, InventoryObject> getInventoryObjects() {
        return inventoryObjects;
    }

}
