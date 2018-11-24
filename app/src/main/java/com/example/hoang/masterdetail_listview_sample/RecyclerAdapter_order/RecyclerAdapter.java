package com.example.hoang.masterdetail_listview_sample.RecyclerAdapter_order;


import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.hoang.masterdetail_listview_sample.OrderActivity;
import com.example.hoang.masterdetail_listview_sample.DataObject.SanPham;
import com.example.hoang.masterdetail_listview_sample.R;

//import com.example.codingmounrtain.addtocartbadgecount.interfaces.AddorRemoveCallbacks;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {


    ArrayList<SanPham> productsList;
    Context mContext;



    public RecyclerAdapter()
    {

    }

    public RecyclerAdapter(Context mContext,ArrayList<SanPham> productsList)
    {
        this.mContext=mContext;
        this.productsList=productsList;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layoutitem,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.productName.setText(productsList.get(position).getTensp());
        Picasso.get().load(productsList.get(position).getImgurl()).centerCrop().resize(400,400).into(holder.productImage);
//      holder.productImage.setImageResource(productsList.get(position).getImgurl());
        holder.productPrice.setText(productsList.get(position).getGia());
//        holder.addRemoveBt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(!productsList.get(position).isAddedTocart())
//                {
//                    productsList.get(position).setAddedTocart(true);
//                    holder.addRemoveBt.setText("Remove");
//                    if(mContext instanceof MainActivity)
//                    {
//                        ((AddorRemoveCallbacks)mContext).onAddProduct();
//                    }
//
//                }
//                else
//                {
//                    productsList.get(position).setAddedTocart(false);
//                    holder.addRemoveBt.setText("Add");
//                    ((AddorRemoveCallbacks)mContext).onRemoveProduct();
//                }
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView productImage;
        TextView productName;
        TextView productPrice;


        public MyViewHolder(View itemView) {
            super(itemView);
            productImage=(ImageView) itemView.findViewById(R.id.productImageView);
            productName=(TextView) itemView.findViewById(R.id.productNameTv);
            productPrice=(TextView)itemView.findViewById(R.id.productPriceTv);
        }
    }
}
