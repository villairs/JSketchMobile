package com.example.villairs.jsketchmobile;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Villairs on 2016-07-09.
 */
public class MyRectangle extends MyShape{


    public MyRectangle(int x,int y,int w,int h,int thickness, int c){
        this.x=x;
        this.y=y;
        this.h=h;
        this.w=w;
        r=0;
        this.thickness=thickness;
        color=c;
        fillColor = Color.WHITE;
        fill = false;
        myPaint = new Paint();
        myPaint.setStyle(Paint.Style.STROKE);
        fillPaint = new Paint();
        fillPaint.setStyle(Paint.Style.FILL);

    }

    public MyRectangle(MyRectangle m){
        color = m.getColor();
        fillColor = m.getFillColor();
        thickness = m.getThickness();
        x = m.getX();
        y = m.getY();
        r = 0;
        h = m.getH();
        w = m.getW();

        if(m.isFilled() ==1){
            fill = true;
        }
        else
            fill = false;

        myPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        myPaint.setStyle(Paint.Style.STROKE);
        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setStyle(Paint.Style.FILL);
    }





    public void draw(Canvas c){
//System.out.println("rect Drew");
        myPaint.setColor(color);
        myPaint.setStrokeWidth(thickness*5);
        c.drawRect(x,y,w,h,myPaint);
     //   System.out.println( x + " "+y +" " + w + " "+ h + " " + color);
        if(fill){
           fillPaint.setColor(fillColor);
            c.drawRect(x,y,w,h,fillPaint);

        }
        if(selected){
            myPaint.setStrokeWidth(thickness*2);
            myPaint.setColor(Color.MAGENTA);
            c.drawRect(x,y,w,h,myPaint);
        }
    }
    public boolean contains(int x,int y){
        if (this.x < x && x < (this.w) && this.y < y &&  y < (this. h))
            return true;
        else
            return false;
    }


}