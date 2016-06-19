/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ddevil.core.misc;

/**
 *
 * @author Selma
 */
public class ColorDesign {

    public static final ColorDesign DEFAULT_COLOR_DESIGN = new ColorDesign('b', '3', '7', 'c');
    public static final char REPLACE_COLOR_CHAR = '&';

    //Color char

    public final char primaryColor;
    public final char secondaryColor;
    public final char neutralColor;
    public final char warningColor;

    public ColorDesign(char primaryColor, char secondaryColor, char neutralColor, char warningColor) {
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
        this.neutralColor = neutralColor;
        this.warningColor = warningColor;
    }

    @Override
    public String toString() {
        return "primaryColor = " + primaryColor + System.lineSeparator()
                + "secondaryColor = " + secondaryColor + System.lineSeparator()
                + "neutralColor = " + neutralColor + System.lineSeparator()
                + "warningColor = " + warningColor;
    }

}
