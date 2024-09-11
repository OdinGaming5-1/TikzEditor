package com.canevi.util;

import com.canevi.export.Exportable;

public class Line implements Exportable
{
    private Coordinate start;
    private Coordinate stop;
    public Line()
    {
    }
    public Line(Coordinate start,Coordinate stop)
    {
        this.start=start;
        this.stop=stop;
    }
    public void SetStart(Coordinate start)
    {
        this.start=start;
    }
    public void SetStop(Coordinate stop)
    {
        this.stop=stop;
    }
    public Coordinate GetStart(){
        return start;
    }
    public Coordinate GetStop(){
        return stop;
    }

    @Override
    public String export() {
        String str="\\draw (";
        str+=this.start.GetX();
        str+=",";
        str+=this.start.GetY();
        str+=") -- (";
        str+=this.stop.GetX();
        str+=",";
        str+=this.stop.GetY();
        str+=");";

        return str;
    }
}
