package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Retrofits.INodeJS;
import com.example.myapplication.Retrofits.RetrofitClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class Product_activity extends AppCompatActivity {

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

    Button btnp, btnm,addcard;
    ImageView back , prodimg;
    TextView prodtitle , prodprice, proddesc ,qte1;
    TextView rep;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        prodimg = findViewById(R.id.prodimg);
        prodtitle = findViewById(R.id.prodtitle);
        prodprice = findViewById(R.id.prodprice);
        proddesc = findViewById(R.id.proddesc);
        rep = findViewById(R.id.rep);
        btnm=findViewById(R.id.btnm);
        btnp=findViewById(R.id.btnp);
        addcard= findViewById(R.id.addcard);
        qte1=findViewById(R.id.qte1);



        Intent intent = getIntent() ;
        String title = intent.getExtras().getString("title");
        String price = intent.getExtras().getString("price");
        String description = intent.getExtras().getString("description");
        String img = intent.getExtras().getString("t");
        String prodid = intent.getExtras().getString("prodid");
        int qte = intent.getExtras().getInt("qte");
        String id = intent.getExtras().getString("userid");
            btnp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int x = Integer.parseInt((qte1.getText().toString()));
                    if (x<qte){
                        x=x+1;
                        qte1.setText(String.valueOf(x));
                        String pr =price.substring(0,price.indexOf("D"));
                        prodprice.setText(String.valueOf(Integer.parseInt(pr)*x)+"DT");
                    }
                }
            });

        btnm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = Integer.parseInt((qte1.getText().toString()));
                if(x>1){
                x=x-1;
                qte1.setText(String.valueOf(x));
                    String pr =price.substring(0,price.indexOf("D"));
                    String lc = prodprice.getText().toString();
                    String f =  lc.substring(0,lc.indexOf("D"));
                    int fn = Integer.parseInt(f) - Integer.parseInt(pr);

                    prodprice.setText(String.valueOf(fn)+"DT");
                }
            }
        });

        //init API
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJS.class);

        addcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id!=null){
                String qt = qte1.getText().toString();
                String prix = prodprice.getText().toString();
                String p2 = prix.substring(0,prix.indexOf("D"));
                addcard(id ,prodid,title , description , p2 , img,Integer.parseInt(qt));

                }else {
                    Intent con = new Intent(Product_activity.this, Login.class);
                    startActivity(con);
                }
            }
        });


        Picasso.get().load(img).into(prodimg);
        prodtitle.setText(title);
        prodprice.setText(price);
        proddesc.setText(description);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent con = new Intent(Product_activity.this, Home.class);
                String id = intent.getExtras().getString("userid");
                con.putExtra("userid", id);
                startActivity(con);
            }
        });


    }
    private void addcard (String IdCli , String idProd , String title, String description , String price , String imglink , int qte ) {
        compositeDisposable.add(myAPI.addcard(IdCli, idProd
                ,title, description , price,imglink , qte)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>(){

                    @Override
                    public void accept(String s) throws Exception {
                        if (s.contains("Product Added successfully")){
                            rep.setText("Product Added successfully");
                            Intent con = new Intent(Product_activity.this, succes.class);
                            con.putExtra("userid", IdCli);
                            startActivity(con);
                        }else
                        {
                            Toast.makeText(Product_activity.this , s , Toast.LENGTH_SHORT).show();

                        }
                    }
                }) );
    }
}