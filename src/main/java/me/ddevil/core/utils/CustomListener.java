/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ddevil.core.utils;

import me.ddevil.core.CustomPlugin;
import org.bukkit.event.Listener;

/**
 *
 * @author BRUNO II
 */
public class CustomListener implements Listener {
    
    public void register() {
        CustomPlugin.registerListener(this);
    }
    
    public void unregister() {
        CustomPlugin.unregisterListener(this);
    }
}
