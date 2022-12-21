package org.example.game;

import java.util.ArrayList;

public class Snake {
    private ArrayList<Cell> snakeCells;
    private Direction snakeDirection;
    private final int NUMBER_OF_CELLS_AT_START;
    private final int CELL_WIDTH;
    private final int CELL_HEIGHT;

    public Snake(int positionX, int positionY, Direction snakeDirection, int numberOfCellsAtStart, int cellWidth
            , int cellHeight, String cellColor) {
        this.NUMBER_OF_CELLS_AT_START = numberOfCellsAtStart;
        this.snakeDirection = snakeDirection;
        this.CELL_HEIGHT = cellHeight;
        this.CELL_WIDTH = cellWidth;
        this.snakeCells = new ArrayList<>();
        this.addCell(positionX, positionY, CELL_WIDTH, CELL_HEIGHT, cellColor, numberOfCellsAtStart, snakeDirection);
    }

    public void addCell(int positionX, int positionY, int cellWidth, int cellHeight, String cellColor,
                        int numberOfCellsToAdd, Direction snakeDirection) {
        int[] shiftOfXYPosition = this.positionShift();
        int shiftOfCellPositionX = -shiftOfXYPosition[0];
        int shiftOfCellPositionY = -shiftOfXYPosition[1];
        for (int i = 0; i < numberOfCellsToAdd; i++)  {
            this.snakeCells.add(new Cell(positionX, positionY, cellWidth, cellHeight, cellColor));
            positionX += shiftOfCellPositionX;
            positionY += shiftOfCellPositionY;

        }

    }

    public void move() {
        int[] shiftOfXYPosition = this.positionShift();
        int positionXShift = shiftOfXYPosition[0];
        int positionYShift = shiftOfXYPosition[1];
        for(Cell cell : snakeCells) {
            //kazdej celle posun suradnice
        }
    }

    private int[] positionShift() {
        int[] shiftOfXYPositions = new int[2];
        int shiftOfCellPositionX = 0;
        int shiftOfCellPositionY = 0;
        switch (snakeDirection) {
            case UP:
                shiftOfCellPositionY = -this.CELL_HEIGHT;
                break;
            case RIGHT:
                shiftOfCellPositionX = this.CELL_WIDTH;
                break;
            case DOWN:
                shiftOfCellPositionY = this.CELL_HEIGHT;
                break;
            case LEFT:
                shiftOfCellPositionX = -this.CELL_WIDTH;
                break;
        }
        shiftOfXYPositions[0] = shiftOfCellPositionX;
        shiftOfXYPositions[1] = shiftOfCellPositionY;
        return shiftOfXYPositions;
    }

    public void removeCell() {

    }
}
