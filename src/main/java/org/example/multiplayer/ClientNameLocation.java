package org.example.multiplayer;

import org.example.game.SnakeDirection;

import java.io.Serializable;

public class ClientNameLocation implements Serializable {
    private int clientSnakeHeadX;
    private int clientSnakeHeadY;
    private String clientName;
    private SnakeDirection snakeDirection;
    public ClientNameLocation(int clientSnakeHeadX, int clientSnakeHeadY, String clientName,
                              SnakeDirection snakeDirection) {
        this.clientSnakeHeadX = clientSnakeHeadX;
        this.clientSnakeHeadY = clientSnakeHeadY;
        this.clientName = clientName;
        this.snakeDirection = snakeDirection;
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
