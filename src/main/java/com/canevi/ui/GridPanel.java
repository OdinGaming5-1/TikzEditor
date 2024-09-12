package com.canevi.ui;

import com.canevi.drawer.CircleDrawer;
import com.canevi.drawer.Drawable;
import com.canevi.drawer.GridDrawer;
import com.canevi.export.Exportable;
import com.canevi.util.Circle;
import com.canevi.util.Coordinate;
import com.canevi.util.Radius;
import com.canevi.util.Size;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class GridPanel extends JPanel {
    private double scale = 1.0; // scale for zooming
    private double offsetX = 0, offsetY = 0; // offsets for panning

    private final MouseHandler mouseHandler;

    private final GridDrawer gridDrawer;
    private final CircleDrawer circleDrawer;

    private final java.util.List<Circle> circles = new ArrayList<>();
    private final java.util.Map<Drawable<?>, java.util.List<Exportable>> toBeInstantiated = new HashMap<>();

    private EditMode editMode;

    public GridPanel() {
        editMode =EditMode.HAND;

        int gridSize = 50;
        gridDrawer = new GridDrawer(gridSize);
        circleDrawer = new CircleDrawer(gridSize);

        mouseHandler = new MouseHandler(this);
        addMouseWheelListener(mouseHandler);
        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Apply scaling and panning
        g2d.scale(scale, scale);

        Size size = new Size(getWidth(), getHeight());
        Coordinate offset = new Coordinate(offsetX, offsetY);

        gridDrawer.draw(g2d, size, offset, java.util.List.of());
        circleDrawer.draw(g2d, size, offset, circles);

        toBeInstantiated.entrySet().stream().filter(e -> e.getValue() != null).forEach(e -> e.getValue().forEach(v ->
            e.getKey().instantiate(g2d, size, offset, mouseHandler.getLastMousePosition(), v)
        ));
        if(toBeInstantiated.get(circleDrawer) != null) {
            toBeInstantiated.get(circleDrawer).forEach(c -> circles.add((Circle) c));
            toBeInstantiated.put(circleDrawer, null);
        }
    }

    public void spawnContextMenu(MouseEvent e) {
        JPopupMenu pm = new JPopupMenu("Message");
        JMenuItem m1 = new JMenuItem("Hand");
        JMenuItem m2 = new JMenuItem("Circle");
        pm.add(m1);
        pm.add(m2);

        m1.addActionListener(_ -> editMode = EditMode.HAND);
        m2.addActionListener(_ -> editMode = EditMode.CIRCLE);

        pm.show(this, e.getX(), e.getY());
    }
    public void zoomIn() {
        scale *= 1.2;
    }

    public void zoomOut() {
        scale *= 0.8;
    }

    public void pan(int deltaX, int deltaY) {
        offsetX += deltaX;
        offsetY += deltaY;
    }

    public EditMode getEditMode() {
        return this.editMode;
    }

    public void addCircle(Coordinate coordinate, Radius radius) {
        Circle circle = new Circle(coordinate, radius);
        if(toBeInstantiated.get(circleDrawer) == null) {
            toBeInstantiated.put(circleDrawer, java.util.List.of(circle));
        } else {
            toBeInstantiated.get(circleDrawer).add(circle);
        }
        repaint();
    }
}
