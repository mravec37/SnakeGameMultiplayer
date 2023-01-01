package org.example.multiplayer;

import org.example.game.ClientArrowKeyPressed;

import java.io.Serializable;

public class MessageForServer implements Serializable {
    private ClientArrowKeyPressed arrowKeyPressed;
    private String playerName;
    public MessageForServer(ClientArrowKeyPressed arrowKeyPressed, String playerName) {
        this.arrowKeyPressed = arrowKeyPressed;
        this.playerName = playerName;
    }

    public ClientArrowKeyPressed getArrowKeyPressed() {
        return this.arrowKeyPressed;
    }

    public String getPlayerName() {
        return this.playerName;
    }
}
