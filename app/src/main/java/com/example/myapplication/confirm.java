package com.example.myapplication;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class confirm extends AppCompatActivity {
    private  static int SPLASH_SCREEN = 1500;
    Animation topAnim  ;
    TextView logo ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        topAnim = AnimationUtils.loadAnimation(this , R.anim.top_animation);


        logo =  findViewById(R.id.logo);

        logo.setAnimation(topAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(confirm.this, Home.class);
                String id = getIntent().getStringExtra("userid");
                intent.putExtra("userid", id);
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN);

    }
}