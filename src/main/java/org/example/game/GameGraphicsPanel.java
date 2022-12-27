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
    private  JTextField nameField;
    private boolean startScreen;
    JButton continueButton;
    JButton enterNameButton;
    JLabel enterNameLabel;
    GameGraphicsPanel(SnakeGame game) {
        this.game = game;
        this.SCREEN_WIDTH = this.game.getScreenWidth();
        this.SCREEN_HEIGHT = this.game.getScreenHeight();
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.startScreen = true;
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        this.setUpPanelComponents();
        this.setUpListeners();
    }

    private void setUpListeners() {
        this.continueButton.addActionListener(e -> {
            startScreen = true;
            System.out.println("Button pressed");
            continueButton.setVisible(false);
        });

        this.enterNameButton.addActionListener(e -> {
            startScreen = false;
            game.startGame();
            System.out.println("Button pressed xdd");
            hideStartScreen();
            game.startGame();
        });
    }

    private void hideStartScreen() {
        enterNameLabel.setVisible(false);
        enterNameButton.setVisible(false);
        nameField.setVisible(false);
    }

    private void setUpPanelComponents() {
        this.continueButton = new JButton("Continue");
        this.continueButton.setVisible(false);
        this.add(continueButton);
        this.enterNameLabel = new JLabel();
        this.enterNameLabel.setVisible(false);
        enterNameLabel.setText("Enter your name");
        enterNameLabel.setFont(new Font("Serif", Font.PLAIN, 40));
        enterNameLabel.setForeground(Color.red);
        enterNameButton = new JButton("Play");
        this.add(this.enterNameLabel);
        this.enterNameButton = new JButton("Play");
        this.enterNameButton.setVisible(false);
        this.add(enterNameButton);
        this. nameField = new JTextField();
        this.nameField.setVisible(false);
        this.add(nameField);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        ArrayList<DrawableGameObject> list = this.game.getDrawableObjects();
           if (startScreen)
               this.startScreen(g);
        else
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
        } else if (!this.game.getGameRunning() && !this.startScreen)
            this.gameOverGraphics(g);
          else if (!this.game.getGameRunning() && this.startScreen)
              this.startScreen(g);
    }

    public void gameOverGraphics(Graphics g) {

        System.out.println("game over");
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

        continueButton.setVisible(true);
        this.continueButton.setBounds((SCREEN_WIDTH - metrics.stringWidth("Game Over"))/2,
                SCREEN_HEIGHT/2 + 50, metrics.stringWidth("Game Over"),33);

    }

    private void startScreen(Graphics g) {
        g.setColor(Color.red);
        System.out.println("startScreen");
        enterNameLabel.setVisible(true);
        enterNameLabel.setBounds((this.SCREEN_WIDTH/2)-200,(this.SCREEN_HEIGHT/2)-200,400,300);
        enterNameButton.setVisible(true);
        enterNameButton.setBounds((this.SCREEN_WIDTH/2) +215,this.SCREEN_HEIGHT/2,130,33);
        nameField.setVisible(true);
        nameField.setBounds(this.SCREEN_WIDTH/2-(200) ,this.SCREEN_HEIGHT/2,400,33);
        System.out.println("startScreen2");
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