package org.example.game;

import org.example.graphics_objects.DrawableGameObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class GameGraphicsPanel extends JPanel  implements ActionListener {

    private final int SCREEN_WIDTH;
    private final int SCREEN_HEIGHT;
    private Random random;
    private SnakeGame game;
    GameGraphicsPanel(SnakeGame game) {
        this.game = game;
        random = new Random();
        this.SCREEN_WIDTH = this.game.getScreenWidth();
        this.SCREEN_HEIGHT = this.game.getScreenHeight();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        ArrayList<DrawableGameObject> list = this.game.getDrawableObjects();
        draw(g, list);
    }

    public GameGraphicsPanel getGamePanel() {
        return this;
    }


    public void draw(Graphics g, ArrayList<DrawableGameObject> objectsToDraw) {

        if (this.game.getGameRunning()) {
            g.setColor(Color.green);
            objectsToDraw.forEach(objectToDraw -> {
               objectToDraw.drawObject(g);
            });
        } else
            this.gameOverGraphics(g);

    }

    public void gameOverGraphics(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD,75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over"))/2,
                SCREEN_HEIGHT/2);
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD,40));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Score: " + this.game.getApplesEaten(), (SCREEN_WIDTH - metrics2.stringWidth("Score: " +
                        this.game.getApplesEaten()))/2,
                g.getFont().getSize());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    game.leftArrowPressed();
                    break;
                case KeyEvent.VK_RIGHT:
                    game.rightArrowPressed();
                    break;
                case KeyEvent.VK_UP:
                    game.upArrowPressed();
                    break;
                case KeyEvent.VK_DOWN:
                    game.downArrowPressed();
                    break;

            }
        }
  }
}

//}