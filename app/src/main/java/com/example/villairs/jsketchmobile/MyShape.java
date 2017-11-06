package com.example.villairs.jsketchmobile;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Villairs on 2016-07-08.
 */


// Base shape class
// used to make line, rect and circle classes
// each will have their own draw methods and stuff
public class MyShape{
    Paint myPaint,fillPaint;
    boolean fill,selected;
    int thickness,x,y,h,w,r;
    int color, fillColor;

    public MyShape(){}


    // set and get methods
    public void toggleFilled(){
        fill = true;}
    public void setSelected(boolean b){
        selected = b;}

    public void setFillColor(int c){
        fillColor = c;
    }

    public int getFillColor(){
        return fillColor;}

    public int isFilled(){
        if(fill)
            return 1;
        else
            return 0;
    }
    public int getThickness(){
        return thickness;}
    public int getX(){
        return x;}
    public int getY(){
        return y;}
    public int getH(){
        return h;}
    public int getW(){
        return w;}
    public int getR(){
        return r;}
    public int getColor(){
        return color;}

    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y= y;
    }
    public void setW(int w){
        this.w =w;
    }
    public void setH(int h){
        this.h = h;
    }

    // each class will have its own draw and contains methods
    // because they are drawn differently and calculating if it contains a point is different.
    public void draw(Canvas canvas){}

    public boolean contains(int x, int y){
        return false;
    }
}

