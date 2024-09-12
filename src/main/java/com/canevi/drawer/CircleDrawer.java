package com.canevi.drawer;

import com.canevi.export.Exportable;
import com.canevi.util.Circle;
import com.canevi.util.Coordinate;
import com.canevi.util.Size;

import java.awt.*;
import java.util.List;

public class CircleDrawer implements Drawable<Circle> {
    private final int gridSize;

    public CircleDrawer(int gridSize) {
        this.gridSize = gridSize;
    }

    private void doProcess(Graphics2D g2d, Coordinate position, Coordinate offset, Circle circle) {
        if(circle.GetRadius().GetRadius()==0) return;
        g2d.setColor(Color.red);
        double radius = circle.GetRadius().GetRadius();
        int positionX = getPosition(position.GetX(), offset.GetX(), radius, gridSize);
        int positionY = getPosition(position.GetY(), offset.GetY(), radius, gridSize);
        g2d.drawOval(positionX, positionY, (int) (2*radius), (int) (2*radius));
        //g2d.drawString("Circle Position:   x: " + positionX + " y: " + positionY, 20, 90);
    }

    @Override
    public void instantiate(Graphics2D g2d, Size size, Coordinate offset, Coordinate last, Exportable drawable) {
        Circle circle = (Circle) drawable;
        circle.SetCoordinate(last);
        doProcess(g2d, last.subtract(offset), offset, circle);
    }

    @Override
    public void draw(Graphics2D g2d, Size size, Coordinate offset, List<Circle> drawableList) {
        drawableList.forEach(d -> doProcess(g2d, d.GetCoordinate(), offset, d));
    }
}
