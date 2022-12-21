package org.example.game;

import java.util.ArrayList;

public class Snake {
    private ArrayList<Cell> snakeCells;
    private Direction direction;
    private final int NUMBER_OF_CELLS_AT_START;

    public Snake(int positionX, int positionY, Direction direction, int numberOfCellsAtStart) {
        this.NUMBER_OF_CELLS_AT_START = numberOfCellsAtStart;
        this.direction = direction;
        this.addCell(positionX, positionY);
    }

    public void addCell(int positionX, int positionY) {
        this.snakeCells.add(new Cell(positionX, positionY));
    }

    public void removeCell() {

    }
}
