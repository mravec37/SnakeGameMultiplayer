package org.example.graphics_objects;


import java.awt.*;

public class Cell implements DrawableGameObject {
    private int positionX;
    private int positionY;
    private int cellWidth;
    private int cellHeight;
    private GraphicShape cellShape;
    private final Color color;

    public Cell(int positionX, int positionY, int cellWidth, int cellHeight, Color color, GraphicShape cellSHape) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.cellHeight = cellHeight;
        this.cellWidth = cellWidth;
        this.color = color;
        this.cellShape = cellSHape;
        this.cellShape.setPositionX(positionX);
        this.cellShape.setPositionY(positionY);
        this.cellShape.setHeight(this.cellHeight);
        this.cellShape.setWidth(this.cellWidth);

    }

    public int getPositionX() {
        return this.positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
        this.cellShape.setPositionX(positionX);
    }

    public int getPositionY() {
        return this.positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
        this.cellShape.setPositionY(positionY);
    }

    @Override
    public void setWidth(int width) {
        this.cellWidth = width;
        this.cellShape.setWidth(this.cellWidth);
    }

    @Override
    public void setHeight(int height) {
        this.cellHeight = height;
        this.cellShape.setHeight(this.cellHeight);
    }

    @Override
    public Graphics drawObject(Graphics g) {
        this.cellShape.drawShape(g, this.color);
        return g;
    }

    @Override
    public Color getColor() {
        return this.color;
    }

    public int getWidth() {
        return cellWidth;
    }

    public int getHeight() {
        return cellHeight;
    }
    @Override
    public int getX() {
        return this.positionX;
    }
    @Override
    public int getY() {
        return this.positionY;
    }

}
