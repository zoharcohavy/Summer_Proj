package com.example.zoharcohavy.summer_proj;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import com.example.zoharcohavy.summer_proj.GameView;
import java.util.Random;

/**
 * Created by zoharcohavy on 8/9/18.
 */

public class Being {

    boolean alive;
    boolean hungry;
    int size = 10;
    int dir = 0;
    int dir_timer = 15;
    private double pain;
    private double centerX,centerY;
    private double velocityX,velocityY;
    public double food_spottedX, food_spottedY;

    public Being() {
        hungry = false;
        alive = true;
        pain = 0;
        centerX = 200;
        centerY = 200;
        velocityX = 1;
        velocityY = 1;
        food_spottedX = -1;
        food_spottedY = -1;
    };

    public void reset_pain() {
        pain = 0;
    }

    public void locate_food(Food food) {
        if ((centerX <= food.getCenterX() + 3*food.size )&& (centerX >= food.getCenterX() - 3*food.size )
                && (centerY <= food.getCenterY() + 3*food.size )&& (centerY >= food.getCenterY() - 3*food.size )) {
            this.food_spottedX = food.getCenterX();
            this.food_spottedY = food.getCenterY();
        }
    }

    public void draw(Canvas canvas) {
        if (canvas != null) {
            Paint paint = new Paint();
            //paint.setColor(Color.rgb((int)((pain/100)*250), (int)(250 - ((pain/100)+1)*250), 0));
            paint.setColor(Color.rgb((int)pain, 255 - (int)pain, 0));
            canvas.drawRect((int) ((int)centerX-size), (int) ((int)centerY-size), (int) ((int)centerX+size), (int) ((int)centerY+size), paint);
        }
    }

    public void updatePosition() {
        centerX += velocityX;
        if (centerX >= 720 || centerX <= 0) {
            velocityX *= -1;
        }
        centerY += velocityY;
        if(centerY >= 1280 || centerY <=0) {
            velocityY *= -1;
        }
    }
    public void updateDirection(int temp_dir) {//if ur putting velocity this is updating direction after food is consumed
        dir = (temp_dir+4)%8;
        dir_timer = 1;
        updateDirection();
        dir_timer = 30;
        pain = 0;
        food_spottedX = -1;
        food_spottedY = -1;
    }
    public void updatePosition_food (Food food) {
        if (food_spottedX >= centerX) {
            centerX += 1;
        }
        else {centerX -= 1;}
        if (food_spottedY >= centerY) {
            centerY += 1;
        }
        else {centerY -= 1;}

        if (should_eat(food)) {
            dir = new Random().nextInt(2);
            if (dir == 0) {
                if (centerX < food.getCenterX()) {
                    dir = 2;
                }
                else {
                    dir = 6;
                }
            }
            else {
                if (centerY < food.getCenterY()) {
                    dir = 4;
                }
                else {
                    dir = 0;
                }
            }
            updateDirection(dir);
            reset_pain();
            food.size = food.size - 1;
            dir_timer = 40;
            food_spottedX = -1;
            food_spottedY = -1;
            hungry = false;
        }

    }

    public void updateDirection() {
        if (food_spottedX !=-1) {
            //updateDirection_food();
            //return;
        }
        dir_timer--;
        if (dir_timer == 0) {
            dir_timer = new Random().nextInt(50)+5; //add one (minimum) so its never negative
            dir = new Random().nextInt(8);

            //directions work as follows: 0 is north, 1 is north_east... and you continue counter clockwise
            switch (dir) {
                case 0: velocityX = 0; velocityY = -1; break;
                case 1: velocityX = 0.57; velocityY = -0.57; break;
                case 2: velocityX = 1; velocityY = 0; break;
                case 3: velocityX = 0.57; velocityY = 0.57; break;
                case 4: velocityX = 0; velocityY = 1; break;
                case 5: velocityX = -0.57; velocityY = 0.57; break;
                case 6: velocityX = -1; velocityY = 0; break;
                case 7: velocityX = -0.57; velocityY = -0.57; break;
            }
        }
    }

    public void update(Food food) {
        pain = pain + 0.5;
        if (pain == 50) {
            hungry = true;
        }
        if (pain == 254) {
            alive = false;
        }
        if (food_spottedX < 0) {
            updateDirection();
            updatePosition();
        }
        else {
            updatePosition_food(food);
        }
    }

    public double getCenterX() {
        return centerX;
    }
    public double getCenterY() {
        return centerY;
    }
    public boolean should_eat(Food food) {
        return (centerX <= food.getCenterX() + food.size )&& (centerX >= food.getCenterX() - food.size )
                && (centerY <= food.getCenterY() + food.size )&& (centerY >= food.getCenterY() - food.size ) && (hungry);
    }
}
