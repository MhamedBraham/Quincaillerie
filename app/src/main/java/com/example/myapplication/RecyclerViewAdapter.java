package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

        private Context mContext ;
        private List<products> mData ;
        String id ;

    public RecyclerViewAdapter(Context mContext, List<products> mData , String id) {
        this.mContext = mContext;
        this.mData = mData;
        this.id=id;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view=mInflater.inflate(R.layout.cardveiw_item_products,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.prodtitle.setText(mData.get(position).getTitle());
        holder.prodprice.setText(mData.get(position).getPrice());
        Picasso.get().load(mData.get(position).getT()).into(holder.prodimg);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext,Product_activity.class);
                intent.putExtra("prodid",mData.get(position).getId());
                intent.putExtra("title",mData.get(position).getTitle());
                intent.putExtra("price",mData.get(position).getPrice());
                intent.putExtra("description",mData.get(position).getDescription());
                intent.putExtra("t",mData.get(position).getT());
                intent.putExtra("qte", mData.get(position).getQte() );
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
        ImageView prodimg;
        CardView cardView;
        public MyViewHolder(View itemView) {
            super(itemView);

            prodtitle = (TextView) itemView.findViewById(R.id.prodtitle);
            prodprice = (TextView) itemView.findViewById(R.id.prodprice);
            prodimg = (ImageView) itemView.findViewById(R.id.prodimg);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);
        }
    }


}
