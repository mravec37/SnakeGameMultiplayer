package org.example.multiplayer;


import javax.swing.*;
import java.awt.event.ActionListener;

public class TickTimer {
    private static Timer timer;
    private static int DELAY = 64;

    public static void addActionListener(ActionListener actionListener) {
        if (TickTimer.timer == null) {
            timer = new Timer(DELAY, actionListener);
            timer.start();
        } else {
            timer.addActionListener(actionListener);
        }
    }

    public static void removeActionListener(ActionListener actionListener) {
        if (TickTimer.timer != null) {
            timer.removeActionListener(actionListener);
        }
    }
}
