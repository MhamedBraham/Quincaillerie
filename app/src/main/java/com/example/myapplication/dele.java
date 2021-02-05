package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class dele extends AppCompatActivity {
    private  static int SPLASH_SCREEN = 1500;
    Animation topAnim , bottomAnim ;
    ImageView image;
    TextView logo ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dele);
        topAnim = AnimationUtils.loadAnimation(this , R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this , R.anim.bottom_animation);

        image =  findViewById(R.id.img);
        logo =  findViewById(R.id.logo);

        logo.setAnimation(topAnim);
        image.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(dele.this, Order.class);
                String id = getIntent().getStringExtra("userid");
                intent.putExtra("userid", id);
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN);
    }
}