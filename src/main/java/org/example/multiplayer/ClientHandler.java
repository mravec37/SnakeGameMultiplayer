package org.example.multiplayer;

import org.example.graphics_objects.DrawableGameObject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable, ActionListener {

        private Socket socket;
        private InputStream inputStream;
        private ObjectInputStream objectInputStream;
        private OutputStream outputStream;
        private ObjectOutputStream objectOutputStream;
        private boolean gameOver;

        private ArrayList<DrawableGameObject> drawableGameObjects;


    private String clientUsername;
        public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
       // private Snake snake;
        private final int clientId;
        private static int lastId = 0;

    ClientHandler(Socket socket, int clientId) {
            this.clientId = clientId;
            this.gameOver = false;
            this.drawableGameObjects = new ArrayList<>();
            try {
                this.socket = socket;
                this.inputStream = this.socket.getInputStream();
                this.objectInputStream = new ObjectInputStream(inputStream);
                this.outputStream = this.socket.getOutputStream();
                this.objectOutputStream = new ObjectOutputStream(outputStream);
                //this.snake = this.setUpSnake();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

//    private Snake setUpSnake() {
//
//    }

    @Override
    public void run() {

    }

//    public Snake getSnake() {
//           // return this.snake;
//    }

    public String getClientUsername() {
        return clientUsername;
    }

    public void setClientUsername(String clientUsername) {
        this.clientUsername = clientUsername;
    }

    public int getClientId() {
        return clientId;
    }
    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public ArrayList<DrawableGameObject> getDrawableGameObjects() {
        return drawableGameObjects;
    }

    public void setDrawableGameObjects(ArrayList<DrawableGameObject> drawableGameObjects) {
        this.drawableGameObjects = drawableGameObjects;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.sendMessageToClient();
    }

    private void sendMessageToClient() {
        try {
            objectOutputStream.writeObject(new MessageForClient(this.drawableGameObjects, this.gameOver));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
