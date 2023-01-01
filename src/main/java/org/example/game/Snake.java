package org.example.game;

import org.example.graphics_objects.Cell;
import org.example.graphics_objects.DrawableGameObject;
import org.example.graphics_objects.Rectangle;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Snake {
    private ArrayList<Cell> snakeCells;
    private SnakeDirection snakeDirection;

    private final int NUMBER_OF_CELLS_AT_START;
    private final int CELL_WIDTH;
    private final int CELL_HEIGHT;

    private int numberOfCells;
    private final int SNAKE_POSITION_SHIFT;

    private Color cellColor;

    public Snake(int positionX, int positionY, SnakeDirection snakeDirection, int numberOfCellsAtStart, int cellWidth
            , int cellHeight, Color cellColor, int snakePositionShift) {
        this.NUMBER_OF_CELLS_AT_START = numberOfCellsAtStart;
        this.numberOfCells = 0;
        this.snakeDirection = snakeDirection;
        this.CELL_HEIGHT = cellHeight;
        this.CELL_WIDTH = cellWidth;
        this.snakeCells = new ArrayList<>();
        this.SNAKE_POSITION_SHIFT = snakePositionShift;
        this.cellColor = cellColor;
        this.addCells(positionX, positionY, CELL_WIDTH, CELL_HEIGHT, this.cellColor, this.NUMBER_OF_CELLS_AT_START,
                snakeDirection);
    }

    public DrawableGameObject addCellToTail() {
        int[] shiftOfXYPosition = this.positionShift(this.CELL_WIDTH, this.CELL_HEIGHT);
        int shiftOfCellPositionX = -shiftOfXYPosition[0];
        int shiftOfCellPositionY = -shiftOfXYPosition[1];

       return this.addCells(this.snakeCells.get(numberOfCells-1).getPositionX() + shiftOfCellPositionX,
                this.snakeCells.get(numberOfCells-1).getPositionY() + shiftOfCellPositionY, this.CELL_WIDTH,
                this.CELL_HEIGHT, this.cellColor, 1, this.snakeDirection);
    }
    public DrawableGameObject addCells(int positionX, int positionY, int cellWidth, int cellHeight, Color cellColor,
                        int numberOfCellsToAdd, SnakeDirection snakeDirection) {
        int[] shiftOfXYPosition = this.positionShift(this.CELL_WIDTH, this.CELL_HEIGHT);
        int shiftOfCellPositionX = -shiftOfXYPosition[0];
        int shiftOfCellPositionY = -shiftOfXYPosition[1];
        Cell cell=null;
        for (int i = 0; i < numberOfCellsToAdd; i++)  {
            Random random = new Random();
            Color colorSnake = new Color(random.nextInt(255),random.nextInt(255),
                    random.nextInt(255));
            cell = new Cell(positionX, positionY, cellWidth, cellHeight, colorSnake, new Rectangle());
            this.snakeCells.add(cell);
            positionX += shiftOfCellPositionX;
            positionY += shiftOfCellPositionY;
            this.numberOfCells++;

        }
        return cell;
    }

    public void move() {
        int[] shiftOfXYPosition = this.positionShift(this.CELL_WIDTH, this.CELL_HEIGHT);
        int positionXShift = shiftOfXYPosition[0];
        int positionYShift = shiftOfXYPosition[1];

        for (int i = this.snakeCells.size() - 1; i > 0; i--) {
            this.snakeCells.get(i).setPositionX(this.snakeCells.get(i-1).getPositionX());
            this.snakeCells.get(i).setPositionY(this.snakeCells.get(i-1).getPositionY());
        }
        this.snakeCells.get(0).setPositionX(this.snakeCells.get(0).getPositionX() + positionXShift);
        this.snakeCells.get(0).setPositionY(this.snakeCells.get(0).getPositionY() + positionYShift);

    }

    private int[] positionShift(int shiftX, int shiftY)  {
        int[] shiftOfXYPositions = new int[2];
        int shiftOfCellPositionX = 0;
        int shiftOfCellPositionY = 0;
        switch (snakeDirection) {
            case UP:
                shiftOfCellPositionY = - shiftY;
                break;
            case RIGHT:
                shiftOfCellPositionX = shiftX;
                break;
            case DOWN:
                shiftOfCellPositionY = shiftY;
                break;
            case LEFT:
                shiftOfCellPositionX = - shiftX;
                break;
        }
        shiftOfXYPositions[0] = shiftOfCellPositionX;
        shiftOfXYPositions[1] = shiftOfCellPositionY;
        return shiftOfXYPositions;
    }

    public int getNumberOfCells() {
        return numberOfCells;
    }

    public void setNumberOfCells(int numberOfCells) {
        this.numberOfCells = numberOfCells;
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
