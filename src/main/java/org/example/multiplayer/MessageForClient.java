package org.example.multiplayer;

import org.example.graphics_objects.DrawableGameObject;

import java.io.Serializable;
import java.util.ArrayList;

public class MessageForClient implements Serializable {
    private ArrayList<DrawableGameObject> objectsToDraw;
    private boolean gameRunning;
    private int clientScore;

    private String scoreLeader;
    private int scoreLeaderScore;
    private boolean playSoundQue;
    private ArrayList<ClientNameLocation> clientNameLocations;

    public MessageForClient(ArrayList<DrawableGameObject> objectsToDraw, boolean gameOver, int clientScore,
                            String scoreLeader, int scoreLeaderScore, boolean playSoundQue,
                            ArrayList<ClientNameLocation> clientsNamesLocations) {
       // this.objectsToDraw = (ArrayList<DrawableGameObject>) objectsToDraw.clone();
        this.scoreLeader = scoreLeader;
        this.clientNameLocations = clientsNamesLocations;
        this.playSoundQue = playSoundQue;
        this.scoreLeaderScore = scoreLeaderScore;
        this.objectsToDraw = objectsToDraw;
        this.clientScore = clientScore;
        this.gameRunning = gameOver;
    }

    public ArrayList<ClientNameLocation> getClientNameLocations() {
        return clientNameLocations;
    }

    public boolean getPlaySoundQue() {
        return this.playSoundQue;
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
    public String getScoreLeader() {
        return scoreLeader;
    }

    public int getScoreLeaderScore() {
        return scoreLeaderScore;
    }

}
