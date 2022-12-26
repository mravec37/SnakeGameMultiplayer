package org.example.game;

import org.example.graphics_objects.Cell;
import org.example.graphics_objects.Rectangle;

import java.awt.*;
import java.util.ArrayList;

public class Snake {
    private ArrayList<Cell> snakeCells;
    private SnakeDirection snakeDirection;

    private final int NUMBER_OF_CELLS_AT_START;
    private final int CELL_WIDTH;
    private final int CELL_HEIGHT;

    private final int SNAKE_POSITION_SHIFT;

    public Snake(int positionX, int positionY, SnakeDirection snakeDirection, int numberOfCellsAtStart, int cellWidth
            , int cellHeight, Color cellColor, int snakePositionShift) {
        this.NUMBER_OF_CELLS_AT_START = numberOfCellsAtStart;
        this.snakeDirection = snakeDirection;
        this.CELL_HEIGHT = cellHeight;
        this.CELL_WIDTH = cellWidth;
        this.snakeCells = new ArrayList<>();
        this.SNAKE_POSITION_SHIFT = snakePositionShift;
        this.addCells(positionX, positionY, CELL_WIDTH, CELL_HEIGHT, cellColor, numberOfCellsAtStart, snakeDirection);
    }

    public void addCells(int positionX, int positionY, int cellWidth, int cellHeight, Color cellColor,
                        int numberOfCellsToAdd, SnakeDirection snakeDirection) {
        int[] shiftOfXYPosition = this.positionShift(this.SNAKE_POSITION_SHIFT);
        int shiftOfCellPositionX = -shiftOfXYPosition[0];
        int shiftOfCellPositionY = -shiftOfXYPosition[1];
        for (int i = 0; i < numberOfCellsToAdd; i++)  {
            this.snakeCells.add(new Cell(positionX, positionY, cellWidth, cellHeight, cellColor,new Rectangle()));
            positionX += shiftOfCellPositionX;
            positionY += shiftOfCellPositionY;

        }
    }
    public void move() {
        int[] shiftOfXYPosition = this.positionShift(this.SNAKE_POSITION_SHIFT);
        int positionXShift = shiftOfXYPosition[0];
        int positionYShift = shiftOfXYPosition[1];

        for (int i = this.snakeCells.size() - 1; i > 0; i--) {
            this.snakeCells.get(i).setPositionX(this.snakeCells.get(i-1).getPositionX());
            this.snakeCells.get(i).setPositionY(this.snakeCells.get(i-1).getPositionY());
        }
        this.snakeCells.get(0).setPositionX(this.snakeCells.get(0).getPositionX() + positionXShift);
        this.snakeCells.get(0).setPositionY(this.snakeCells.get(0).getPositionY() + positionYShift);
        /*this.snakeCells.
        this.snakeCells.forEach(cell -> {
        if(this.snakeCells.get(0) != cell) {
            cell.setPositionX(cell.getPositionX() + positionXShift);
            cell.setPositionY(cell.getPositionY() + positionYShift);
        }
        });*/
    }

    private int[] positionShift(int positionShift) {
        int[] shiftOfXYPositions = new int[2];
        int shiftOfCellPositionX = 0;
        int shiftOfCellPositionY = 0;
        switch (snakeDirection) {
            case UP:
                shiftOfCellPositionY = - positionShift;
                break;
            case RIGHT:
                shiftOfCellPositionX = positionShift;
                break;
            case DOWN:
                shiftOfCellPositionY = positionShift;
                break;
            case LEFT:
                shiftOfCellPositionX = - positionShift;
                break;
        }
        shiftOfXYPositions[0] = shiftOfCellPositionX;
        shiftOfXYPositions[1] = shiftOfCellPositionY;
        return shiftOfXYPositions;
    }

    public ArrayList<Cell> getSnakeCells() {
        return snakeCells;
    }

    public void setSnakeDirection(SnakeDirection snakeDirection) {
        this.snakeDirection = snakeDirection;
    }

    public SnakeDirection getSnakeDirection() {
        return snakeDirection;
    }

    public int getNUMBER_OF_CELLS_AT_START() {
        return NUMBER_OF_CELLS_AT_START;
    }

    public int getCELL_WIDTH() {
        return CELL_WIDTH;
    }

    public int getCELL_HEIGHT() {
        return CELL_HEIGHT;
    }

}
