/*
 * Copyright (C) 2016 Selma
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package me.ddevil.core.utils;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 *
 * @author Selma
 */
public class PotionUtils {

    public static PotionEffect parsePotionEffect(String s) throws PotionParseException {
        String[] config = s.split(":");
        try {

            return new PotionEffect(
                    //Name
                    PotionEffectType.getByName(config[0]),
                    //Duration
                    Integer.valueOf(config[1]) * 20 + 1,
                    //Amplifier
                    Integer.valueOf(config[2]));

        } catch (Exception e) {
            throw new PotionParseException(s, e);
        }
    }

    public static class PotionParseException extends Exception {

        public PotionParseException(String input, Exception e) {
            super("Input String " + input + " is not correctly formated for parsing" + System.lineSeparator() + "Reason : " + e.getMessage());
        }

    }
}
