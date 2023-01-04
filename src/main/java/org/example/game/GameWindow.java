package org.example.game;

import javax.swing.*;


public class GameWindow extends JFrame {

    private boolean gameRunning = false;
    public GameWindow(GameGraphicsPanel gamePanel) {
        this.add(gamePanel);
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        this.pack();

    }

}
