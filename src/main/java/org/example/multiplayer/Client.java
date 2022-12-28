package org.example.multiplayer;

import org.example.game.GameGraphicsPanel;
import org.example.game.GameWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client implements ActionListener {

    private Socket socket;
    private OutputStream outputStream;
    private InputStream inputStream;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private GameWindow gameWindow;
    private GameGraphicsPanel gameGraphicsPanel;
    public Client() throws IOException {
        this.socket = new Socket("localhost",7777);
        this.outputStream = socket.getOutputStream();
        this.objectOutputStream = new ObjectOutputStream(this.outputStream);
        this.inputStream = this.socket.getInputStream();
        this.objectInputStream = new ObjectInputStream(inputStream);
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            MessageForClient messageForClient = (MessageForClient) objectInputStream.readObject();
            messageForClient.getObjectsToDraw();

        } catch (IOException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }
}
