package com.example.androidsmartcitytraveller;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class GifActivity extends AppCompatActivity {

    Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(GifActivity.this, TravelPlanActivity.class);
                startActivity(intent);
            }
        }, 6000);


    }
}
