package com.example.villairs.jsketchmobile;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class JSketch extends AppCompatActivity {
public ImageButton red, green, blue, thin, med, large, fill, circle, rect, line, select, erase,undo,redo;
public int colour, mode, size,height,width;
   // Bitmap bitmap;
MyCanvas canvas;
    DisplayMetrics metrics;
   View myView;

    public void clearColors(){
        red.getBackground().setAlpha(0);
        green.getBackground().setAlpha(0);
        blue.getBackground().setAlpha(0);
        myView.invalidate();
    }
    public void clearLines(){

        thin.getBackground().setAlpha(0);
        med.getBackground().setAlpha(0);
        large.getBackground().setAlpha(0);
        myView.invalidate();

    }
    public void clearOther(){
        select.getBackground().setAlpha(0);
        fill.getBackground().setAlpha(0);
        line.getBackground().setAlpha(0);
        circle.getBackground().setAlpha(0);
        rect.getBackground().setAlpha(0);
        erase.getBackground().setAlpha(0);
        myView.invalidate();
    }




    public void setSize(int s){
        size = s;
        canvas.setLineSize(s);
        clearLines();
    }

    public void setMode(int m){
        mode = m;
        canvas.setOther(m);
        clearOther();
    }

    public void setColour(int c){
        colour = c;
        canvas.setColor(c);
        clearColors();
    }








    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jsketch);

       myView = findViewById(R.id.layout);
        canvas = (MyCanvas) findViewById(R.id.view2);

     //   canvas.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));


      //  viewg.addView(canvas);
      //  metrics = new DisplayMetrics();
     //   getWindowManager().getDefaultDisplay().getMetrics(metrics);

      //  height = metrics.heightPixels;
      //  width = metrics.widthPixels;

    //    bitmap = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);




        red = (ImageButton) findViewById(R.id.red);
        red.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                setColour(Color.RED);
                red.getBackground().setAlpha(255);
               myView.invalidate();
            }
        });
        blue = (ImageButton) findViewById(R.id.blue);
        blue.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                setColour(Color.BLUE);
                blue.getBackground().setAlpha(255);
                myView.invalidate();
            }
        });

        green = (ImageButton) findViewById(R.id.green);
        green.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                setColour(Color.GREEN);
                green.getBackground().setAlpha(255);
                myView.invalidate();
            }
        });
        thin = (ImageButton) findViewById(R.id.thin);
        thin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                setSize(1);
                thin.getBackground().setAlpha(255);
                myView.invalidate();
            }
        });
        med = (ImageButton) findViewById(R.id.med);
        med.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                setSize(2);
                med.getBackground().setAlpha(255);
                myView.invalidate();
            }
        });
        large = (ImageButton) findViewById(R.id.large);
        large.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                setSize(3);
                large.getBackground().setAlpha(255);
                myView.invalidate();
            }
        });

        fill = (ImageButton) findViewById(R.id.fill);
        fill.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                setMode(1);
                fill.getBackground().setAlpha(255);
                myView.invalidate();
            }
        });
        line = (ImageButton) findViewById(R.id.line);
        line.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                setMode(2);
                line.getBackground().setAlpha(255);
                myView.invalidate();
            }
        });
        select = (ImageButton) findViewById(R.id.select);
        select.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                setMode(3);
                select.getBackground().setAlpha(255);
                myView.invalidate();
            }
        });

        erase = (ImageButton) findViewById(R.id.erase);
        erase.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                setMode(4);
                erase.getBackground().setAlpha(255);
                myView.invalidate();
            }
        });

        rect = (ImageButton) findViewById(R.id.rect);
        rect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                setMode(5);
                rect.getBackground().setAlpha(255);
                myView.invalidate();
            }
        });

        circle = (ImageButton) findViewById(R.id.circle);
        circle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                setMode(6);
                circle.getBackground().setAlpha(255);
                myView.invalidate();
            }
        });
        undo = (ImageButton) findViewById(R.id.undo);
       undo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            canvas.undo();
            }
        });

       redo = (ImageButton) findViewById(R.id.redo);
        redo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            canvas.redo();
            }
        });



        setColour(Color.RED);
        setMode(6);
        setSize(1);
        red.getBackground().setAlpha(255);
        thin.getBackground().setAlpha(255);
        circle.getBackground().setAlpha(255);
        myView.invalidate();
        canvas.setJSketch(this);
    }
}
