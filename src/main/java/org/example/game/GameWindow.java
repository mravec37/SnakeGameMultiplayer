package org.example.game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameWindow extends JFrame {

    private GameGraphicsPanel gamePanel;
    private StartGamePanel startGamePanel;
    private SnakeGame game;
    private boolean gameRunning = false;
    GameWindow(GameGraphicsPanel gamePanel, StartGamePanel startGamePanel, SnakeGame game) {
        this.game = game;
        this.startGamePanel = startGamePanel;
        this.gamePanel = gamePanel;
        this.add(gamePanel);
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        this.pack();

    }

}
