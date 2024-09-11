package com.canevi.util;

import com.canevi.export.Exportable;

public class Circle implements Exportable
{
    private Radius radius;
    private Coordinate coordinate;

    public Circle(){
        this.coordinate=new Coordinate(0,0);
        this.radius=new Radius(0);
    }
    public Circle(Coordinate c,Radius r){
        this.coordinate=c;
        this.radius=r;
    }

    public void SetRadius(Radius radius) {
        this.radius = radius;
    }

    public void SetCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public Radius GetRadius() {
        return radius;
    }

    public Coordinate GetCoordinate() {
        return coordinate;
    }


    @Override
    public String export() {
        String str="";
        str+="\\draw (";
        str+=this.coordinate.GetX()+","+this.coordinate.GetY()+")";
        str+=" circle ("+this.radius.GetRadius()+");";
        return str;
    }

    public static void main(String[] args)
    {
        Circle c=new Circle(new Coordinate(2,2),new Radius(3));
        System.out.println(c.export());
    }
}
