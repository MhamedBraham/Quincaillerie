package com.example.myapplication;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.Retrofits.INodeJS;
import com.example.myapplication.Retrofits.RetrofitClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class shop extends AppCompatActivity {


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

    ImageView back;
    List<products> lstproducts;
    TextView rep ;
    String id ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        id = getIntent().getStringExtra("userid");
        back = findViewById(R.id.bc);
        rep=findViewById(R.id.rep);
        //init API
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJS.class);
        lstproducts= new ArrayList<>();

        shopcard(id);
        // lstproducts.add(new products("","aaaa","description","200","http://www.metm.tn/Images/Catalogue/4332/4332_1.jpg" , 15));
        // rep.setText("no order");


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ab = new Intent(shop.this, Profile.class);
                String id = getIntent().getStringExtra("userid");
                ab.putExtra("userid", id);
                startActivity(ab);
            }
        });
    }

    private void shopcard(String id) {
        compositeDisposable.add(myAPI.shopcard(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>(){

                    @Override
                    public void accept(String res) throws Exception {

                        if(res.equals("noproducts")||(res=="noproducts")|| res.contains("noproducts"))
                        {
                            rep.setText("No Shop");

                        }else{
                            JSONArray s = new JSONArray(res);

                            String  title = "";
                            String adr="";
                            String conf ="";
                            String desc ="";
                            int price=0 , qte = 0;
                            for (int i = 0 ; i < s.length();i++){

                                JSONObject product = s.getJSONObject(i);

                                title = title +" , "+ product.getString("title");
                                int m = Integer.parseInt(product.getString("price"));
                                price=price+m;
                                qte = qte + product.getInt("qte");
                                conf = product.getString("confirmation");
                                adr =  product.getString("adress");
                                desc = conf+"/"+adr ;
                            }
                            lstproducts.add(new products(id,title.substring(2,title.length()),desc,String.valueOf(price+"Dt"),"http://www.metm.tn/Images/Catalogue/4332/4332_1.jpg" , qte));
                            RecyclerView myrv = (RecyclerView) findViewById(R.id.recycleview);
                            RecyclerViewshop myAdapter = new RecyclerViewshop(shop.this, lstproducts ,id);
                            myrv.setLayoutManager(new GridLayoutManager(shop.this,1));
                            myrv.setAdapter(myAdapter);

                        }

                    }
                })
        );
    }
}