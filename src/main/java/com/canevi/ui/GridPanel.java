package com.canevi.ui;

import com.canevi.util.Circle;
import com.canevi.util.Coordinate;
import com.canevi.util.Radius;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GridPanel extends JPanel {
    private double scale = 1.0; // scale for zooming
    private int offsetX = 0, offsetY = 0; // offsets for panning
    private int lastX, lastY; // for tracking mouse drag
    private boolean isDragging = false;

    private final int gridSize = 50;

    private EditMode editmode;

    /* OBJECTS */
    private Circle circle;

    public GridPanel() {
        editmode=EditMode.HAND;
        circle=new Circle();

        addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if(editmode==EditMode.HAND) {
                    if (e.getPreciseWheelRotation() < 0) {
                        scale *= 1.2; // zoom in
                    } else {
                        scale *= 0.8; // zoom out
                    }
                    repaint();
                }
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(editmode==EditMode.HAND) {
                    lastX = e.getX();
                    lastY = e.getY();
                    isDragging = true;
                    repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(editmode==EditMode.HAND)
                {
                    isDragging = false;
                }
                else if(editmode==EditMode.CIRCLE)
                {
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        circle = new Circle(new Coordinate(e.getX() - 20, e.getY() - 20), new Radius(20));
                        repaint();
                    }
                }

                if (SwingUtilities.isRightMouseButton(e)) {
                    //right click
                    spawnContextMenu(e);
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if(editmode==EditMode.HAND) {
                    if (isDragging) {
                        int deltaX = e.getX() - lastX;
                        int deltaY = e.getY() - lastY;
                        offsetX += deltaX;
                        offsetY += deltaY;
                        lastX = e.getX();
                        lastY = e.getY();
                        repaint();
                    }
                }
            }
        });
    }

    private void spawnContextMenu(MouseEvent e) {
        System.out.println("Right click!");

        JPopupMenu pm = new JPopupMenu("Message");
        // create menuItems
        JMenuItem m1 = new JMenuItem("Hand");
        JMenuItem m2 = new JMenuItem("Circle");
        pm.add(m1);
        pm.add(m2);

        m1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("Hand");
                editmode=EditMode.HAND;
            }
        });
        m2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("Circle");
                editmode=EditMode.CIRCLE;
            }
        });

        pm.show(this,e.getX(),e.getY());

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Apply scaling and panning
        //g2d.translate(offsetX, offsetY);
        g2d.scale(scale, scale);

        // Draw the endless grid
        drawGrid(g2d);
        //drawDrawCircleOnClick(g2d);
        if(circle.GetRadius().GetRadius()!=0)
        {
            g2d.setColor(Color.red);
            g2d.drawOval((int) circle.GetCoordinate().GetX(), (int)circle.GetCoordinate().GetY(), 2*(int)circle.GetRadius().GetRadius(), 2*(int)circle.GetRadius().GetRadius());
        }
    }

    private int getPosition(int last, int offset, int radius) {
        double division = (double) (last) / gridSize;
        double floor = Math.floor(division);
        double ceil = Math.ceil(division);
        division = Math.abs(division - floor) > Math.abs(division - ceil) ? ceil : floor;
        int modded = (int) (division * gridSize);
        int translated = modded + (offset % (2 * radius)) - radius;
        return (int) (translated * scale);
    }

    private void drawDrawCircleOnClick(Graphics2D g2d) {
        g2d.setColor(Color.red);
        int radius = 25;
        int positionX = getPosition(lastX, offsetX, radius);
        int positionY = getPosition(lastY, offsetY, radius);

        g2d.drawOval(positionX, positionY, 2*radius, 2*radius);
        g2d.drawString("Last:       x: " + lastX + " y: " + lastY, 20, 30);
        g2d.drawString("Offset:     x: " + offsetX + " y: " + offsetY, 20, 60);
        g2d.drawString("Position:   x: " + positionX + " y: " + positionY, 20, 90);
    }

    private void drawGrid(Graphics2D g2d) {
        // size of each grid cell

        // Set grid color
        g2d.setColor(Color.LIGHT_GRAY);

        // Get visible area
        int width = getWidth();
        int height = getHeight();

        // Calculate starting points to make the grid appear
        int startX = offsetX;
        int endX = gridSize * (width % gridSize + 1);
        int startY = offsetY;
        int endY = gridSize * (height % gridSize + 1);

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
