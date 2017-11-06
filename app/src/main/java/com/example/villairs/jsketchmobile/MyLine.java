package com.example.villairs.jsketchmobile;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import java.lang.Math;
/**
 * Created by Villairs on 2016-07-09.
 */
public class MyLine extends MyShape{
    public MyLine(int x, int y,int w, int h, int thickness, int c){
        this.x = x;
        this.y = y;
        this.h = h;
        this.w = w;
        r=0;
        this.thickness = thickness;
        color = c;
        fillColor = Color.WHITE;
        myPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        myPaint.setStyle(Paint.Style.STROKE);
        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setStyle(Paint.Style.FILL);

    }

    public MyLine(MyLine m){

        color = m.getColor();
        fillColor = m.getFillColor();
        thickness = m.getThickness();
        x = m.getX();
        y = m.getY();
        r =0;
        h = m.getH();
        w = m.getW();

        myPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        myPaint.setStyle(Paint.Style.STROKE);
        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setStyle(Paint.Style.FILL);



    }




    public void draw(Canvas c){

        myPaint.setColor(color);
        myPaint.setStrokeWidth(thickness*5);
        c.drawLine(x,y,w,h,myPaint);

        if(selected){
            myPaint.setStrokeWidth(thickness*2);
            myPaint.setColor(Color.MAGENTA);
            c.drawLine(x,y,w,h,myPaint);
        }

    }


    public boolean contains(int x,int y){
        Point p1 = new Point(this.x,this.y);
        Point p2 = new Point(w,h);
        Point p3 = new Point(x,y);
        double dist13 = Math.sqrt(((p1.x-p3.x)*(p1.x-p3.x))  + ((p1.y - p3.y)*(p1.y - p3.y)));
        double dist23 = Math.sqrt(((p2.x-p3.x)*(p2.x-p3.x))  + ((p2.y - p3.y)*(p2.y - p3.y)));
        double dist12 =  Math.sqrt(((p1.x-p2.x)*(p1.x-p2.x))  + ((p1.y - p2.y)*(p1.y - p2.y)));
        double dist = dist13 + dist23 - dist12;
       // double dist = (p1.distance(p3) + p2.distance(p3)) - p1.distance(p2);
        if (dist < (double) thickness*5)
            return true;
        else
            return false;
    }
}