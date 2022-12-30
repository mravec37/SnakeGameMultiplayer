package org.example.multiplayer;

import org.example.game.ClientArrowKeyPressed;
import org.example.game.SnakeGame;
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
        private boolean gameRunning;

        private ArrayList<DrawableGameObject> drawableGameObjects;

        private ClientArrowKeyPressed clientArrowKeyPressed;
        private String clientUsername;
        public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
        private final int clientId;
        private static int lastId = 0;
        private boolean gameOver;
        private int clientScore;
        private boolean stopSendingMessagesToClient;
    ClientHandler(Socket socket, int clientId) {
            this.clientId = clientId;
            this.gameRunning = false;
            this.clientScore = 0;
            this.drawableGameObjects = new ArrayList<>();
            this.stopSendingMessagesToClient = false;
            this.clientArrowKeyPressed = ClientArrowKeyPressed.NONE;
            this.gameOver = false;
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
        return gameRunning;
    }

    public void setGameRunning(boolean gameRunning) {
        this.gameRunning = gameRunning;
        if (!gameRunning)
            this.gameOver = true;
    }

    public ArrayList<DrawableGameObject> getDrawableGameObjects() {
        return drawableGameObjects;
    }

    public void setDrawableGameObjects(ArrayList<DrawableGameObject> drawableGameObjects) {
        //this.drawableGameObjects = (ArrayList<DrawableGameObject>) drawableGameObjects.clone();
        this.drawableGameObjects = drawableGameObjects;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.gameRunning) {
            //System.out.println("Action performed at: "+ java.time.LocalTime.now());
            this.sendMessageToClient();
            this.getMessageFromClient();
        } else if (gameOver && !stopSendingMessagesToClient) {
            this.sendMessageToClient();
            stopSendingMessagesToClient = true;
            Server.connectedClients--;
            TickTimer.removeActionListener(this);
            try {
                socket.close();
                //objectInputStream.close();
                //objectOutputStream.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public ClientArrowKeyPressed getClientArrowKeyPressed() {
        return this.clientArrowKeyPressed;
    }

    private void getMessageFromClient() {
        try {
            MessageForServer messageForServer = (MessageForServer) objectInputStream.readObject();
            this.clientArrowKeyPressed = messageForServer.getArrowKeyPressed();
            //this.snakeGame.setArrowkeyPressed(this.clientArrowKeyPressed);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendMessageToClient() {
        try {
            System.out.println("sending message to client");
            MessageForClient messageForClient = new MessageForClient(this.drawableGameObjects, this.gameRunning,
                    this.clientScore);
            System.out.println("Message is: " + messageForClient.isGameRunning() + " Dr Obj: " +
                    messageForClient.getObjectsToDraw());
           // System.out.println("X is: " + messageForClient.getObjectsToDraw().get(1).getX());
            objectOutputStream.writeObject(messageForClient);
            objectOutputStream.reset();
            System.out.println("message sent");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getClientScore() {
        return clientScore;
    }

    public void setClientScore(int clientScore) {
        this.clientScore = clientScore;
    }
}
