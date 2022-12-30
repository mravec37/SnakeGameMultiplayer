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

    public Server(ServerSocket serverSocket, int maxClients, SnakeGame snakeGame) {
        this.serverSocket = serverSocket;
        this.MAX_CLIENTS = maxClients;
        this.connectedClients = 0;
        this.snakeGame = snakeGame;
        TickTimer.addActionListener(this);
    }

    public void startServer() {

        try {
            while (!serverSocket.isClosed() && this.connectedClients <= this.MAX_CLIENTS) {
                Socket socket = serverSocket.accept();                //vytvori novy socket urceny na komunikaciu so socketom klienta
                System.out.println("A new client has connected!");
                this.connectedClients++;
                if (connectedClients == 1)
                    this.snakeGame.startGame();
                ClientHandler clientHandler = new ClientHandler(socket, this.connectedClients - 1);
                TickTimer.addActionListener(clientHandler);
                this.snakeGame.addClient(clientHandler);
                //Thread thread = new Thread(clientHandler);
                //thread.start();
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
        //System.out.println("Server Action performed at: "+ java.time.LocalTime.now());
    }
}
