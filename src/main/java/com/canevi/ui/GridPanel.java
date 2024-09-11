package com.canevi.ui;

import com.canevi.drawer.CircleDrawer;
import com.canevi.drawer.Drawable;
import com.canevi.drawer.GridDrawer;
import com.canevi.util.Circle;
import com.canevi.util.Coordinate;
import com.canevi.util.Radius;
import com.canevi.util.Size;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class GridPanel extends JPanel {
    private double scale = 1.0; // scale for zooming
    private double offsetX = 0, offsetY = 0; // offsets for panning
    private double lastX, lastY; // for tracking mouse drag
    private boolean isDragging = false;

    private final int gridSize = 50;
    private final Map<String, Drawable> drawableMap = new HashMap<>();

    private EditMode editmode;

    public GridPanel() {
        editmode=EditMode.HAND;

        GridDrawer gridDrawer = new GridDrawer(gridSize);
        drawableMap.put(GridDrawer.getName(), gridDrawer);

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
                if(SwingUtilities.isLeftMouseButton(e)) {
                    lastX = e.getX();
                    lastY = e.getY();
                    isDragging = true;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(SwingUtilities.isLeftMouseButton(e)) {
                    if(editmode==EditMode.HAND) {
                        isDragging = false;
                    } else if(editmode==EditMode.CIRCLE) {
                        Circle circle = new Circle(new Coordinate(e.getX()-20,e.getY()-20),new Radius(20));
                        if(drawableMap.containsKey(CircleDrawer.getName())) {
                            CircleDrawer circleDrawer = (CircleDrawer) drawableMap.get(CircleDrawer.getName());
                            circleDrawer.setCircle(circle);
                        } else {
                            CircleDrawer circleDrawer = new CircleDrawer(circle, gridSize);
                            drawableMap.put(CircleDrawer.getName(), circleDrawer);
                        }
                        repaint();
                    }
                }

                if (SwingUtilities.isRightMouseButton(e)) {
                    spawnContextMenu(e);
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if(SwingUtilities.isLeftMouseButton(e) && editmode==EditMode.HAND) {
                    if (isDragging) {
                        int deltaX = e.getX() - (int) lastX;
                        int deltaY = e.getY() - (int) lastY;
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Apply scaling and panning
        g2d.scale(scale, scale);

        drawableMap.forEach((key, value) -> value.draw(g2d, new Size(getWidth(), getHeight()),
                new Coordinate(offsetX, offsetY), new Coordinate(lastX/scale, lastY/scale)));
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
}
