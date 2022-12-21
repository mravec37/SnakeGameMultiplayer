package org.example.game;

import org.example.graphics.Rectangle;

public class Cell {
    private Rectangle cell;
    private int positionX;
    private int positionY;

    public Cell(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }
}
