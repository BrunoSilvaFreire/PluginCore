/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ddevil.core.thread;

import java.util.ArrayList;

/**
 *
 * @author Selma
 */
public abstract class ProgressableThread extends CustomThread {

    private final ArrayList<ThreadUpdateListener> updateListeners = new ArrayList<>();

    public final void addUpdateListener(final ThreadUpdateListener listener) {
        updateListeners.add(listener);
    }

    public final void removeUpdateListener(final ThreadUpdateListener listener) {
        updateListeners.remove(listener);
    }

    protected void notifyUpdateListeners() {
        int i = 0;
        for (ThreadUpdateListener listener : updateListeners) {
            listener.onUpdate();
            i++;
        }

    }
}
