package com.example.hoang.masterdetail_listview_sample.RecyclerAdapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hoang.masterdetail_listview_sample.DataObject.SanPham;
import com.example.hoang.masterdetail_listview_sample.Interface.AddorRemoveCallbacks;
import com.example.hoang.masterdetail_listview_sample.Interface.ItemClickListener;
import com.example.hoang.masterdetail_listview_sample.OrderActivity;
import com.example.hoang.masterdetail_listview_sample.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    ArrayList<SanPham> productsList;
    Context mContext;


    public RecyclerAdapter() {
    }

    public RecyclerAdapter(Context mContext, ArrayList<SanPham> productsList) {
        this.mContext = mContext;
        this.productsList = productsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layoutitem, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.productName.setText(productsList.get(position).getTensp());
        Picasso.get().load(productsList.get(position).getImgurl()).centerCrop().resize(400, 400).into(holder.productImage);
        holder.productPrice.setText(productsList.get(position).getGia());
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (isLongClick) {
                    if (mContext instanceof OrderActivity) {
                        ((AddorRemoveCallbacks) mContext).onRemoveProduct(position);
                    }
                } else {
                    if (mContext instanceof OrderActivity) {
                        ((AddorRemoveCallbacks) mContext).onAddProduct(position);
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        ImageView productImage;
        TextView productName;
        TextView productPrice;
        RecyclerView recycler;
        private ItemClickListener itemClickListener;


        public MyViewHolder(View itemView) {
            super(itemView);
            productImage = (ImageView) itemView.findViewById(R.id.productImageView);
            productName = (TextView) itemView.findViewById(R.id.productNameTv);
            productPrice = (TextView) itemView.findViewById(R.id.productPriceTv);
            recycler = (RecyclerView) itemView.findViewById(R.id.recyclerview);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), false);
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), true);
            return true;
        }
    }
}
