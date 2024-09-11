package com.canevi.util;

import com.canevi.export.Exportable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PolyLine implements Exportable
{
    private List<Coordinate> coordinates;
    private boolean Cycle;

    public PolyLine()
    {
        this.coordinates = new ArrayList<>();
        this.Cycle=false;
    }
    public void AddLine(Coordinate coordinate)
    {
        this.coordinates.add(coordinate);
    }
    public void Pop()
    {
        this.coordinates.removeLast();
    }
    public Iterator<Coordinate> GetIterator()
    {
        return this.coordinates.iterator();
    }
    public int GetSize()
    {
        return this.coordinates.size();
    }

    public void SetCycle(boolean val){
        this.Cycle=val;
    }
    public boolean GetCycle(){
        return this.Cycle;
    }

    @Override
    public String export() {
        String str="\\draw ";
        for (int i = 0; i < coordinates.size(); i++) {
            Coordinate coordinate = coordinates.get(i);
            if(i==0)
            {
                str += "(" + coordinate.GetX() + "," + coordinate.GetY() + ")";
            }
            else
            {
                str += " -- (" + coordinate.GetX() + "," + coordinate.GetY() + ")";
            }
        }
        str+=";";
        return str;
    }

}
