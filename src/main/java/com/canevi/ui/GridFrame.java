package com.canevi.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GridFrame extends JFrame {

    public GridFrame() {
        setupFrame();
    }

    private void setupFrame() {
        setSize(800, 800);
        setTitle("Scalable and Pannable Grid");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GridPanel gridPanel = new GridPanel();
        add(gridPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    static class GridPanel extends JPanel {
        private double scale = 1.0; // scale for zooming
        private int offsetX = 0, offsetY = 0; // offsets for panning
        private int lastX, lastY; // for tracking mouse drag
        private boolean isDragging = false;

        public GridPanel() {
            addMouseWheelListener(new MouseWheelListener() {
                @Override
                public void mouseWheelMoved(MouseWheelEvent e) {
                    if (e.getPreciseWheelRotation() < 0) {
                        scale *= 1.1; // zoom in
                    } else {
                        scale *= 0.9; // zoom out
                    }
                    repaint();
                }
            });

            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    lastX = e.getX();
                    lastY = e.getY();
                    isDragging = true;
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    isDragging = false;
                }
            });

            addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
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
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Apply scaling and panning
            g2d.translate(offsetX, offsetY);
            g2d.scale(scale, scale);

            // Draw the endless grid
            drawGrid(g2d);
        }

        private void drawGrid(Graphics2D g2d) {
            int gridSize = 50; // size of each grid cell

            // Set grid color
            g2d.setColor(Color.LIGHT_GRAY);

            // Get visible area
            int width = getWidth();
            int height = getHeight();

            int scale = 20;

            // Calculate starting points to make the grid appear
            int startX = 0;
            int endX = gridSize * scale;
            int startY = 0;
            int endY = gridSize * scale;

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GridFrame::new);
    }
}
