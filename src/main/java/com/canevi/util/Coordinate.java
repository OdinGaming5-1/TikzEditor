package com.canevi.util;

public class Coordinate
{
    private double _x;
    private double _y;

    public Coordinate()
    {
        this._x=0;
        this._y=0;
    }
    public Coordinate(double x,double y)
    {
        this._x=x;
        this._y=y;
    }
    public void SetX(double x)
    {
        this._x=x;
    }
    public void SetY(double y)
    {
        this._y=y;
    }
    public void SetYX(double x,double y)
    {
        this._x=x;
        this._y=y;
    }
    public double GetX()
    {
        return this._x;
    }
    public double GetY()
    {
        return this._y;
    }

    public Coordinate add(Coordinate coordinate) {
        return new Coordinate(this._x + coordinate._x, this._y + coordinate._y);
    }
    public Coordinate subtract(Coordinate coordinate) {
        return new Coordinate(this._x - coordinate._x, this._y - coordinate._y);
    }
}
