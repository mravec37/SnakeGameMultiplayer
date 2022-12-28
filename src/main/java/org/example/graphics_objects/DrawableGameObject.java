package org.example.graphics_objects;

import java.awt.*;

public interface DrawableGameObject<T> {
    public abstract int getWidth();
    public abstract int getHeight();
    public abstract void setPositionX(int positionX);
    public abstract void setPositionY(int positionY);
    public abstract void setWidth(int width);
    public abstract void setHeight(int height);

    public abstract Graphics drawObject(Graphics g);
}
