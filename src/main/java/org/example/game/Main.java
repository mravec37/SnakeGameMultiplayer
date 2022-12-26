package org.example.game;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        SnakeGame game = new SnakeGame(1670, 900);
        GameGraphicsPanel gamePanel = new GameGraphicsPanel(game);
        Timer timer = new Timer(75, game);
        timer.addActionListener(gamePanel);

        new GameFrame(gamePanel);
        timer.start();
        game.startGame();
    }
}