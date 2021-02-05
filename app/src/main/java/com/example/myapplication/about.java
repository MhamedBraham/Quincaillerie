package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

public class about extends AppCompatActivity {
    ImageView img , aboutimg;
    TextView desc1 ;
    TabLayout tabLayout;
    ViewPager viewPager;
    float v = 0 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        img = findViewById(R.id.back);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ab = new Intent(about.this, Home.class);
                String id = getIntent().getStringExtra("userid");
                ab.putExtra("userid", id);
                startActivity(ab);
            }
        });

        aboutimg = findViewById(R.id.aboutimg);
        desc1 = findViewById(R.id.desc1);

        aboutimg.setTranslationY(10);
        desc1.setTranslationY(10);

        aboutimg.setAlpha(v);
        desc1.setAlpha(v);

        aboutimg.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(100).start();
        desc1.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(200).start();

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);

        tabLayout.addTab(tabLayout.newTab().setText("About Us"));

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        tabLayout.setTranslationY(300);

        tabLayout.setAlpha(v);

        tabLayout.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(100).start();
    }
}