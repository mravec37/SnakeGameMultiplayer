package org.example.multiplayer;

import org.example.game.SnakeGame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements ActionListener {
    private final ServerSocket serverSocket;
    private final int MAX_CLIENTS;
    public static int connectedClients;
    private SnakeGame snakeGame;
    private boolean gameStarted;
    public Server(ServerSocket serverSocket, int maxClients, SnakeGame snakeGame) {
        this.serverSocket = serverSocket;
        this.gameStarted = false;
        this.MAX_CLIENTS = maxClients;
        this.snakeGame = snakeGame;
        TickTimer.addActionListener(this);
    }

    public void startServer() {

        try {
            while (!serverSocket.isClosed() && connectedClients <= this.MAX_CLIENTS) {
                Socket socket = serverSocket.accept();
                System.out.println("A new client has connected!");
                connectedClients++;
                if (connectedClients == 1 && !this.gameStarted) {
                    this.snakeGame.startGame();
                    this.gameStarted = true;
                }
                ClientHandler clientHandler = new ClientHandler(socket, connectedClients - 1);
                TickTimer.addActionListener(clientHandler);
                this.snakeGame.addClient(clientHandler);
            }

        } catch (IOException e) {
            this.closeServerSocket();
            throw new RuntimeException(e);
        }

    }

    private void closeServerSocket() {
        try {
            if(serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        int maxClients = 4;
        try {
            serverSocket = new ServerSocket(7770);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        final int SCREEN_WIDTH = 1670;
        final int SCREEN_HEIGHT= 900;
        Server server = new Server(serverSocket, maxClients, new SnakeGame(SCREEN_WIDTH, SCREEN_HEIGHT));
        server.startServer();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.snakeGame.runGame();
    }
}
