package com.canevi.drawer;

import com.canevi.export.Exportable;
import com.canevi.util.Coordinate;
import com.canevi.util.Size;

import java.awt.*;
import java.util.List;

public class GridDrawer implements Drawable<Exportable> {
    private final int gridSize;

    public GridDrawer(int gridSize) {
        this.gridSize = gridSize;
    }

    @Override
    public void instantiate(Graphics2D g2d, Size size, Coordinate offset, Coordinate last, Exportable drawable) {
        // empty for developing purpose
    }

    @Override
    public void draw(Graphics2D g2d, Size size, Coordinate offset, List<Exportable> drawableList) {
        g2d.setColor(Color.red);

        g2d.drawString("Offset:     x: " + offset.GetX() + " y: " + offset.GetY(), 20, 60);

        g2d.setColor(Color.LIGHT_GRAY);

        int startX = (int) offset.GetX();
        int endX = (int) (offset.GetX() + gridSize * (size.GetWidth() % gridSize + 1));
        int startY = (int) offset.GetY();
        int endY = (int) (offset.GetY() + gridSize * (size.GetHeight() % gridSize + 1));

        // Draw vertical lines (endless in X direction)
        for (int i = startX; i < endX + gridSize; i += gridSize) {
            g2d.drawLine(i, startY, i, endY);
        }

        // Draw horizontal lines (endless in Y direction)
        for (int j = startY; j < endY + gridSize; j += gridSize) {
            g2d.drawLine(startX, j, endX, j);
        }
    }
}
