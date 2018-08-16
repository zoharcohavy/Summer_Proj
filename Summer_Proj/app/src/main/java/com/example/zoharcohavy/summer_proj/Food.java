package com.example.zoharcohavy.summer_proj;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by zoharcohavy on 8/11/18.
 */

public class Food {

    int size;
    boolean finished;
    private double centerX,centerY;

    public Food(int size) {
        this.size = size;
        centerX = 200;
        centerY = 300;
        finished = false;
    }

    public void draw(Canvas canvas) {
        if (canvas != null) {
            Paint paint = new Paint();
            paint.setColor(Color.rgb(0, 0, 0));
            canvas.drawRect((int) (centerX-size), (int) (centerY-size), (int) (centerX+size), (int) (centerY+size), paint);
        }
    }

    public void eaten() {
        if (size <=10) {
            finished = true;
        }
        size = size - 5;
    }

    public double getCenterX() {
        return centerX;
    }
    public double getCenterY() {
        return centerY;
    }

}
