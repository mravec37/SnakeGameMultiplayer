package org.example.multiplayer;

import org.example.game.ClientArrowKeyPressed;
import org.example.game.SnakeDirection;
import org.example.graphics_objects.DrawableGameObject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

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
        private static ArrayList<ClientHandler> clients = new ArrayList<>();
        private final int clientId;
        private static int lastId = 0;
        private boolean gameOver;
        private int clientScore;
        private int snakeHeadX;
        private int snakeHeadY;
        private SnakeDirection snakeDirection;
        private String clientName;

        private boolean stopSendingMessagesToClient;
        private boolean playSoundQue;

    public void setSnakeHeadX(int snakeHeadX) {
        this.snakeHeadX = snakeHeadX;
    }

    public void setSnakeHeadY(int snakeHeadY) {
        this.snakeHeadY = snakeHeadY;
    }

    ClientHandler(Socket socket, int clientId) {
            this.clientName = "";
            this.snakeHeadX = 0;
            this.snakeHeadY = 0;
            this.clientId = clientId;
            this.playSoundQue = false;
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
            clients.add(this);
        }

    @Override
    public void run() {

    }
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
            this.stopSendingMessagesToClient = true;
            clients.remove(this);
            Server.connectedClients--;
            TickTimer.removeActionListener(this);
            try {
                objectInputStream.close();
                objectOutputStream.close();
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
            this.clientName = messageForServer.getPlayerName();
            //this.snakeGame.setArrowkeyPressed(this.clientArrowKeyPressed);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private AtomicReference<ClientHandler> getHighestScoreClient() {
        AtomicReference<ClientHandler> highestScoreClient = new AtomicReference<>();
        AtomicInteger highestScore = new AtomicInteger(Integer.MIN_VALUE);
        clients.forEach(client -> {
            if (client.getClientScore() > highestScore.get()) {
                highestScore.set(client.getClientScore());
                highestScoreClient.set(client);
            }
        });
        return highestScoreClient;
    }
    private void sendMessageToClient() {
        try {
            System.out.println("sending message to client");
            AtomicReference<ClientHandler> highestScoreClient = this.getHighestScoreClient();
            MessageForClient messageForClient = new MessageForClient(this.drawableGameObjects, this.gameRunning,
                    this.clientScore, highestScoreClient.get().clientName, highestScoreClient.get().clientScore,
                    this.playSoundQue, this.getClientsNamesLocations());
            System.out.println("Message is: " + messageForClient.isGameRunning() + " Dr Obj: " +
                    messageForClient.getObjectsToDraw());
           // System.out.println("X is: " + messageForClient.getObjectsToDraw().get(1).getX());
            objectOutputStream.writeObject(messageForClient);
            objectOutputStream.reset();
            System.out.println("message sent");
            this.playSoundQue = false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setSnakeDirection(SnakeDirection snakeDirection) {
        this.snakeDirection = snakeDirection;
    }

    private ArrayList<ClientNameLocation> getClientsNamesLocations() {
        ArrayList<ClientNameLocation> clientsNamesLocations = new ArrayList<>();
        clients.forEach(client -> {
            clientsNamesLocations.add(new ClientNameLocation(client.snakeHeadX, client.snakeHeadY, client.clientName,
                    client.snakeDirection));
        });
            return clientsNamesLocations;
    }

    public int getClientScore() {
        return clientScore;
    }

    public void setClientScore(int clientScore) {
        if (clientScore > this.clientScore)
            this.playSoundQue = true;
        this.clientScore = clientScore;
    }
}
