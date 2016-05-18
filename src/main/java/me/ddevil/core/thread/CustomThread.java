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
package me.ddevil.core.thread;

import java.util.ArrayList;

/**
 *
 * @author Selma
 */
public abstract class CustomThread extends Thread {

    private long startTime;
    private long endTime;
    private long totalTime;
    private final ArrayList<ThreadFinishListener> listeners = new ArrayList<>();

    public final void addListener(final ThreadFinishListener listener) {
        listeners.add(listener);
    }

    public final void removeListener(final ThreadFinishListener listener) {
        listeners.remove(listener);
    }

    private void notifyListeners() {
        for (ThreadFinishListener listener : listeners) {
            listener.onFinish();
        }
    }

    @Override
    public final void run() {
        startTime = System.currentTimeMillis();
        try {
            doRun();
            endTime = System.currentTimeMillis();
            totalTime = endTime - startTime;
        } finally {
            notifyListeners();
        }
        listeners.clear();
    }

    public long getTotalTime() {
        return totalTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public float getTotalTimeSeconds() {
        return (float) getTotalTime() / 1000;
    }

    public long getStartTime() {
        return startTime;
    }

    public abstract void doRun();
}
