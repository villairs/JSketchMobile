package com.example.villairs.jsketchmobile;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;

import android.widget.LinearLayout;


import java.util.ArrayList;

/**
 * Created by Villairs on 2016-07-09.
 */
public class MyCanvas extends LinearLayout {

    // curcolor,linesize, other and all the x,y variables are recieved from the vector drawer class
    // drawing shapes and everything done here
    // curcolor = currently selected color
    // linesize = ''             ''   line thickness
    //  other =    ''            ''   shape or pointer thing
    long startTime,endTime,time;
    boolean shadow;
    MyShape sShape,selected;
   int curColor;
   int selectedColor;
    int lineSize,other,x,y,w,h,r,xP,yP,xR,yR,sSize;
    boolean hasSelected;
    boolean isMoving;
    JSketch j;
    Canvas canvas;
    Bitmap b;
    ArrayList<MyShape> shapeList;
    Paint bitPaint;
    ArrayList<ArrayList<MyShape>> undoList;
    ArrayList<ArrayList<MyShape>> redoList;

    public MyCanvas(Context c, AttributeSet a){
        super(c,a);
        isMoving = false;
        undoList = new ArrayList<ArrayList<MyShape>>();
        redoList =new ArrayList<ArrayList<MyShape>>();
        shapeList = new ArrayList<MyShape>();

        hasSelected = false;
        bitPaint = new Paint(Paint.DITHER_FLAG);
        b = Bitmap.createBitmap(600,600, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(b);
        shadow = false;
        setWillNotDraw(false);
        //setRetainInstance(true);
    }

    public void setJSketch(JSketch m){
        j = m;
    }


    protected void onSizeChanged(int w, int h, int ow, int oh) {

        super.onSizeChanged(w, h, ow, oh);
       b = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
       canvas = new Canvas(b);
        invalidate();
    }



    public boolean onTouchEvent(MotionEvent e) {
        int xi = (int)e.getX();
        int yi = (int)e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
               setPressedCoord(xi,yi);
                if(other ==1||other==3||other==4)
                {

                    click(xi,yi);
                invalidate();
                    startTime = System.nanoTime();
                }
                else{
                    shadow = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(shadow){
                    setReleasedCoord(xi,yi);
                    drawShadow();
                invalidate();}
                else if(hasSelected){
                    if(isMoving){
                        saveState();
                        isMoving = false;
                    }
                calcMove();
                doMove(xi,yi);
                }
                break;
            case MotionEvent.ACTION_UP:
                setReleasedCoord(xi,yi);
               // System.out.println("release works");
                if(other ==2||other ==6||other==5){
                    shadow = false;
                    saveState();
                drawShape();
                invalidate();}
                else{
                    endTime = System.nanoTime();
                    time = endTime - startTime;
                    if(time >= 500000000 && other ==4){
                       eraseAll();
                    }
                                    }
                break;
        }
        return true;
    }

    public void saveState(){
        if(undoList.size() ==5){
            undoList.remove(0);
        }
        ArrayList<MyShape> temp = new ArrayList<MyShape>();
        for(int i = 0; i < shapeList.size(); i++){
            MyShape tempShape;
            if(shapeList.get(i) instanceof MyCircle)
            {
                tempShape = new MyCircle((MyCircle)shapeList.get(i));
                temp.add(tempShape);
            }
            else if(shapeList.get(i) instanceof MyRectangle){
                tempShape = new MyRectangle((MyRectangle)shapeList.get(i));
                temp.add(tempShape);
            }
            else if(shapeList.get(i) instanceof MyLine){
                tempShape = new MyLine((MyLine)shapeList.get(i));
                temp.add(tempShape);
            }


        }
        undoList.add(temp);
    }
public void eraseAll(){
    if (other == 4){
        saveState();
        shapeList.clear();
        invalidate();
    }

}
    public void undo(){
        if(undoList.size()==0){}
        else{
            if(redoList.size() == 5){
                redoList.remove(0);
            }
            redoList.add(shapeList);
            shapeList = undoList.get(undoList.size()-1);
            undoList.remove(undoList.size()-1);
            invalidate();
        }
    }
    public void redo(){

        if(redoList.size()==0){}
        else{
            if(undoList.size() == 5){
                undoList.remove(0);
            }
            undoList.add(shapeList);
            shapeList = redoList.get(redoList.size()-1);
            redoList.remove(redoList.size()-1);
            deselect();
            invalidate();
        }


    }
    public void clearRedoList(){
        redoList.clear();
    }

public void calcMove(){
    if(selected instanceof MyRectangle){
        xR = Math.abs(selected.getX() - selected.getW());
        xR = xR/2;
        yR = Math.abs(selected.getY() - selected.getH());
        yR = yR/2;
    }
    else if( selected instanceof MyCircle){


    }
    else if(selected instanceof MyLine){
      xR =   selected.getX() - selected.getW();
      yR =selected.getY() - selected.getH();
        xR = xR/2;
        yR = yR/2;
    }
}
public void doMove(int x, int y){
    if(selected instanceof MyRectangle){
        selected.setX(x-xR);
        selected.setW(x+xR);
        selected.setY(y-yR);
        selected.setH(y+yR);
    }
    else if(selected instanceof MyCircle){
        selected.setY(y);
        selected.setX(x);
    }
    else if(selected instanceof MyLine){
        selected.setX(x-xR);
        selected.setY(y-yR);
        selected.setW(x+xR);
        selected.setH(y+yR);
    }
    invalidate();
}

    //getting the pressed and released coordinates from the mouse handler
    public void setPressedCoord(int x, int y){
        xP = x;
        yP = y;
    }

    public void setReleasedCoord(int x, int y){
        xR=x;
        yR=y;
    }

    // calculates the shape
    // since shapes are drawn from the top left corner, its important to note if the mouse released coords is to the north west of the mouse pressed coords
    // so shapes get drawn properly
    //note for circles, the mouse pressed indicates the center of the circle, distance from pressed to released dictates the radius
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
      //  canvas.save();
        canvas.drawBitmap(b, 0, 0,bitPaint);
        repaint();
//canvas.restore();
    }

    public void calcShape(){
        if(other == 5){
            if (xR < xP){
                x = xR;
                w = xP;
            }
            else {
                x = xP;
                w = xR;}
            if (yR < yP){
                y= yR;
                h = yP;}
            else {
                y= yP;
                h = yR;}

         //   w = Math.abs(xP-xR);
         //   h = Math.abs(yP-yR);


        }
        else if (other ==6){

            x = xP;
            y = yP;
            r = (int)Math.sqrt(((xP-xR)*(xP-xR)) + ((yP-yR)*(yP-yR)));
//JOptionPane.showMessageDialog(null,r);
            w = xR;
            h = yR;
        }



        else if (other ==2)
        {
            x = xP;
            y = yP;
            w = xR;
            h = yR;
        }

    }

    public void setColor(int c){
        curColor = c;
       // System.out.println("Change COlor works");
    }

    public void setLineSize(int i){          //FOR SIZE 1 is smallest 4 is largest.
        lineSize = i;
    }
    public void setOther(int i)
    {             //FOR OTHER, 1 = mouse, 2 = eraser, 3 = fill, 4 = rectangle, 5 = circle, 6 = line
    if (other == 3 && i != 3){
        deselect();
    }
        other = i;
    }

    public void clear(){
       canvas.drawColor(0);
        b.eraseColor(Color.WHITE);
    }


    //erase the most recently created shape that contains point (x,y)
    public void erase(int x, int y){
        for(int i = shapeList.size() - 1; i >=0; i--){
            if (shapeList.get(i).contains(x,y))
            {
                clear();
                shapeList.remove(i);
               invalidate();
                break;
            }

        }

    }
    public void repaint(){
        clear();
        for (int i = 0; i < shapeList.size();i++){
            shapeList.get(i).draw(canvas);
        }
        if(shadow){
            drawShadow();
        }
    }

    public void setSketchColor(int c){
        if (c == Color.RED){
            j.red.performClick();
        }
        else if (c == Color.GREEN){
            j.green.performClick();
        }
        else if(c == Color.BLUE){
            j.blue.performClick();
        }

    }

    public void setSketchSize(int c){
        if (c ==1){
            j.thin.performClick();
        }
        else if(c==2){
            j.med.performClick();
        }
        else if(c==3){
            j.large.performClick();
        }
    }






    //select most recently created shape at point x,y
    public void select(int x, int y){
        for(int i = shapeList.size() - 1; i >=0; i--){
            if (shapeList.get(i).contains(x,y))
            {
                if(hasSelected)
                    deselect();
                hasSelected = true;
                isMoving = true;
                selected = shapeList.get(i);
                selected.setSelected(true);
                selectedColor = selected.getColor();
                setSketchColor(selectedColor);
                sSize =selected.getThickness();
                setSketchSize(sSize);
                clear();
               invalidate();
                break;
            }

        }
    }
    public void deselect(){
     //   for (int i = 0; i < shapeList.size();i++){
         //   if(shapeList.get(i).selected){
              //  shapeList.get(i).setSelected(false);
        if(hasSelected){
                isMoving = false;
                selected.setSelected(false);
                clear();
                invalidate();
                hasSelected = false;}
                //break;}
         //   }
     //   }

    }

    //handles mouse clicked events
    public void click(int x, int y){
        if(other == 3){
            select(x,y);
        }
        else if(other == 4){
            saveState();
            erase(x,y);
        }
        else if (other ==1){
            saveState();
            fill(x,y);
        }
    }
    //fill shapes
    public void fill(int x, int y){
        for(int i = shapeList.size() - 1; i >=0; i--){
            if (shapeList.get(i).contains(x,y))
            {
                clear();
                shapeList.get(i).toggleFilled();
                shapeList.get(i).setFillColor(curColor);
                repaint();
                break;
            }

        }
    }





    //where actual drawing occurs,
    // called after all information is gathered
    public void drawShape(){
        if(other == 5){
            calcShape();
            MyRectangle m = new MyRectangle(x,y,w,h,lineSize,curColor);
          //  System.out.println("Rect created");
            m.draw(canvas);
            shapeList.add(m);
        }
        else if(other == 6){
            calcShape();
            // JOptionPane.showMessageDialog(null,r);
            MyCircle m = new MyCircle(x,y,r,w,h, lineSize, curColor);
            m.draw(canvas);
            shapeList.add(m);
        }
        else if (other ==2){
            calcShape();
            MyLine m = new MyLine(x,y,w,h,lineSize,curColor);
            m.draw(canvas);
            shapeList.add(m);
        }
    }
    public void drawShadow(){
        if(other == 5){
            calcShape();
            MyRectangle m = new MyRectangle(x,y,w,h,lineSize,Color.BLACK);
            //  System.out.println("Rect created");
            m.draw(canvas);
            sShape = m;
        }
        else if(other == 6){
            calcShape();
            // JOptionPane.showMessageDialog(null,r);
            MyCircle m = new MyCircle(x,y,r,w,h, lineSize, Color.BLACK);
            m.draw(canvas);
            sShape = m;
        }
        else if (other ==2){
            calcShape();
            MyLine m = new MyLine(x,y,w,h,lineSize,Color.BLACK);
            m.draw(canvas);
           sShape = m;
        }
    }

    // resets the canvas
    public void newFile(){
        shapeList.clear();
        clear();

    }




    // load from a file

    // get input from a input dialog
    //input comes in the form
    // a number indicating how many shapes
    // each shape is on a separate line, a shape has 10 ints detailing what it is
    // read line then split string,
    // parse ints then make shapes

/*
    public void load(){
        newFile();
        String name = JOptionPane.showInputDialog(null,"Input the name of the save file you want to load.");
        try{
            BufferedReader input = new BufferedReader(new FileReader(name));
            String s = input.readLine();
            //JOptionPane.showMessageDialog(null,s);
            int loopSize = Integer.parseInt(s);
            for (int i = 0; i < loopSize; i++){
                s = input.readLine();
                String[] nums = s.split(" ");
                int t = Integer.parseInt(nums[0]);
                int tx = Integer.parseInt(nums[1]);
                int ty = Integer.parseInt(nums[2]);
                int tr = Integer.parseInt(nums[3]);
                int tw = Integer.parseInt(nums[4]);
                int th = Integer.parseInt(nums[5]);
                int tt = Integer.parseInt(nums[6]);
                int tc = Integer.parseInt(nums[7]);
                int tf = Integer.parseInt(nums[8]);
                int tfc = Integer.parseInt(nums[9]);

                if(t == 1){
                    MyLine m = new MyLine(tx,ty,tw,th,tt,unencodeColor(tc));
                    if(tf == 1){
                        m.toggleFilled();
                        m.setFillColor(unencodeColor(tfc));
                    }
                    m.draw(getGraphics());
                    shapeList.add(m);
                }
                else if (t ==2){
                    MyRectangle m = new MyRectangle(tx,ty,tw,th,tt,unencodeColor(tc));
                    if(tf == 1){
                        m.toggleFilled();
                        m.setFillColor(unencodeColor(tfc));
                    }
                    m.draw(getGraphics());
                    shapeList.add(m);
                }
                else if (t ==3){
                    MyCircle m = new MyCircle(tx,ty,tr,tw,th,tt,unencodeColor(tc));
                    if(tf == 1){
                        m.toggleFilled();
                        m.setFillColor(unencodeColor(tfc));
                    }
                    m.draw(getGraphics());
                    shapeList.add(m);
                }



            }
            input.close();
        }
        catch (IOException e){}
    }
*/

    // change integers back into colors
    // see encode color
    /*
    public int unencodeColor(int c){
        if (c == 1)
            return Color.RED;
        else if (c == 2)
            return Color.BLUE;

        else if (c == 3)
            return Color.GREEN;

        else
            return Color.WHITE;
    }*/

    // encode a color to an int
    // so that saving and loading is easier
    // this is because outputting a color outputs a whole bunch of gibberish
    // much easier to encode into an int.
    /*
    public int encodeColor(int c){
        if( c == Color.RED){
            return 1;}
        else if( c == Color.BLUE){
            return 2;}

        else if( c == Color.GREEN){
            return 3;}

        else
            return 7;
    }
*/


    // save to text file
    // get name from input
    // encode colors
    // output type of shape
    // output shape characteristics with encoded colors
/*
    public void save(){
        String name = JOptionPane.showInputDialog(null,"Input the name of the new save file. If the name isn't unique it will be overwritten. The name has to end with .txt.");
        try{
            BufferedWriter fw = new BufferedWriter(new FileWriter(name));
            fw.write(shapeList.size()+"");
            fw.newLine();
            for (int i = 0; i < shapeList.size(); i++){
                if (shapeList.get(i) instanceof MyLine){
                    fw.write("1 ");
                }
                else if(shapeList.get(i) instanceof MyRectangle){
                    fw.write("2 ");
                }
                else
                {fw.write("3 ");}

                fw.write(shapeList.get(i).getX() + " ");
                fw.write(shapeList.get(i).getY() + " ");
                fw.write(shapeList.get(i).getR() + " ");
                fw.write(shapeList.get(i).getW() + " ");
                fw.write(shapeList.get(i).getH() + " ");
                fw.write(shapeList.get(i).getThickness() + " ");
                fw.write(encodeColor(shapeList.get(i).getColor()) + " ");
                fw.write(shapeList.get(i).isFilled()+" ");
                fw.write(encodeColor(shapeList.get(i).getFillColor())+" ");

                fw.newLine();
            }

            fw.close();
        }
        catch (IOException e){}
    }

*/



}