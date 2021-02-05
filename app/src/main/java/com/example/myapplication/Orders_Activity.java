package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class Orders_Activity extends AppCompatActivity {


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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);


        back = findViewById(R.id.bc);
        rep=findViewById(R.id.rep);
        //init API
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJS.class);
        lstproducts= new ArrayList<>();
        String id = getIntent().getStringExtra("userid");
        card(id);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ab = new Intent(Orders_Activity.this, Order.class);
                String id = getIntent().getStringExtra("userid");
                ab.putExtra("userid", id);
                startActivity(ab);
            }
        });
    }



    private void card(String id) {
        compositeDisposable.add(myAPI.card2(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>(){

                    @Override
                    public void accept(String res) throws Exception {

                        if(res!=null) {
                            JSONArray s = new JSONArray(res);

                            for (int i = 0; i < s.length(); i++) {

                                JSONObject product = s.getJSONObject(i);

                                String IdCard = product.getString("IdCard");
                                String title = product.getString("title");
                                String price = product.getString("price");
                                String imglink = product.getString("imglink");
                                int qte = product.getInt("qte");

                                lstproducts.add(new products(IdCard, title, "", price + "Dt", imglink, qte));

                            }

                            RecyclerView myrv = (RecyclerView) findViewById(R.id.recycleview);
                            RecyclerViewOP myAdapter = new RecyclerViewOP(Orders_Activity.this, lstproducts, id);
                            myrv.setLayoutManager(new GridLayoutManager(Orders_Activity.this, 1));
                            myrv.setAdapter(myAdapter);
                        }else{
                            Toast.makeText(Orders_Activity.this ,"no product",Toast.LENGTH_SHORT).show();
                        }


                    }
                })
        );
    }
}