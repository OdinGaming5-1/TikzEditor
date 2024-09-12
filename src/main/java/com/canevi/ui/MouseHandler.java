package com.canevi.ui;

import com.canevi.util.Coordinate;
import com.canevi.util.Radius;

import javax.swing.*;
import java.awt.event.*;

public class MouseHandler extends MouseAdapter implements MouseMotionListener, MouseWheelListener {
    private final GridPanel gridPanel;
    private double lastX, lastY; // for tracking mouse drag

    public MouseHandler(GridPanel gridPanel) {
        this.gridPanel = gridPanel;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if(gridPanel.getEditMode() == EditMode.HAND) {
            if (e.getPreciseWheelRotation() < 0) {
                gridPanel.zoomIn();
            } else {
                gridPanel.zoomOut();
            }
        }
        gridPanel.repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(SwingUtilities.isLeftMouseButton(e)) {
            lastX = e.getX();
            lastY = e.getY();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(SwingUtilities.isLeftMouseButton(e)) {
            if(gridPanel.getEditMode() == EditMode.CIRCLE) {
                gridPanel.addCircle(new Coordinate(e.getX(), e.getY()), new Radius(20));
            }
        } else if (SwingUtilities.isRightMouseButton(e)) {
            gridPanel.spawnContextMenu(e);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(SwingUtilities.isLeftMouseButton(e) && gridPanel.getEditMode() == EditMode.HAND) {
            int deltaX = e.getX() - (int) lastX;
            int deltaY = e.getY() - (int) lastY;
            gridPanel.pan(deltaX, deltaY);
            lastX = e.getX();
            lastY = e.getY();
            gridPanel.repaint();
        }
    }

    public Coordinate getLastMousePosition() {
        return new Coordinate(lastX, lastY);
    }
}