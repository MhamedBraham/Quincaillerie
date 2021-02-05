package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.Retrofits.INodeJS;
import com.example.myapplication.Retrofits.RetrofitClient;
import com.google.android.material.tabs.TabLayout;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class Contact extends AppCompatActivity {

    INodeJS myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
    Button send ;
    EditText name, emailsend, phone , desc;
    ImageView back;
    TabLayout tabLayout;
    ViewPager viewPager;
    TextView rep , rep1;

    float v=0 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent con = new Intent(Contact.this, Home.class);
                String id = getIntent().getStringExtra("userid");
                con.putExtra("userid", id);
                startActivity(con);
            }
        });


        name = findViewById(R.id.name);
        emailsend = findViewById(R.id.emailsend);
        phone = findViewById(R.id.phone);
        desc = findViewById(R.id.desc);
        send = findViewById(R.id.send);
        rep=findViewById(R.id.rep);
        rep1=findViewById(R.id.rep1);


        //init API
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJS.class);


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                contactuser(name.getText().toString() , emailsend.getText().toString()
                        ,phone.getText().toString(), desc.getText().toString());
            }
        });


        name.setTranslationY(10);
        emailsend.setTranslationY(10);
        phone.setTranslationY(10);
        desc.setTranslationY(10);
        send.setTranslationY(10);

        name.setAlpha(v);
        emailsend.setAlpha(v);
        phone.setAlpha(v);
        desc.setAlpha(v);
        send.setAlpha(v);

        name.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(100).start();
        emailsend.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(200).start();
        phone.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        desc.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        send.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();


        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);

        tabLayout.addTab(tabLayout.newTab().setText("Contact"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        tabLayout.setTranslationY(300);

        tabLayout.setAlpha(v);

        tabLayout.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(100).start();




    }

    private void contactuser(String name, String emailsend, String phone, String descrip) {

        compositeDisposable.add(myAPI.contactUser(name, emailsend
                ,Integer.parseInt(phone), descrip)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>(){

                    @Override
                    public void accept(String s) throws Exception {
                        if (s.contains("Send it successfully")){
                            rep.setText(s.substring(1,s.length()-1));

                        }else
                        {
                            rep1.setText(s.substring(1,s.length()-1));

                        }
                    }
                }) );
    }
}