package me.ddevil.core.eastereggs;

import me.ddevil.core.CustomPlugin;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by BRUNO II on 16/06/2016.
 */
public class EasterEggs {
    public static void main(String[] args) {
        try {
            System.out.println("Oh noes, you are supposed to place this file in /{your server folder}/plugins");
            Thread.sleep(5000);
            System.out.println("I mean... come on man, are you even trying?");
            System.in.read();
        } catch (InterruptedException | IOException ex) {
            //Oh noes, I don't care
        }
    }
}
