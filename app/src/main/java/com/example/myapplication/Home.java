package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.myapplication.Retrofits.INodeJS;
import com.example.myapplication.Retrofits.RetrofitClient;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


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


    List<products> lstproducts;
    List<products> panier;

        ImageView cardic ;
        DrawerLayout drawerLayout;
        NavigationView navigationView;
        Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        cardic=findViewById(R.id.cardic);
        cardic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = getIntent().getStringExtra("userid");
                if(id!=null){
                Intent order = new Intent(Home.this, Order.class);
                order.putExtra("userid", id);
                startActivity(order);
                }else{
                    Intent login = new Intent(Home.this , Login.class);
                    startActivity(login);
                }

            }
        });

        //init API
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJS.class);



        lstproducts= new ArrayList<>();
        homeApp();
        /*lstproducts.add(new products("ddddd","description","500","https://siteq.com.tn/uploads/products/peinture-a-l-eau-40kg-siteq.jpg" , 15));
        lstproducts.add(new products("aaaa","description","200","http://www.metm.tn/Images/Catalogue/4332/4332_1.jpg" , 15));
        lstproducts.add(new products("zzzzzz","description","500","https://piecedepro.fr/website/image/product.template/161173_4fd5fb0/image" , 15));
        lstproducts.add(new products("ddddd","description","500","https://www.general-equipement.tn/558-home_default/chariniere-inox-charniere.jpg" , 15));
        lstproducts.add(new products("aaaa","description","200","https://atalanta-group.com.tn/media/produits/55473ff57c472.jpg",15));
        lstproducts.add(new products("zzzzzz","description","500","https://www.cdiscount.com/pdt2/9/4/1/1/700x700/biz3700420318941/rw/boulon-tete-ronde-6-x-20-mm-avec-collet-carre-pa.jpg",15));
        lstproducts.add(new products("ddddd","description","500","https://www.bh-industrie.com/ressources/images/5f9393747c7b.jpg",15));
        lstproducts.add(new products("aaaa","description","200","https://www.comptoir-hammami.com/wp-content/uploads/2018/03/coude-pvc-1.jpg",15));
        lstproducts.add(new products("zzzzzz","description","500","https://aats3-8ea3a45b69a7b0d7b948f17b5eab61f-public.s3-eu-west-1.amazonaws.com/vernis_lasure_2_0_0.jpg",15));
        lstproducts.add(new products("ddddd","description","500","https://image.shutterstock.com/image-photo/bright-spring-view-cameo-island-260nw-1048185397.jpg",15));
        lstproducts.add(new products("aaaa","description","200","https://d1fmx1rbmqrxrr.cloudfront.net/cnet/optim/i/edit/2019/04/eso1644bsmall__w770.jpg" ,15));
        lstproducts.add(new products("zzzzzz","description","500","https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg",15));
        lstproducts.add(new products("ddddd","description","500","https://image.shutterstock.com/image-photo/bright-spring-view-cameo-island-260nw-1048185397.jpg",15));
        lstproducts.add(new products("aaaa","description","200","https://d1fmx1rbmqrxrr.cloudfront.net/cnet/optim/i/edit/2019/04/eso1644bsmall__w770.jpg",15));
        lstproducts.add(new products("zzzzzz","description","500","https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg",15));
        */


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView =findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        Menu menu = navigationView.getMenu();
        String id = getIntent().getStringExtra("userid");


        if(id==null){

            menu.findItem(R.id.nav_logout).setVisible(false);
            menu.findItem(R.id.nav_profile).setVisible(false);
            menu.findItem(R.id.nav_login).setVisible(true);
            menu.findItem(R.id.nav_signup).setVisible(true);

        }else{

                menu.findItem(R.id.nav_login).setVisible(false);
                menu.findItem(R.id.nav_signup).setVisible(false);
                menu.findItem(R.id.nav_logout).setVisible(true);
                menu.findItem(R.id.nav_profile).setVisible(true);


        }



        navigationView.bringToFront();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout , toolbar , R.string.navigation_drawer_open , R.string.navigation_drawer_close );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_home);

    }

    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        String id = getIntent().getStringExtra("userid");

        switch (item.getItemId()){

                case R.id.nav_home:
                    break;
                case R.id.nav_contcat:
                    Intent con = new Intent(Home.this, Contact.class);
                    con.putExtra("userid", id);
                    startActivity(con);
                    break;
                case R.id.nav_about:
                    Intent ab = new Intent(Home.this, about.class);
                    ab.putExtra("userid", id);
                    startActivity(ab);
                    break;
                case R.id.nav_login:
                    Intent log = new Intent(Home.this, Login.class);
                    startActivity(log);
                    break;
                case R.id.nav_signup:
                    Intent sig = new Intent(Home.this, Signup.class);
                    startActivity(sig);
                    break;

                case R.id.nav_profile:
                    Intent prof = new Intent(Home.this, Profile.class);
                    prof.putExtra("userid", id);
                    startActivity(prof);
                    break;
                case R.id.nav_logout:
                    Intent act = new Intent(Home.this, Login.class);
                    startActivity(act);
                    break;
            }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }



  private void homeApp() {
        compositeDisposable.add(myAPI.homeApp("")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>(){

                    @Override
                    public void accept(String res) throws Exception {

                            JSONArray s = new JSONArray(res);

                        for (int i = 0 ; i < s.length();i++){

                            JSONObject product = s.getJSONObject(i);

                            String id = product.getString("idimg");
                            String title = product.getString("title");
                            String description = product.getString("description");
                            String price = product.getString("price");
                            String imglink = product.getString("imglink");
                            int qte = product.getInt("qte");
                            if(qte>0){
                            lstproducts.add(new products(id,title,description,price+"DT" , imglink , qte));}
                        }
                        String id = getIntent().getStringExtra("userid");
                        RecyclerView myrv = (RecyclerView) findViewById(R.id.recycleview);
                        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(Home.this, lstproducts ,id);
                        myrv.setLayoutManager(new GridLayoutManager(Home.this,3));
                        myrv.setAdapter(myAdapter);

                    }
                })
        );

      return;

}
}