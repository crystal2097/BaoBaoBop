package com.example.hoang.masterdetail_listview_sample.RecyclerAdapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hoang.masterdetail_listview_sample.DataObject.SanPham;
import com.example.hoang.masterdetail_listview_sample.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerAdapter_cart extends RecyclerView.Adapter<RecyclerAdapter_cart.ViewHolder> {
    ArrayList<SanPham> productsList;
    Context mContext;

    public RecyclerAdapter_cart() {
    }

    public RecyclerAdapter_cart(Context mContext, ArrayList<SanPham> productsList) {
        this.mContext = mContext;
        this.productsList = productsList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_layoutitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerAdapter_cart.ViewHolder holder, final int position) {
        holder.productName.setText(productsList.get(position).getTensp());
        Picasso.get().load(productsList.get(position).getImgurl()).centerCrop().resize(400, 400).into(holder.productImage);
        holder.productPrice.setText(productsList.get(position).getGia());


    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView productImage;
        TextView productName;
        TextView productPrice;
        RecyclerView recycler;
        TextView txtSoLuong;

        public ViewHolder(View itemView) {
            super(itemView);
            productImage = (ImageView) itemView.findViewById(R.id.cart_avatar);
            productName = (TextView) itemView.findViewById(R.id.cart_name);
            productPrice = (TextView) itemView.findViewById(R.id.cart_price);
            recycler = (RecyclerView) itemView.findViewById(R.id.cart_recyclerview);
            txtSoLuong = (TextView) itemView.findViewById(R.id.cart_Soluong);


        }


    }
}
