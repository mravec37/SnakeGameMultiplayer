package org.example.game;

import org.example.graphics.Circle;

public class Food {
    private Circle food;
    private int positionX;
    private int positionY;

    public Food(Circle food, int positionX, int positionY) {
        this.food = food;
        this.positionX = positionX;
        this.positionY = positionY;
    }
}
