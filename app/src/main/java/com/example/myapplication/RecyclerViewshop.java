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

public class RecyclerViewshop extends RecyclerView.Adapter<RecyclerViewshop.MyViewHolder> {
    INodeJS myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();


    private Context mContext ;
    private List<products> mData ;
    String id ;
    Dialog dialog;
    CheckBox ch;
    public RecyclerViewshop(Context mContext, List<products> mData , String id) {
        this.mContext = mContext;
        this.mData = mData;
        this.id=id;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view=mInflater.inflate(R.layout.shop_item_cardview,parent,false);

        return new RecyclerViewshop.MyViewHolder(view);
    }


    public void onBindViewHolder(MyViewHolder holder, int position) {

            String desc = mData.get(position).getDescription();
            String c = desc.substring(0,desc.indexOf("/"));
            String a = desc.substring(desc.indexOf("/")+1,desc.length());

        holder.conf.setText(c);
        holder.adr.setText(a);
        holder.prodtitle.setText(mData.get(position).getTitle());
        holder.prodprice.setText(mData.get(position).getPrice());
        holder.prodqte.setText(String.valueOf(mData.get(position).getQte()));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
        TextView conf ;
        TextView adr ;
        CardView cardView;
        public MyViewHolder(View itemView) {
            super(itemView);

            prodtitle = (TextView) itemView.findViewById(R.id.Title);
            prodprice = (TextView) itemView.findViewById(R.id.price);
            prodqte = (TextView) itemView.findViewById(R.id.qte);
            conf = (TextView) itemView.findViewById(R.id.conf) ;
            adr = (TextView) itemView.findViewById(R.id.adr);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);
        }
    }


}

