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
package me.ddevil.core.chat;

import java.util.List;
import me.ddevil.core.Manager;

/**
 *
 * @author Selma
 */
public interface MessageManager extends Manager {

    public boolean isValidColor(char c);

    public char getColor(int i);

    /**
     * Gets the colored and beautiful plugin name Ex: §a§lMine§e§lMe
     *
     * @return the colored plugin prefix
     */
    public String getPluginPrefix();

    /**
     * Gets the message separator used by the ChatManager
     *
     * @return the message separator
     */
    public String getMessageSeparator();

    public String translateTags(String input);

    public String translateColors(String input);

    public String translateAll(String input);

    public List<String> translateTags(Iterable<String> input);

    public List<String> translateColors(Iterable<String> input);

    public List<String> translateAll(Iterable<String> input);
}
