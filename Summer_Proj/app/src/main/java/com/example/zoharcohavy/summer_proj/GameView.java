package com.example.zoharcohavy.summer_proj;

import android.content.Context;
import android.graphics.Color;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.graphics.Canvas;
import java.util.LinkedList;
import java.util.ListIterator;


/**
 * Created by zoharcohavy on 8/8/18.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    public MainThread thread;
    public Being list_being;
    public Food food = new Food(20);
    LinkedList<Being> beings;
    ListIterator<Being> iterator;


    public GameView(Context context, LinkedList<Being> beings) {
        super(context);
        getHolder().addCallback(this);
        this.beings = beings;
        thread = new MainThread(getHolder(), this);
        setFocusable(true);
    }

    public void update() {
        iterator = beings.listIterator();
        while (iterator.hasNext()) {
            list_being = iterator.next();
            if (list_being.alive) {// eat food if youre sitting on food

                if(list_being.hungry && list_being.food_spottedY == -1) {
                    list_being.locate_food(food);
                }

                if (list_being.should_eat(food) ){
                    list_being.updateDirection(list_being.dir);
                    list_being.reset_pain();
                    food.size = food.size - 1;

                }
                list_being.update(food);
            }
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(Color.WHITE);
        iterator = beings.listIterator();
        while (iterator.hasNext()) {
            list_being = iterator.next();
            if (list_being.alive) {
                list_being.draw(canvas);
            }
        }

        food.draw(canvas);

    }

}
