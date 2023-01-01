package org.example.multiplayer;

import org.example.game.ClientArrowKeyPressed;
import org.example.game.GameGraphicsPanel;
import org.example.game.GameWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.InetAddress;

public class Client implements ActionListener {

    private Socket socket;
    private OutputStream outputStream;
    private InputStream inputStream;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private GameWindow gameWindow;
    private GameGraphicsPanel gameGraphicsPanel;
    private ClientArrowKeyPressed arrowKeyPressed;
    private boolean playerPlaying;
    public Client() throws IOException {
        this.playerPlaying = false;
        this.gameGraphicsPanel = new GameGraphicsPanel();
        this.gameWindow = new GameWindow(this.gameGraphicsPanel);
        TickTimer.addActionListener(this);
        //this.waitForTicks();
    }

    private void waitForTicks() {
        while(socket.isConnected()) {
            this.readMessageFromServer();
            this.sendMessageToServer();
        }
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.gameGraphicsPanel.isPlayerReady()) {
            this.connectClientToServer();
            this.gameGraphicsPanel.setPlayerReady(false);
            System.out.println("Player is ready");
        }
        if (this.playerPlaying) {
            this.readMessageFromServer();
            this.sendMessageToServer();
        }
    }

    private void updateGameGraphicsPanel(MessageForClient messageForClient) {
        this.gameGraphicsPanel.setObjectsToDraw(messageForClient.getObjectsToDraw());
        this.gameGraphicsPanel.setScore(messageForClient.getClientScore());
        this.gameGraphicsPanel.setGameRunning(messageForClient.isGameRunning());
        this.gameGraphicsPanel.setHighestScorePlayer(messageForClient.getScoreLeader());
        this.gameGraphicsPanel.setHighestScorePlayerScore(messageForClient.getScoreLeaderScore());
        this.gameGraphicsPanel.setPlaySoundQue(messageForClient.getPlaySoundQue());
        this.gameGraphicsPanel.setClientsNamesLocations(messageForClient.getClientNameLocations());
    }
    private void readMessageFromServer() {

        System.out.println("Reading message");
        try {
           MessageForClient messageForClient = (MessageForClient) objectInputStream.readObject();
            //System.out.println("X is: " + messageForClient.getObjectsToDraw().get(1).getX());
            System.out.println("Game running is: " + messageForClient.isGameRunning());
            System.out.println("Drawable objects are " + messageForClient.getObjectsToDraw().toString());
            System.out.println("Message read");
            //if (messageForClient.getObjectsToDraw() != null)
            this.updateGameGraphicsPanel(messageForClient);
            if (!messageForClient.isGameRunning()) {
                this.playerPlaying = false;
                this.gameGraphicsPanel.setGameRunning(false);
                this.disconnectClient();
            } else {
                this.gameGraphicsPanel.setGameStarted(true);
            }
            this.gameGraphicsPanel.paintGraphics();
            this.arrowKeyPressed = this.gameGraphicsPanel.getArrowKeyPressed();
        } catch (IOException e) {
           throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private void disconnectClient() throws IOException {
        //this.socket.close();
        this.outputStream.close();
        this.objectOutputStream.close();
        this.inputStream.close();
        this.objectInputStream.close();
    }

    private void sendMessageToServer() {
        if (this.playerPlaying) {
            MessageForServer messageForServer = new MessageForServer(this.arrowKeyPressed,
                    this.gameGraphicsPanel.getPlayerName());
            try {
                this.objectOutputStream.writeObject(messageForServer);
                objectOutputStream.reset();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

//    public void setMessageForClient(MessageForClient messageForClient) {
//        this.messageForClient = messageForClient;
//    }


    private void connectClientToServer() {
        try {
            System.out.println("Connecting to server");
            this.playerPlaying = true;
             this.socket = new Socket("192.168.1.36",7770);
//            byte[] address = new byte[]{10, 64, 17, 89};
//            InetAddress serverAddress = InetAddress.getByAddress(address);
//            InetAddress serverAddress = InetAddress.getByName("178.143.44.155");
//            InetAddress localAddress= InetAddress.getByName("192.168.1.36");
//            this.socket = new Socket(serverAddress, 1637, localAddress, 7701);
            //this.socket = new Socket("10.64.17.89, 3201", 3201, "192.168.1.36", 7777);
            this.outputStream = socket.getOutputStream();
            this.objectOutputStream = new ObjectOutputStream(this.outputStream);
            this.inputStream = this.socket.getInputStream();
            this.objectInputStream = new ObjectInputStream(inputStream);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    }
