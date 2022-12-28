package org.example.graphics_objects;

import java.awt.*;

public class Rectangle extends GraphicShape {

    private int positionX;
    private int positionY;
    private int rectangleWidth;
    private int rectangleHeight;

    public Rectangle(int positionX, int positionY, int circleWidth, int circleHeight) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.rectangleWidth = circleWidth;
        this.rectangleHeight = circleHeight;
    }
    public Rectangle() {
    }

    @Override
    public int getWidth() {
        return this.rectangleWidth;
    }

    @Override
    public int getHeight() {
        return this.rectangleHeight;
    }

    @Override
    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    @Override
    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    @Override
    public void setWidth(int width) {
        this.rectangleWidth = width;
    }

    @Override
    public void setHeight(int height) {
        this.rectangleHeight = height;
    }

    @Override
    public Graphics drawShape(Graphics g, Color color) {
        g.setColor(color);
        g.fillRect(this.positionX, this.positionY, this.rectangleWidth, this.rectangleHeight);
        return g;
    }
}
