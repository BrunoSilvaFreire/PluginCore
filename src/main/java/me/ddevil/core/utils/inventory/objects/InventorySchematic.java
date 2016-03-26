/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ddevil.core.utils.inventory.objects;

import java.util.HashMap;
import java.util.Map;
import me.ddevil.core.utils.inventory.InventoryUtils;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Selma
 */
public class InventorySchematic {

    private final Map<Integer, ItemStack> referenceMap;
    private final int minx;
    private final int miny;
    private final int maxx;
    private final int maxy;

    public InventorySchematic(Inventory inv, int pos1, int pos2) {
        Map<Integer, ItemStack> presetMap = new HashMap();
        for (int i : InventoryUtils.getSquare(inv, pos1, pos2)) {
            if (inv.getItem(i) != null) {
                presetMap.put(i, inv.getItem(i));
            }
        }
        this.referenceMap = presetMap;
        int[] referencePoints = InventoryUtils.getReferencePoints(inv, pos1, pos2);
        this.minx = referencePoints[0];
        this.miny = referencePoints[1];
        this.maxx = referencePoints[2];
        this.maxy = referencePoints[3];

    }

    public void place(Inventory i, int start) {
    }
}
