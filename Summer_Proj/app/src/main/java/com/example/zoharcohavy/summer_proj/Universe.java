package com.example.zoharcohavy.summer_proj;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.util.Log;

import java.util.LinkedList;

public class Universe extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LinkedList<Being> beings = new LinkedList<Being>();
        for (int i = 0; i < 10; i++) {
            beings.add(new Being());
        }
        LinkedList<Food> foods = new LinkedList<Food>();
        for (int i = 0; i < 1; i++) {
            foods.add(new Food(20));
        }
        setContentView(new GameView(this, beings));


    }
}
