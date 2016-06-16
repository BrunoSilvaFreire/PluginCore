/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ddevil.core.ui.objects;

import java.util.Arrays;
import me.ddevil.core.events.inventory.InventoryObjectClickEvent;
import me.ddevil.core.ui.objects.interfaces.InventoryObjectClickListener;
import me.ddevil.core.utils.items.ItemUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author BRUNO II
 */
public class PageScroller extends BasicClickableInventoryObject {

    private static ItemStack generateIcon(ScrollableInventoryContainer container, ScrollDirection direction) {
        String name = (direction == ScrollDirection.NEXT) ? "§aNext" : "§aNext";
        return ItemUtils.createItem(Material.DIAMOND, name, Arrays.asList(new String[]{"§a" + container.getCurrentPage() + "§7/§2" + container.getTotalPages()}));
    }

    public PageScroller(final ScrollableInventoryContainer container, final ScrollDirection direction) {
        super(generateIcon(container, direction),
                new InventoryObjectClickListener() {

                    @Override
                    public void onInteract(InventoryObjectClickEvent e) {
                        container.goToPage(direction);
                    }
                }, container.menu);
    }

    public enum ScrollDirection {

        NEXT,
        PREVIOUS
    }
}
