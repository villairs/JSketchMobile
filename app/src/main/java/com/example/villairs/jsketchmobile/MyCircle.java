package com.example.villairs.jsketchmobile;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

/**
 * Created by Villairs on 2016-07-08.
 */
public class MyCircle extends MyShape{

    public MyCircle(int x, int y, int r, int w, int h,int thickness, int c){
        color = c;
        this.thickness = thickness;
        this.x = x;
        this.y = y;
        this.r = r;
        this.h=h;
        this.w=w;
        fill = false;
        fillColor= Color.WHITE;
        myPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        myPaint.setStyle(Paint.Style.STROKE);
        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setStyle(Paint.Style.FILL);
    }

    public MyCircle(MyCircle m){
        color = m.getColor();
        fillColor = m.getFillColor();
        thickness = m.getThickness();
        x = m.getX();
        y = m.getY();
        r = m.getR();
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


    // since circles are drawn from the top left corner, need to adjust x, y and radius values
    // so that everything aligns properly

    public void draw(Canvas canvas){
       myPaint.setStrokeWidth(5*thickness);
        myPaint.setColor(color);
        // JOptionPane.showMessageDialog(null,r);
        canvas.drawOval(x-r,y-r,x+r,y+r,myPaint);

        if(fill){
            fillPaint.setColor(fillColor);
            canvas.drawOval(x-r,y-r,x+r,y+r,fillPaint);
        }
        if(selected){
            myPaint.setStrokeWidth(3);
            myPaint.setColor(Color.MAGENTA);
            canvas.drawOval(x-r,y-r,x+r,y+r,myPaint);
        }
    }

    public boolean contains(int x,int y){
        Point p1 = new Point(x,y);
        Point p2 = new Point(this.x,this.y);

        if (r*r >= (((x-this.x)*(x-this.x)) + ((y-this.y)*(y-this.y))))
            return true;
        else
            return false;

    }
}