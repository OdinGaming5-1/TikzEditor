package com.canevi.drawer;

import com.canevi.export.Exportable;
import com.canevi.util.Coordinate;
import com.canevi.util.Size;

import java.awt.*;

public interface Drawable<T extends Exportable> {

    default int getPosition(double last, double offset, double radius, int gridSize) {
        double division = getAbsoluteDivision(last, gridSize);
        double modded = division * gridSize;
        double translated = modded + offset - radius;
        return (int) translated;
    }

    default double getAbsoluteDivision(double value, double divisor) {
        double division = value / divisor;
        double floor = Math.floor(division);
        double ceil = Math.ceil(division);
        return Math.abs(division - floor) > Math.abs(division - ceil) ? ceil : floor;
    }

    void instantiate(Graphics2D g2d, Size size, Coordinate offset, Coordinate last, Exportable drawable);
    void draw(Graphics2D g2d, Size size, Coordinate offset, java.util.List<T> drawableList);
}
