package org.example.game;

import org.example.graphics_objects.DrawableGameObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class GameGraphicsPanel extends JPanel  implements ActionListener {

    private final int SCREEN_WIDTH = 1670;
    private final int SCREEN_HEIGHT = 900;
    private Random random;
    private  JTextField nameField;
    private boolean startScreen;
    private ArrayList<DrawableGameObject> objectsToDraw;
    JButton continueButton;
    JButton enterNameButton;
    JLabel enterNameLabel;
    int numberOfFramesDrawn = 0;
    private boolean gameRunning;

    private int score;

    private boolean playerReady;
    private ClientArrowKeyPressed arrowKeyPressed;
    private boolean gameStarted;

    public GameGraphicsPanel() {
        this.objectsToDraw = new ArrayList<>();
//        this.SCREEN_WIDTH = this.game.getScreenWidth();
//        this.SCREEN_HEIGHT = this.game.getScreenHeight();
        this.playerReady = false;
        this.gameStarted = false;
        random = new Random();
        this.score = 0;
        this.gameRunning = false;
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.startScreen = true;
        this.arrowKeyPressed = ClientArrowKeyPressed.NONE;
        this.setFocusable(true);
        this.addKeyListener(new GameGraphicsPanelKeyAdapter());
        this.setUpPanelComponents();
        this.setUpListeners();
    }

    private void setUpListeners() {
        this.continueButton.addActionListener(e -> {
            //startScreen = true;
            System.out.println("Button pressed");
            this.startScreen = true;
            continueButton.setVisible(false);
            this.gameStarted = false;
        });

        this.enterNameButton.addActionListener(e -> {
            this.startScreen = false;
            this.playerReady = true;
            System.out.println("Button pressed xdd");
            hideStartScreen();
        });
    }

    public void setGameRunning(boolean gameRunning) {
        this.gameRunning = gameRunning;

    }

    public ClientArrowKeyPressed getArrowKeyPressed() {
        return arrowKeyPressed;
    }
    private void hideStartScreen() {
        enterNameLabel.setVisible(false);
        enterNameButton.setVisible(false);
        nameField.setVisible(false);
        this.continueButton.setVisible(false);
    }

    public void setObjectsToDraw(ArrayList<DrawableGameObject> objectsToDraw) {
        this.objectsToDraw = (ArrayList<DrawableGameObject>) objectsToDraw.clone();
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
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
           if (startScreen && !gameStarted)
               this.startScreen(g);
        else if (!startScreen && this.gameRunning)
            draw(g, objectsToDraw);
        else if (!startScreen && !this.gameRunning && gameStarted)
               this.gameOverGraphics(g);
    }

    public GameGraphicsPanel getGamePanel() {
        return this;
    }


    public void draw(Graphics g, ArrayList<DrawableGameObject> objectsToDraw) {
        g.setFont(new Font("Ink Free", Font.BOLD,40));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.setColor(Color.red);
        g.drawString("Score: " + this.score, (SCREEN_WIDTH - metrics2.stringWidth("Score: " + this.score))/2,
                g.getFont().getSize());
        numberOfFramesDrawn++;
        if (this.gameRunning) {
            objectsToDraw.forEach(objectToDraw -> {
               g.setColor(objectToDraw.getColor());
               objectToDraw.drawObject(g);
            });
        } else if (!this.gameRunning && !this.startScreen){}
            //this.gameOverGraphics(g);
          else if (!this.gameRunning && this.startScreen)
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
//       g.drawString("Score: " + this.game.getApplesEaten(), (SCREEN_WIDTH - metrics2.stringWidth("Score: " +
//                        this.game.getApplesEaten()))/2,
//                g.getFont().getSize());

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

    public void setArrowkeyPressed(ClientArrowKeyPressed clientArrowKeyPressed) {
        this.arrowKeyPressed = clientArrowKeyPressed;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void paintGraphics() {
        this.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       // this.repaint();
    }

    public boolean isPlayerReady() {
        return playerReady;
    }

    public void setPlayerReady(boolean playerReady) {
        this.playerReady = playerReady;
    }

    public class GameGraphicsPanelKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    GameGraphicsPanel.this.arrowKeyPressed = ClientArrowKeyPressed.LEFT_ARROW;
                    break;
                case KeyEvent.VK_RIGHT:
                    GameGraphicsPanel.this.arrowKeyPressed = ClientArrowKeyPressed.RIGHT_ARROW;
                    break;
                case KeyEvent.VK_UP:
                    GameGraphicsPanel.this.arrowKeyPressed = ClientArrowKeyPressed.UP_ARROW;
                    break;
                case KeyEvent.VK_DOWN:
                    GameGraphicsPanel.this.arrowKeyPressed = ClientArrowKeyPressed.DOWN_ARROW;
                    break;

            }
        }
  }
}

//}