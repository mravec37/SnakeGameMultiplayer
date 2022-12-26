package org.example.game;

import javax.swing.*;

public class GameFrame extends JFrame {

    private GameGraphicsPanel gamePanel;
    GameFrame(GameGraphicsPanel gamePanel) {
        this.add(gamePanel);
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);

    }

    public GameGraphicsPanel getPanel() {
        return this.gamePanel;
    }
}
