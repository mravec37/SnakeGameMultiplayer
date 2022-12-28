package org.example.graphics_objects;

import java.awt.*;

public class Circle extends GraphicShape {

    private int positionX;
    private int positionY;
    private int circleWidth;
    private int circleHeight;

    public Circle(int positionX, int positionY, int circleWidth, int circleHeight) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.circleWidth = circleWidth;
        this.circleHeight = circleHeight;
    }
    public Circle(){}

    @Override
    public int getWidth() {
        return this.circleWidth;
    }

    @Override
    public int getHeight() {
        return this.circleHeight;
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
        this.circleWidth = width;
    }

    @Override
    public void setHeight(int height) {
        this.circleHeight = height;
    }

    @Override
    public Graphics drawShape(Graphics g, Color color) {
        g.setColor(color);
        g.fillOval(this.positionX, this.positionY, this.circleWidth, this.circleHeight);
        return g;
    }
}
