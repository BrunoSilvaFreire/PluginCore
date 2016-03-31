/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ddevil.core.chat;

/**
 *
 * @author Selma
 */
public class ColorDesign {

    private final char primaryColor;
    private final char secondaryColor;
    private final char neutralColor;
    private final char warningColor;

    public ColorDesign(char primaryColor, char secondaryColor, char neutralColor, char warningColor) {
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
        this.neutralColor = neutralColor;
        this.warningColor = warningColor;
    }

    public char getWarningColor() {
        return warningColor;
    }

    public char getSecondaryColor() {
        return secondaryColor;
    }

    public char getPrimaryColor() {
        return primaryColor;
    }

    public char getNeutralColor() {
        return neutralColor;
    }

}
