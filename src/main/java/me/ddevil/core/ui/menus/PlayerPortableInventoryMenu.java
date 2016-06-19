/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ddevil.core.ui.menus;

import me.ddevil.core.events.inventory.InventoryObjectClickEvent;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

/**
 *
 * @author murillofreire
 */
public abstract class PlayerPortableInventoryMenu extends BasicInventoryMenu {

    public PlayerPortableInventoryMenu(Player p) {
        super(p.getInventory());
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        ItemStack item = e.getItem();
        Player player = e.getPlayer();
        PlayerInventory inventory = player.getInventory();

        if (inventory.equals(mainInventory)) {
            e.setCancelled(true);
            if (item != null) {
                int slot = mainInventory.first(item);
                if (slot >= 0) {
                    if (hasObjectInSlot(slot)) {
                        Action action = e.getAction();
                        InventoryObjectClickEvent.InteractionType it;
                        if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) {
                            it = InventoryObjectClickEvent.InteractionType.INTERACT_CLICK_RIGHT;
                        } else if ((action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK)) {
                            it = InventoryObjectClickEvent.InteractionType.INTERACT_CLICK_RIGHT;
                        } else {
                            return;
                        }
                        Block finalBlock = null;
                        if (e.getClickedBlock() != null) {
                            finalBlock = e.getClickedBlock().getRelative(e.getBlockFace());
                        }
                        PlayerInventory inv = player.getInventory();
                        new InventoryObjectClickEvent(
                                getInventoryObject(slot),
                                slot,
                                player,
                                it,
                                finalBlock,
                                inv.getItem(inv.getHeldItemSlot())
                        ).
                                call();
                    }
                }
            }
        }
    }
}
