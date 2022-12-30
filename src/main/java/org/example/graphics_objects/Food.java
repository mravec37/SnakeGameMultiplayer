package org.example.graphics_objects;

import java.awt.*;

public class Food implements DrawableGameObject {
    private int positionX;
    private int positionY;
    private int  foodWidth;
    private int foodHeight;
    private Color foodColor;
    private GraphicShape foodShape;
    public Food(int positionX, int positionY, int foodWidth, int foodHeight, Color color, GraphicShape foodShape) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.foodHeight = foodHeight;
        this.foodWidth = foodWidth;
        this.foodShape = foodShape;
        this.foodColor = color;
        this.foodShape.setPositionX(this.positionX);
        this.foodShape.setPositionY(this.positionY);
        this.foodShape.setHeight(this.foodHeight);
        this.foodShape.setWidth(this.foodWidth);
    }

    public int getPositionX() {
        return this.positionX;
    }

    public Color getFoodColor() {
        return foodColor;
    }

    public void setFoodColor(Color foodColor) {
        this.foodColor = foodColor;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
        this.foodShape.setPositionX(this.positionX);
    }

    public int getPositionY() {
        return this.positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
        this.foodShape.setPositionY(this.positionY);

    }

    @Override
    public void setWidth(int width) {
        this.foodWidth = width;
    }

    @Override
    public void setHeight(int height) {
        this.foodHeight = height;
    }

    @Override
    public Graphics drawObject(Graphics g) {
        foodShape.drawShape(g, this.foodColor);
        return g;
    }

    @Override
    public Color getColor() {
        return this.foodColor;
    }

    @Override
    public String getX() {
        return this.positionX + "";
    }

    public int getWidth() {
        return foodWidth;
    }

    public int getHeight() {
        return this.foodHeight;
    }
}
