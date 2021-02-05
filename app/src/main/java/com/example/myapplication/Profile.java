package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Retrofits.INodeJS;
import com.example.myapplication.Retrofits.RetrofitClient;

import org.json.JSONArray;
import org.json.JSONObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class Profile extends AppCompatActivity {

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
    Button orders ;
    TextView username,name , email , adr ,prep;
    ImageView back , addadr , pass;
    Dialog dialog , dialog2;
    EditText oldp , newp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        username=findViewById(R.id.username);
        name= findViewById(R.id.username2);
        email=findViewById(R.id.useremail);
        adr = findViewById(R.id.useradr);
        orders= findViewById(R.id.orders);
        addadr = findViewById(R.id.addadr);
        pass = findViewById(R.id.pass);



            dialog = new Dialog(Profile.this);
            dialog.setContentView(R.layout.custom_dialog);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background));
            }
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.setCancelable(false);


            EditText adress1 = dialog.findViewById(R.id.adruser);
            Button addad = dialog.findViewById(R.id.addaa);
            Button cancel =  dialog.findViewById(R.id.cancel);


            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });



            addad.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String res = adress1.getText().toString();

                            if (res.equals("")){
                                Toast.makeText(Profile.this , "ADD Your Adress", Toast.LENGTH_SHORT).show();
                            }else{
                                String id = getIntent().getStringExtra("userid");
                                sendadr(res,id);
                            }

                }
            });


        addadr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.show();
                }
            });



        dialog2 = new Dialog(Profile.this);
        dialog2.setContentView(R.layout.update_password);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            dialog2.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background));
        }
        dialog2.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog2.setCancelable(false);


        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.show();

            }
        });

        oldp = dialog2.findViewById(R.id.oldpass);
        newp = dialog2.findViewById(R.id.newpass);
        prep = dialog2.findViewById(R.id.prep);
        Button upd = dialog2.findViewById(R.id.upd);
        Button canc =  dialog2.findViewById(R.id.cancel);

        canc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.dismiss();
            }
        });

        upd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(newp.getText().toString().equals("")){
                    prep.setText("put new password");

                }else{
                    Retrofit retrofit = RetrofitClient.getInstance();
                    myAPI = retrofit.create(INodeJS.class);
                    String id = getIntent().getStringExtra("userid");
                    updpadd(id ,oldp.getText().toString() , newp.getText().toString());
                }

            }
        });



        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this , shop.class);
                String id = getIntent().getStringExtra("userid");
                intent.putExtra("userid", id);
                startActivity(intent);
            }
        });

        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJS.class);
        String id = getIntent().getStringExtra("userid");

        profileuser(id);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent con = new Intent(Profile.this, Home.class);
                String id = getIntent().getStringExtra("userid");
                con.putExtra("userid", id);
                startActivity(con);

            }
        });

    }

    private void profileuser(String id1) {

        compositeDisposable.add(myAPI.profileuser(id1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>(){

                    @Override
                    public void accept(String res) throws Exception {

                        JSONArray s = new JSONArray(res);

                        JSONObject user = s.getJSONObject(0);

                            String fname = user.getString("firstname");
                            String lname = user.getString("lastname");
                            String uemail = user.getString("email");
                            String uadr = user.getString("adresse");
                            username.setText(fname+" "+lname);
                            name.setText(fname+" "+lname);
                            email.setText(uemail);

                            if(uadr!="null"){
                                adr.setText(uadr);
                                addadr.setVisibility(View.GONE);
                            } else{
                                addadr.setVisibility(View.VISIBLE);
                                adr.setText("No Adress");
                            }
                    }
                })
        );

    }

    private void sendadr(String adrs , String id) {

        compositeDisposable.add(myAPI.adadr(id , adrs)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>(){

                    @Override
                    public void accept(String res) throws Exception {

                                if(res!="") {
                                    dialog.dismiss();
                                    Toast.makeText(Profile.this, "Successfully", Toast.LENGTH_SHORT).show();
                                    adr.setText(adrs);
                                    addadr.setVisibility(View.GONE);
                                }
                    }
                })
        );
    }

    private void updpadd (String id, String o , String n ){
        compositeDisposable.add(myAPI.updpass(id,o,n)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>(){

                    @Override
                    public void accept(String s) throws Exception {

                        if (s.contains("email")){
                            dialog2.dismiss();
                            oldp.setText("");
                            newp.setText("");
                            prep.setText("");
                            Toast.makeText(Profile.this, "Successfully", Toast.LENGTH_SHORT).show();

                        }else
                        {
                            oldp.setText("");
                            newp.setText("");
                            prep.setText(s.substring(1,s.length()-1));

                        }
                    }
                })
        );
    }
}