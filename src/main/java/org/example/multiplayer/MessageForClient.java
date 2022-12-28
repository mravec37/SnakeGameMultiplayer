package org.example.multiplayer;

import org.example.graphics_objects.DrawableGameObject;

import java.io.Serializable;
import java.util.ArrayList;

public class MessageForClient implements Serializable {
    private ArrayList<DrawableGameObject> objectsToDraw;
    private boolean gameOver;

    public MessageForClient(ArrayList<DrawableGameObject> objectsToDraw, boolean gameOver) {
        this.objectsToDraw = objectsToDraw;
        this.gameOver = gameOver;
    }

    public ArrayList<DrawableGameObject> getObjectsToDraw() {
        return this.objectsToDraw;
    }

    public boolean isGameOver() {
        return this.gameOver;
    }
}
