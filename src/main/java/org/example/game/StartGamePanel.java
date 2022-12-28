package org.example.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartGamePanel extends JPanel  {
    private final int SCREEN_WIDTH;
    private final int SCREEN_HEIGHT;
    private  JTextField nameField;
    private SnakeGame game;
    public StartGamePanel(int screenWidth, int screenHeight, SnakeGame game) {
        this.game = game;
        this.SCREEN_HEIGHT = screenHeight;
        this.SCREEN_WIDTH = screenWidth;
        this.setPreferredSize(new Dimension(this.SCREEN_WIDTH, this.SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.setVisible(true);
        this.setLayout(null);
        this.createGui();
    }

    private void createGui() {
        JLabel enterNameLabel = new JLabel();
        enterNameLabel.setText("Enter your name");
        enterNameLabel.setFont(new Font("Serif", Font.PLAIN, 40));
        enterNameLabel.setBounds(this.SCREEN_WIDTH/2-(200),(this.SCREEN_HEIGHT/2)-200,400,300);
        enterNameLabel.setForeground(Color.red);
        this.add(enterNameLabel);

        JButton enterNameButton = new JButton("Enter");
        //enterNameButton.setLocation(this.SCREEN_WIDTH/2, this.SCREEN_HEIGHT/2);
        enterNameButton.setBounds((this.SCREEN_WIDTH/2) +215,this.SCREEN_HEIGHT/2,130,33);
        enterNameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                enterNameButtonPressed();
            }
        } );
        this.add(enterNameButton);

        nameField = new JTextField();
        nameField.setBounds(this.SCREEN_WIDTH/2-(200) ,this.SCREEN_HEIGHT/2,400,33);
        this.add(nameField);
//        JButton button = new JButton("KEK");
//        button.setBounds(200,300,100,50);
//        this.add(button);

    }

    private void enterNameButtonPressed() {
        //String name = nameField.getText();
        this.game.startGame();
        System.out.println("Button pressed");
    }


}
