package org.example.game;

import org.example.graphics.Rectangle;

public class Cell {
    private Rectangle cell;
    private int positionX;
    private int positionY;
    private final int CELL_WIDTH;
    private final int CELL_HEIGHT;
    public Cell(int positionX, int positionY, int cellWidth, int cellHeight, String color) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.CELL_HEIGHT = cellHeight;
        this.CELL_WIDTH = cellWidth;
        this.cell = new Rectangle(this.positionX, this.positionY, this.CELL_WIDTH, this.CELL_HEIGHT, color);
        this.cell.zobraz();
    }
}
