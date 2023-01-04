package org.example.multiplayer;

import org.example.game.ClientArrowKeyPressed;
import org.example.game.SnakeDirection;
import org.example.graphics_objects.DrawableGameObject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;


public class ClientHandler implements ActionListener {

        private Socket socket;
        private InputStream inputStream;
        private ObjectInputStream objectInputStream;
        private OutputStream outputStream;
        private ObjectOutputStream objectOutputStream;
        private boolean gameRunning;

        private ArrayList<DrawableGameObject> drawableGameObjects;

        private ClientArrowKeyPressed clientArrowKeyPressed;
        private static ArrayList<ClientHandler> clients = new ArrayList<>();
        private final int clientId;
        private boolean gameOver;
        private int clientScore;
        private int snakeHeadX;
        private int snakeHeadY;
        private SnakeDirection snakeDirection;
        private String clientName;
        private String highestScoreClient;
        private int highestScore;
        private boolean stopSendingMessagesToClient;
        private boolean playSoundCue;
    private ArrayList<ClientNameLocation> clientsNamesLocations;

    public void setSnakeHeadX(int snakeHeadX) {
        this.snakeHeadX = snakeHeadX;
    }

    public void setSnakeHeadY(int snakeHeadY) {
        this.snakeHeadY = snakeHeadY;
    }

    ClientHandler(Socket socket, int clientId) {
            this.clientName = "";
            this.clientsNamesLocations = new ArrayList<>();
            this.snakeHeadX = 0;
            this.snakeHeadY = 0;
            this.clientId = clientId;
            this.playSoundCue = false;
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
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            clients.add(this);
        }

    public int getClientId() {
        return clientId;
    }

    public void setGameRunning(boolean gameRunning) {
        this.gameRunning = gameRunning;
        if (!gameRunning)
            this.gameOver = true;
    }

    public void setDrawableGameObjects(ArrayList<DrawableGameObject> drawableGameObjects) {
        this.drawableGameObjects = drawableGameObjects;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.gameRunning) {
            this.sendMessageToClient();
            this.getMessageFromClient();
        } else if (gameOver && !stopSendingMessagesToClient) {
            this.sendMessageToClient();
            this.stopSendingMessagesToClient = true;
            clients.remove(this);
            Server.connectedClients--;
            TickTimer.removeActionListener(this);
            this.closeCommunication();
        }
    }

    public ClientArrowKeyPressed getClientArrowKeyPressed() {
        return this.clientArrowKeyPressed;
    }

    private void getMessageFromClient() {
        if (this.socket.isConnected()) {
            try {
                MessageForServer messageForServer = (MessageForServer) objectInputStream.readObject();
                this.clientArrowKeyPressed = messageForServer.getArrowKeyPressed();
                this.clientName = messageForServer.getPlayerName();
            } catch (IOException | ClassNotFoundException e) {
                this.closeCommunication();
                throw new RuntimeException(e);
            }
        }
    }

    public void setHighestScoreClient(String name, int highestScore) {
        this.highestScoreClient = name;
        this.highestScore = highestScore;
    }
    private void sendMessageToClient() {
        if (this.socket.isConnected()) {
            try {
                MessageForClient messageForClient = new MessageForClient(this.drawableGameObjects, this.gameRunning,
                        this.clientScore, this.highestScoreClient, this.highestScore,
                        this.playSoundCue, this.clientsNamesLocations);
                objectOutputStream.writeObject(messageForClient);
                objectOutputStream.reset();
                this.playSoundCue = false;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void closeCommunication() {
        try {
        if (this.objectOutputStream != null)
            objectOutputStream.close();
        if (this.objectInputStream != null)
            objectInputStream.close();
        if (this.socket != null)
            this.socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void setSnakeDirection(SnakeDirection snakeDirection) {
        this.snakeDirection = snakeDirection;
    }

    public void setClientsNamesLocations() {
        ArrayList<ClientNameLocation> clientsNamesLocations = new ArrayList<>();
        clients.forEach(client -> {
            clientsNamesLocations.add(new ClientNameLocation(client.snakeHeadX, client.snakeHeadY, client.clientName,
                    client.snakeDirection, client.getClientScore()));
        });
        this.clientsNamesLocations = clientsNamesLocations;
    }

    public int getClientScore() {
        return clientScore;
    }

    public void setClientScore(int clientScore) {
        if (clientScore > this.clientScore)
            this.playSoundCue = true;
        this.clientScore = clientScore;
    }

    public String getClientName() {
        return this.clientName;
    }
}
