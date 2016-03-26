/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ddevil.core.utils.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Selma
 */
public class ItemData {

    public static ItemData fromItemStack(ItemStack i) {
        return new ItemData(i.getType(), i.getData().getData());
    }
    private final byte data;
    private final Material type;

    public ItemData(Material type, byte data) {
        this.type = type;
        this.data = data;

    }

    public byte getData() {
        return data;
    }

    public Material getType() {
        return type;
    }

    public boolean equals(ItemData itemData) {
        return itemData.getData() == data && itemData.getType() == type;
    }
}
