package org.example.game;

import org.example.graphics_objects.DrawableGameObject;
import org.example.multiplayer.ClientNameLocation;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
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
    private JButton continueButton;
    private JButton enterNameButton;
    private JLabel enterNameLabel;
    private boolean gameRunning;
    private boolean playSoundCue;
    private int score;
    private boolean playerReady;
    private ClientArrowKeyPressed arrowKeyPressed;
    private boolean gameStarted;
    private String playerName;
    private String highestScorePlayer;
    private int highestScorePlayerScore;
    private ArrayList<ClientNameLocation> clientsNamesLocations;
    private ArrayList<JLabel> clientNameLabels;

    public GameGraphicsPanel() {
        this.clientNameLabels = new ArrayList<>();
        this.clientsNamesLocations = new ArrayList<>();
        this.objectsToDraw = new ArrayList<>();
//        this.SCREEN_WIDTH = this.game.getScreenWidth();
//        this.SCREEN_HEIGHT = this.game.getScreenHeight();
        this.playerName = "";
        this.highestScorePlayer = "";
        random = new Random();
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
            System.out.println("Button pressed");
            this.startScreen = true;
            continueButton.setVisible(false);
            this.gameStarted = false;
        });

        this.enterNameButton.addActionListener(e -> {
        if (!this.nameField.getText().equals("")) {
            this.playerName = this.nameField.getText();
            this.startScreen = false;
            this.playerReady = true;
            System.out.println("Button pressed xdd");
            hideStartScreen();
        }
        });
    }

    public String getPlayerName() {
        return this.playerName;
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

    public void draw(Graphics g, ArrayList<DrawableGameObject> objectsToDraw) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform defaultAt = g2d.getTransform();
        AffineTransform at = new AffineTransform();
        at.rotate(Math.toRadians(0));
        this.playSoundCue();
        g.setFont(new Font("Ink Free", Font.BOLD,40));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.setColor(Color.red);
        g.drawString("Your Score: " + this.score, SCREEN_WIDTH - (metrics2.stringWidth("Score: " +
                        this.score)+100), g.getFont().getSize());
        g.drawString("Score Leader: " + this.highestScorePlayerScore + " " + this.highestScorePlayer,
                10, g.getFont().getSize());
        this.drawPlayerNames(g);

        if (this.gameRunning) {
            objectsToDraw.forEach(objectToDraw -> {
               g.setColor(objectToDraw.getColor());
               objectToDraw.drawObject(g);
            });
        } else if (!this.gameRunning && this.startScreen)
              this.startScreen(g);
        this.drawPlayerNames(g);
    }

    private void drawPlayerNames(Graphics g) {

        this.clientsNamesLocations.forEach(clients -> {
            int posX = clients.getClientSnakeHeadX();
            int posY = clients.getClientSnakeHeadY();
            String name = clients.getClientName();
            SnakeDirection direction = clients.getSnakeDirection();
            System.out.println("Pos X: " + posX + " posy: " + posY + " name " +  name);
            Graphics2D g2 = (Graphics2D) g;
            g.setFont(new Font("Ink Free", Font.BOLD,20));
            AffineTransform affineTransform = g2.getTransform();
            g.setColor(Color.white);
            FontMetrics metrics = getFontMetrics(g.getFont());
            double theta = 0;
            String nameWithScore = "";
            int x = 0;
            int y=0;
            nameWithScore =  this.score + " " + name;
            if (direction == SnakeDirection.RIGHT) {
                x =  (posX - metrics.stringWidth(name + " " + this.score)) + 15;
                y = posY - 15;
            }
           else if (direction == SnakeDirection.UP) {
                theta = - Math.PI/2;
                y = posY - 15;
                x = posX - metrics.stringWidth(name + " " + this.score);
            }
           else if (direction == SnakeDirection.LEFT) {
                theta = Math.PI;
                y = posY - 30;
                x = posX - metrics.stringWidth(name + " " + this.score);
            }
            else if (direction == SnakeDirection.DOWN) {
                theta =  Math.PI / 2;
                y = posY - 30;
                x = posX - metrics.stringWidth(name + " " + this.score) + 15;
            }
            g2.rotate(theta, posX, posY);
            g2.drawString(nameWithScore, x, y);
            g2.setTransform(affineTransform);
        });
    }


    private void playSoundCue() {

        if (this.playSoundCue) {
            File file;
            Clip clip;
            AudioInputStream audioStream;
            file = new File("food_eaten.wav");
            try {
                audioStream = AudioSystem.getAudioInputStream(file);
                clip = AudioSystem.getClip();
                clip.open(audioStream);
            } catch (UnsupportedAudioFileException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (LineUnavailableException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Play clip");
            clip.start();
            this.playSoundCue = false;
        }
    }

    public void gameOverGraphics(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform defaultAt = g2d.getTransform();
        AffineTransform at = new AffineTransform();
        at.rotate(Math.toRadians(0));
        File file;
        Clip clip;
        AudioInputStream audioStream;
        file = new File("KEKW.wav");
        try {
            audioStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Play clip");
        clip.start();
        this.arrowKeyPressed = ClientArrowKeyPressed.NONE;
        System.out.println("game over");
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD,75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over"))/2,
                SCREEN_HEIGHT/2);
        g.setFont(new Font("Ink Free", Font.BOLD,43));
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD,40));
        FontMetrics metrics2 = getFontMetrics(g.getFont());

        continueButton.setVisible(true);
        this.continueButton.setBounds((SCREEN_WIDTH - metrics.stringWidth("Game Over"))/2,
                SCREEN_HEIGHT/2 + 50, metrics.stringWidth("Game Over"),33);

    }

    public void setHighestScorePlayer(String highestScorePlayer) {
        this.highestScorePlayer = highestScorePlayer;
    }

    public void setHighestScorePlayerScore(int highestScorePlayerScore) {
        this.highestScorePlayerScore = highestScorePlayerScore;
    }

    public void setPlaySoundQue(boolean playSoundQue) {
        this.playSoundCue = playSoundQue;
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

    public void setClientsNamesLocations(ArrayList<ClientNameLocation> clientsNamesLocations) {
        this.clientsNamesLocations = clientsNamesLocations;
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