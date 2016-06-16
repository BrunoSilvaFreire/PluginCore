/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ddevil.core.ui.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import me.ddevil.core.CustomPlugin;
import me.ddevil.core.ui.InventoryMenu;
import me.ddevil.core.utils.inventory.InventoryUtils;
import me.ddevil.core.utils.items.ItemUtils;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author BRUNO II
 */
public class ScrollableInventoryContainer extends BasicInventoryContainer {

    private final ArrayList<Integer> reservedSlots;
    private final HashMap<Integer, InventoryObject> globalItems = new HashMap();
    private final PageScroller next;
    private final PageScroller previous;
    private int currentPageIndex = 0;

    public ScrollableInventoryContainer(InventoryMenu menu, int pos1, int pos2) {
        super(menu, pos1, pos2 - InventoryUtils.getSquareLength(pos1, pos2));
        this.next = new PageScroller(this, PageScroller.ScrollDirection.NEXT);
        this.previous = new PageScroller(this, PageScroller.ScrollDirection.PREVIOUS);
        this.reservedSlots = new ArrayList();
        for (int i = getLeftBottomSlot(); i < getRightBottomSlot(); i++) {
            reservedSlots.add(i);
        }
    }

    private int getLeftBottomSlot() {
        return pos2 - width;
    }

    private int getRightBottomSlot() {
        return pos2;
    }

    @Override
    public void clear() {
        globalItems.clear();
        for (Integer i : globalItems.keySet()) {
            inventory.setItem(i, null);
            removeObject(i);
        }
        super.clear();
    }

    @Override
    public void fill(ItemStack item) {
        for (int slot = 0; slot < getLastSlot(); slot++) {
            if (canPlaceIn(slot)) {
                CustomPlugin.instance.debug("Placing item " + ItemUtils.toString(item) + " in slot " + slot);
                setItem(slot, item);
            } else {
                CustomPlugin.instance.debug("Could not place item " + ItemUtils.toString(item) + " in slot " + slot + " while filling");
            }
        }
    }

    @Override
    public void update() {
        inventoryObjects.clear();
        for (int index = currentPageIndex * size; index < index + size; index++) {
            InventoryObject get = globalItems.get(index);
            if (get != null) {
                inventoryObjects.put(index, get);
            }
        }
        super.update();
    }

    public void goToPage(int page) {
        currentPageIndex = page;
        update();
    }

    public void goToPage(PageScroller.ScrollDirection direction) {
        switch (direction) {
            case NEXT:
                goToPage(getCurrentPage() + 1);
            case PREVIOUS:
                goToPage(getCurrentPage() - 1);
        }
    }

    public int getLastSlot() {
        int lastSlot = 0;
        for (int i : inventoryObjects.keySet()) {
            if (i > lastSlot) {
                lastSlot = i;
            }
        }
        return lastSlot;
    }

    public InventoryObject getLastObject() {
        return inventoryObjects.get(getLastSlot());
    }

    public int getTotalPages() {
        int lastSlot = getLastSlot();
        if (lastSlot == 0) {
            return 1;
        }
        return lastSlot / size + 1;

    }

    public int getCurrentPage() {
        return currentPageIndex + 1;
    }

    @Override
    public void setObject(int id, InventoryObject item) {
        int slot = map[id];
        inventoryObjects.put(slot, item);
        if (item.hasMultiSlots()) {
            List<Integer> slots = item.getMultiSlots();
            for (int i : slots) {
                if (i != slot) {
                    inventoryObjects.put(i, item);
                }
            }
        }

    }
}
