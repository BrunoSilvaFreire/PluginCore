/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ddevil.core.utils;

import java.text.DecimalFormat;

/**
 *
 * @author Selma
 */
public class MiscUtils {

    public static float getPercentage(int number, int maximum) {
        return (number * 100.0f) / maximum;
    }

}
