package com.canevi.drawer;

import com.canevi.util.Coordinate;
import com.canevi.util.Size;

import java.awt.*;

public class GridDrawer implements Drawable {
    private final int gridSize;

    public GridDrawer(int gridSize) {
        this.gridSize = gridSize;
    }

    public static String getName() {
        return GridDrawer.class.getName();
    }

    @Override
    public void draw(Graphics2D g2d, Size size, Coordinate offset, Coordinate last) {
        g2d.setColor(Color.red);

        g2d.drawString("Last:       x: " + last.GetX() + " y: " + last.GetY(), 20, 30);
        g2d.drawString("Offset:     x: " + offset.GetX() + " y: " + offset.GetY(), 20, 60);

        // Set grid color
        g2d.setColor(Color.LIGHT_GRAY);

        // Calculate starting points to make the grid appear
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
