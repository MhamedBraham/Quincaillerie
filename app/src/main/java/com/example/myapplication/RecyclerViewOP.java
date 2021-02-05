package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Retrofits.INodeJS;
import com.example.myapplication.Retrofits.RetrofitClient;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class RecyclerViewOP extends RecyclerView.Adapter<RecyclerViewOP.MyViewHolder> {

    INodeJS myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();


    private Context mContext ;
    private List<products> mData ;
    String idcli ;
    public RecyclerViewOP(Context mContext, List<products> mData , String idcli) {
        this.mContext = mContext;
        this.mData = mData;
        this.idcli=idcli;
    }

    @Override
    public RecyclerViewOP.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view=mInflater.inflate(R.layout.orders_item_click_cardview,parent,false);

        return new RecyclerViewOP.MyViewHolder(view);
    }

    public void onBindViewHolder(RecyclerViewOP.MyViewHolder holder, int position) {


        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //init API
                Retrofit retrofit = RetrofitClient.getInstance();
                myAPI = retrofit.create(INodeJS.class);
                String m= mData.get(position).getId();
                delone(m);
            }
        });

        Picasso.get().load(mData.get(position).getT()).into(holder.prodimg);
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
        ImageView prodimg, del ;

        CardView cardView;
        public MyViewHolder(View itemView) {
            super(itemView);

            del = (ImageView) itemView.findViewById(R.id.delete) ;
            prodimg = (ImageView) itemView.findViewById(R.id.prodimg);
            prodtitle = (TextView) itemView.findViewById(R.id.Title);
            prodprice = (TextView) itemView.findViewById(R.id.price);
            prodqte = (TextView) itemView.findViewById(R.id.qte);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);
        }
    }
    private void delone(String id) {
        compositeDisposable.add(myAPI.delone(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>(){
                    @Override
                    public void accept(String res) throws Exception {
                        if(res.contains("success")) {
                            Intent i = new Intent(mContext, dele.class);
                            i.putExtra("userid", idcli);
                            mContext.startActivity(i);
                        }
                    }
                })
        );

        return;
    }
}
