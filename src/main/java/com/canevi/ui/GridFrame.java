package com.canevi.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GridFrame extends JFrame {

    public GridFrame()
    {
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GridFrame::new);
    }
}
