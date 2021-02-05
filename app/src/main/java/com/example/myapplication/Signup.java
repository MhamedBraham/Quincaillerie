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
import android.widget.Toast;

import com.example.myapplication.Retrofits.INodeJS;
import com.example.myapplication.Retrofits.RetrofitClient;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class Signup extends AppCompatActivity {

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
    TextView rep;
    ImageView back;
    TabLayout tabLayout;
    ViewPager viewPager;
    Button signup ;
    EditText firstname, lastname, emailsig , passwordsig;
    float v=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        emailsig = findViewById(R.id.emailsig);
        passwordsig = findViewById(R.id.passwordsig);
        signup = findViewById(R.id.signup);
        rep=findViewById(R.id.rep);

        //init API
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJS.class);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String fname = firstname.getText().toString();
                    String lname = lastname.getText().toString() ;
                    String em = emailsig.getText().toString() ;
                    String ps =  passwordsig.getText().toString() ;
                if(fname.equals("") && lname.equals("") && em.equals("") && ps.equals("")){

                    rep.setText("Require");
                }else
                    if(fname.length()<3 || lname.length()<3 || em.indexOf("@")<0 || em.indexOf(".")<0 || em.length()<6 || ps.length()<6){
                        rep.setText("Error");
                    }
                else{
                signupuser(fname,lname,em,ps );
                    }
            }
        });

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent con = new Intent(Signup.this, Home.class);
                startActivity(con);
            }
        });



        firstname.setTranslationY(10);
        lastname.setTranslationY(10);
        emailsig.setTranslationY(10);
        passwordsig.setTranslationY(10);
        signup.setTranslationY(10);

        firstname.setAlpha(v);
        lastname.setAlpha(v);
        emailsig.setAlpha(v);
        passwordsig.setAlpha(v);
        signup.setAlpha(v);

        firstname.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(100).start();
        lastname.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(200).start();
        emailsig.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        passwordsig.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        signup.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();


        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);

        tabLayout.addTab(tabLayout.newTab().setText("Signup"));

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        tabLayout.setTranslationY(300);

        tabLayout.setAlpha(v);

        tabLayout.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(100).start();
    }

    private void signupuser(String firstname, String lastname, String email, String password) {
        compositeDisposable.add(myAPI.registerUser(firstname, lastname
                ,email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>(){

                    @Override
                    public void accept(String s) throws Exception {
                        if (s.contains("Register successfully")){
                            gid(email);

                        }else
                        {
                            rep.setText(s.substring(1,s.length()-1));
                        }
                    }
                }) );
    }

    private  void gid(String email){
        compositeDisposable.add(myAPI.gid(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>(){

                    @Override
                    public void accept(String res) throws Exception {
                        JSONArray s = new JSONArray(res);
                        if (s!=null){
                            JSONObject product = s.getJSONObject(0);
                            String id = product.getString("id");
                            Intent prof = new Intent(Signup.this, Home.class);
                            prof.putExtra("userid", id);
                            startActivity(prof);
                        }
                    }
                }) );
    }

}