package org.example.multiplayer;

import org.example.game.SnakeDirection;

import java.io.Serializable;

public class ClientNameLocation implements Serializable {
    private final int clientScore;
    private final int clientSnakeHeadX;
    private final int clientSnakeHeadY;
    private final String clientName;
    private final SnakeDirection snakeDirection;
    public ClientNameLocation(int clientSnakeHeadX, int clientSnakeHeadY, String clientName,
                              SnakeDirection snakeDirection, int clientScore) {
        this.clientSnakeHeadX = clientSnakeHeadX;
        this.clientSnakeHeadY = clientSnakeHeadY;
        this.clientName = clientName;
        this.snakeDirection = snakeDirection;
        this.clientScore = clientScore;
    }

    public int getClientScore() {
        return this.clientScore;
    }

    public SnakeDirection getSnakeDirection() {
        return snakeDirection;
    }

    public int getClientSnakeHeadX() {
        return clientSnakeHeadX;
    }

    public int getClientSnakeHeadY() {
        return clientSnakeHeadY;
    }

    public String getClientName() {
        return clientName;
    }
}
