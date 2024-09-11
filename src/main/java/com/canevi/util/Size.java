package com.canevi.util;

public class Size
{
    private double width;
    private double height;

    public Size()
    {
        width=0;
        height=0;
    }
    public Size(double width,double height)
    {
        this.height=height;
        this.width=width;
    }

    public double GetWidth() {
        return width;
    }

    public double GetHeight() {
        return height;
    }

    public void SetWidth(double width) {
        this.width = width;
    }

    public void SetHeight(double height) {
        this.height = height;
    }
}
