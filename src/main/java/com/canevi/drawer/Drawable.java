package com.canevi.drawer;

import com.canevi.util.Coordinate;
import com.canevi.util.Size;

import java.awt.*;

public interface Drawable {
    void draw(Graphics2D g2d, Size size, Coordinate offset, Coordinate last);
}
