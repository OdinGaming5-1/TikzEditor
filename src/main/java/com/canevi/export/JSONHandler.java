package com.canevi.export;

import com.canevi.util.Coordinate;
import com.google.gson.Gson;

public class JSONHandler
{
    public static void main(String[] args)
    {
        Gson gson = new Gson();
        Coordinate cor=new Coordinate(1,2);
        String json = gson.toJson(cor);
        System.out.println(json);
    }
}
