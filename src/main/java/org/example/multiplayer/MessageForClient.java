package org.example.multiplayer;

import org.example.graphics_objects.DrawableGameObject;

import java.io.Serializable;
import java.util.ArrayList;

public class MessageForClient implements Serializable {
    private ArrayList<DrawableGameObject> objectsToDraw;
    private boolean gameRunning;
    private int clientScore;

    public MessageForClient(ArrayList<DrawableGameObject> objectsToDraw, boolean gameOver, int clientScore) {
       // this.objectsToDraw = (ArrayList<DrawableGameObject>) objectsToDraw.clone();
        this.objectsToDraw = objectsToDraw;
        this.clientScore = clientScore;
        //System.out.println("Objects to draw in message is: " + objectsToDraw.get(1).getX());
        this.gameRunning = gameOver;
    }

    public int getClientScore() {
        return this.clientScore;
    }

    public ArrayList<DrawableGameObject> getObjectsToDraw() {
        return this.objectsToDraw;
    }

    public boolean isGameRunning() {
        return this.gameRunning;
    }
}
