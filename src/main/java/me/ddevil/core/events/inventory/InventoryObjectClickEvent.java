/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ddevil.core.events.inventory;

import me.ddevil.core.events.CustomEvent;
import org.bukkit.entity.Player;
import me.ddevil.core.utils.inventory.InventoryMenu;
import me.ddevil.core.utils.inventory.objects.InventoryObject;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Selma
 */
public class InventoryObjectClickEvent extends CustomEvent {

    private final InventoryObject object;
    private final InventoryMenu menu;
    private final Player player;
    private final int clickedSlot;
    private final InteractionType clickType;
    private final Block placedBlock;
    private final ItemStack itemInHand;

    public enum InteractionType {

        INVENTORY_CLICK_RIGHT,
        INVENTORY_CLICK_LEFT,
        INTERACT_CLICK_RIGHT,
        INTERACT_CLICK_LEFT;
    }

    public InventoryObjectClickEvent(InventoryObject object, int slot, Player clicker, InteractionType click, Block block, ItemStack itemInHand) {
        this.object = object;
        this.menu = object.getMenu();
        this.player = clicker;
        this.clickedSlot = slot;
        this.clickType = click;
        this.placedBlock = block;
        this.itemInHand = itemInHand;
    }

    public InventoryMenu getMenu() {
        return menu;
    }

    public InventoryObject getObject() {
        return object;
    }

    public Player getPlayer() {
        return player;
    }

    public int getClickedSlot() {
        return clickedSlot;
    }

    public InteractionType getClickType() {
        return clickType;
    }

    public Block getPlacedBlock() {
        return placedBlock;
    }

    public ItemStack getItemInHand() {
        return itemInHand;
    }

}
