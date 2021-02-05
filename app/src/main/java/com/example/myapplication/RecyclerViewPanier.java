package com.example.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Retrofits.INodeJS;
import com.example.myapplication.Retrofits.RetrofitClient;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class RecyclerViewPanier extends RecyclerView.Adapter<RecyclerViewPanier.MyViewHolder> {
    INodeJS myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();


    private Context mContext ;
    private List<products> mData ;
    String id ;
    Dialog dialog;
    CheckBox ch;
    public RecyclerViewPanier(Context mContext, List<products> mData , String id) {
        this.mContext = mContext;
        this.mData = mData;
        this.id=id;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view=mInflater.inflate(R.layout.orders_item_cardview,parent,false);

        return new RecyclerViewPanier.MyViewHolder(view);
    }


    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //init API
                Retrofit retrofit = RetrofitClient.getInstance();
                myAPI = retrofit.create(INodeJS.class);
                String m= mData.get(position).getId();
                del(m);

            }
        });

        dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.chekout_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        Button confir = dialog.findViewById(R.id.confir);
        Button cancel = dialog.findViewById(R.id.cancel);
        EditText adr1 = dialog.findViewById(R.id.adruser);
        TextView repo = dialog.findViewById(R.id.repo);
         ch = dialog.findViewById(R.id.checkbox);


        confir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = RetrofitClient.getInstance();
                myAPI = retrofit.create(INodeJS.class);
                if(ch.isChecked()){
                    orders(id , ch.getText().toString());
                }else {
                    if (ch.isChecked()==false){
                            String ad=adr1.getText().toString();
                                if(ad.length()<30 || ad==""){
                                    repo.setText("Adresse non valide");
                                }else{
                                    orders2(id , ad);
                                }
                    }
                }
                //Toast.makeText(dialog.getContext(), adr1.getText().toString() , Toast.LENGTH_SHORT).show();

            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        ch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( ch.isChecked()==true){
                    adr1.setVisibility(View.INVISIBLE);
                }else {adr1.setVisibility(View.VISIBLE);}
            }
        });




        holder.chekout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //init API
                Retrofit retrofit = RetrofitClient.getInstance();
                myAPI = retrofit.create(INodeJS.class);
                        getadr(id);

                dialog.show();
            }
        });

        holder.prodtitle.setText(mData.get(position).getTitle());
        holder.prodprice.setText(mData.get(position).getPrice());
        holder.prodqte.setText(String.valueOf(mData.get(position).getQte()));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Intent intent = new Intent(mContext,Product_activity.class);
                intent.putExtra("prodid",mData.get(position).getId());
                intent.putExtra("title",mData.get(position).getTitle());
                intent.putExtra("price",mData.get(position).getPrice());
                intent.putExtra("description",mData.get(position).getDescription());
                intent.putExtra("t",mData.get(position).getT());
                intent.putExtra("qte", mData.get(position).getQte() );
                intent.putExtra("userid",id);
                mContext.startActivity(intent);*/
                Intent intent = new Intent(mContext,Orders_Activity.class);
                intent.putExtra("userid",id);
                mContext.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView prodtitle ;
        TextView prodprice;
        TextView prodqte ;
        Button del ,chekout ;

        CardView cardView;
        public MyViewHolder(View itemView) {
            super(itemView);
            chekout = (Button) itemView.findViewById(R.id.chekout);
            del = (Button) itemView.findViewById(R.id.delete) ;
            prodtitle = (TextView) itemView.findViewById(R.id.Title);
            prodprice = (TextView) itemView.findViewById(R.id.price);
            prodqte = (TextView) itemView.findViewById(R.id.qte);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);
        }
    }
    private void getadr(String id) {
        compositeDisposable.add(myAPI.profileuser(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>(){
                    @Override
                    public void accept(String res) throws Exception {
                        JSONArray s = new JSONArray(res);
                        JSONObject user = s.getJSONObject(0);

                        String adr =  user.getString("adresse");
                        if(adr!="null"){
                            ch.setText(adr);
                        }else
                        {
                            ch.setVisibility((View.INVISIBLE));
                        }
                    }
                })
        );
    }

    private void del(String id) {
        compositeDisposable.add(myAPI.del(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>(){

                    @Override
                    public void accept(String res) throws Exception {
                            if(res.contains("success")){
                                Intent i  = new Intent(mContext, dele.class);
                                i.putExtra("userid", id);
                                mContext.startActivity(i);
                            }
                    }
                })
        );

        return;
    }

    private void orders(String id , String ch){
        compositeDisposable.add(myAPI.card2(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>(){

                    @Override
                    public void accept(String res) throws Exception {

                        JSONArray s = new JSONArray(res);

                        for (int i = 0 ; i < s.length();i++){

                            JSONObject order = s.getJSONObject(i);

                            String IdCard = order.getString("IdCard");
                            String IdCli = order.getString("IdCli");
                            String idProd = order.getString("idProd");
                            String title = order.getString("title");
                            String description = order.getString("description");
                            String price = order.getString("price");
                            String imglink = order.getString("imglink");
                            int qte = order.getInt("qte");

                            //init API
                            Retrofit retrofit = RetrofitClient.getInstance();
                            myAPI = retrofit.create(INodeJS.class);
                            insorders(IdCard , IdCli ,idProd,title,description,price,imglink,qte ,"en cour",ch);
                        }
                    }
                })
        );

    }
    private void orders2(String id , String adr){
        compositeDisposable.add(myAPI.card2(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>(){

                    @Override
                    public void accept(String res) throws Exception {

                        JSONArray s = new JSONArray(res);

                        for (int i = 0 ; i < s.length();i++){

                            JSONObject order = s.getJSONObject(i);

                            String IdCard = order.getString("IdCard");
                            String IdCli = order.getString("IdCli");
                            String idProd = order.getString("idProd");
                            String title = order.getString("title");
                            String description = order.getString("description");
                            String price = order.getString("price");
                            String imglink = order.getString("imglink");
                            int qte = order.getInt("qte");

                            //init API
                            Retrofit retrofit = RetrofitClient.getInstance();
                            myAPI = retrofit.create(INodeJS.class);
                            insorders(IdCard ,IdCli ,idProd,title,description,price,imglink,qte ,"en cour",adr);

                        }
                    }
                })
        );


    }

    private  void insorders(String IdCard, String IdCli , String idProd	,String title,String description ,String price ,String imglink	, int qte	,String confirm , String adress){
        compositeDisposable.add(myAPI.insorders(IdCli , idProd , title , description , price , imglink, qte,confirm , adress )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>(){

                    @Override
                    public void accept(String res) throws Exception {
                        if(res.contains("successfully")){
                            dialog.dismiss();
                            Intent con = new Intent(mContext, confirm.class);
                            con.putExtra("userid", IdCli);
                            mContext.startActivity(con);
                            delone(IdCard);
                            sendadr( adress ,IdCli);
                            mqte(idProd , qte);
                        }
                    }
                })
        );
    }

    private void delone(String id) {
        compositeDisposable.add(myAPI.delone(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>(){

                    @Override
                    public void accept(String res) throws Exception {
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

                    }
                })
        );
    }

    private  void mqte(String id , int q){
        compositeDisposable.add(myAPI.mqte(id , q)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>(){

                    @Override
                    public void accept(String res) throws Exception {
                    }
                })
        );
    }

}
