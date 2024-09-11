package com.canevi.util;

import com.canevi.export.Exportable;

public class Rectangle implements Exportable
{
    private Coordinate start;
    private Coordinate stop;
    private Size size;

    public Rectangle(Coordinate start,Coordinate stop){
        this.start=start;
        this.stop=stop;
        this.size=new Size(stop.GetX()-start.GetX(),stop.GetY()-start.GetY());
    }
    public Rectangle(Coordinate position,Size size){
        this.start=position;
        this.size=size;
        this.stop=new Coordinate(position.GetX()+size.GetWidth(),position.GetY()+size.GetHeight());
    }

    public Coordinate GetStart() {
        return start;
    }

    public Coordinate GetStop() {
        return stop;
    }

    public Size GetSize() {
        return size;
    }

    public void SetStart(Coordinate start) {
        this.start = start;
    }

    public void SetStop(Coordinate stop) {
        this.stop = stop;
    }

    public void SetSize(Size size) {
        this.size = size;
    }


    @Override
    public String export() {
        String str="";
        str+="\\draw ("+start.GetX()+","+start.GetY()+")";
        str+=" rectangle ";
        str+=" ("+stop.GetX()+","+stop.GetY()+")";
        return str;
    }

    public static void main(String[] args){
        Rectangle r=new Rectangle(new Coordinate(1,2),new Coordinate(3,4));
        System.out.println(r.export());
    }
}
