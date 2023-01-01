package org.example.game;

import javax.swing.*;

public class Main {
   private static int SCREEN_WIDTH = 1670;
    private static int SCREEN_HEIGHT = 900;
    public static void main(String[] args) {

        SnakeGame game = new SnakeGame(SCREEN_WIDTH, SCREEN_HEIGHT);
        GameGraphicsPanel gamePanel = new GameGraphicsPanel();
       // Timer timer = new Timer(75, game);
       //timer.addActionListener(gamePanel);
        //StartGamePanel startGamePanel = new StartGamePanel(SCREEN_WIDTH, SCREEN_HEIGHT, game);
        GameWindow gameWindow = new GameWindow(gamePanel);
        //timer.addActionListener(gameWindow);
       // timer.start();
        //game.startGame();
        //game.startGame();
    }
}