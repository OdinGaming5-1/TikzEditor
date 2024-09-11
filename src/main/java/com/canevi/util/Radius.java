package com.canevi.util;

public class Radius
{
    private double val;
    public Radius()
    {
        val=0;
    }
    public Radius(double radius)
    {
        assert radius<0; //radius must be positive
        val=radius;
    }
    public double GetRadius()
    {
        return val;
    }
    public void SetRadius(double radius){
        assert radius<0; //radius must be positive
        val=radius;
    }
}
