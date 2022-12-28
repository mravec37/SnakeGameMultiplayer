package org.example.multiplayer;

import org.example.game.SnakeGame;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final ServerSocket serverSocket;
    private final int MAX_CLIENTS;
    private int connectedClients;
    private SnakeGame snakeGame;

    public Server(ServerSocket serverSocket, int maxClients, SnakeGame snakeGame) {
        this.serverSocket = serverSocket;
        this.MAX_CLIENTS = maxClients;
        this.connectedClients = 0;
        this.snakeGame = snakeGame;
    }

    public void startServer() {

        try {
            while (!serverSocket.isClosed() && this.connectedClients < this.MAX_CLIENTS) {
                Socket socket = serverSocket.accept();                //vytvori novy socket urceny na komunikaciu so socketom klienta
                System.out.println("A new client has connected!");
                if (connectedClients == 0)
                    this.snakeGame.startGame();
                this.connectedClients++;
                ClientHandler clientHandler = new ClientHandler(socket, this.connectedClients);
                this.snakeGame.addClient(clientHandler);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void closeServerSocket() {
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
            serverSocket = new ServerSocket(1208);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        final int SCREEN_WIDTH = 1670;
        final int SCREEN_HEIGHT= 900;
        Server server = new Server(serverSocket, maxClients, new SnakeGame(SCREEN_WIDTH, SCREEN_HEIGHT));
        server.startServer();
    }
}
