package com.canevi.drawer;

import com.canevi.util.Circle;
import com.canevi.util.Coordinate;
import com.canevi.util.Size;

import java.awt.*;

public class CircleDrawer implements Drawable {
    private final int gridSize;
    private Circle circle;
    private Coordinate createdAt = null;

    public CircleDrawer(Circle circle, int gridSize) {
        this.gridSize = gridSize;
        this.circle = circle;
    }

    public static String getName() {
        return CircleDrawer.class.getName();
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
        createdAt = null;
    }

    @Override
    public void draw(Graphics2D g2d, Size size, Coordinate offset, Coordinate last) {
        if(circle.GetRadius().GetRadius()==0) return;
        g2d.setColor(Color.red);
        double radius = circle.GetRadius().GetRadius();
        int positionX = getPosition((createdAt == null ? last : createdAt).GetX(), offset.GetX(), radius);
        int positionY = getPosition((createdAt == null ? last : createdAt).GetY(), offset.GetY(), radius);
        if(createdAt == null) createdAt = last;

        g2d.drawOval(positionX, positionY, (int) (2*radius), (int) (2*radius));
        g2d.drawString("Circle Position:   x: " + positionX + " y: " + positionY, 20, 90);
        g2d.drawString("Created Position:   x: " + createdAt.GetX() + " y: " + createdAt.GetY(), 20, 120);
    }
    private int getPosition(double last, double offset, double radius) {
        double division = getAbsoluteDivision(last + (createdAt == null ? -offset : 0), gridSize);
        double modded = division * gridSize;
        double translated = modded + offset - radius;
        return (int) translated;
    }

    private double getAbsoluteDivision(double value, double divisor) {
        double division = value / divisor;
        double floor = Math.floor(division);
        double ceil = Math.ceil(division);
        return Math.abs(division - floor) > Math.abs(division - ceil) ? ceil : floor;
    }

}
