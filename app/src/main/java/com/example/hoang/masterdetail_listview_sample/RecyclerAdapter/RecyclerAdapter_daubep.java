package com.example.hoang.masterdetail_listview_sample.RecyclerAdapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hoang.masterdetail_listview_sample.DataObject.ChiTietOrder;
import com.example.hoang.masterdetail_listview_sample.DataObject.Order;
import com.example.hoang.masterdetail_listview_sample.Interface.SetTinhTrangListener;
import com.example.hoang.masterdetail_listview_sample.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerAdapter_daubep extends RecyclerView.Adapter<RecyclerAdapter_daubep.ViewHolder> {
    ArrayList<Order> orders;
    ArrayList<ChiTietOrder> chiTietOrders;

    Context mContext;

    public RecyclerAdapter_daubep() {
    }

    public RecyclerAdapter_daubep(Context mContext, ArrayList<Order> orders, ArrayList<ChiTietOrder> chiTietOrders) {
        this.mContext = mContext;
        this.orders = orders;
        this.chiTietOrders = chiTietOrders;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.daubep_layoutitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerAdapter_daubep.ViewHolder holder, final int position) {
        holder.txtMieuta.setText(chiTietOrders.get(position).getTenMon());
        Picasso.get().load(chiTietOrders.get(position).getIMGMon()).centerCrop().resize(400, 400).into(holder.imgAvatar);
        holder.txtThoiGian.setText(orders.get(position).getThoiGian());
        holder.txtSoLuong.setText(Integer.toString(chiTietOrders.get(position).getSoLuong()));
        holder.txtMaOrder.setText(Integer.toString(orders.get(position).getMaOrder()));
        holder.btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((SetTinhTrangListener) mContext).SetTinhTrang(position);
                removeAt(position);
            }
        });

    }

    public void removeAt(int position) {
        orders.remove(position);
        chiTietOrders.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, orders.size(), chiTietOrders.size());
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMieuta;
        TextView txtThoiGian;
        TextView txtMaOrder;
        TextView txtSoLuong;
        ImageView imgAvatar;
        ImageButton btnDone;


        public ViewHolder(View itemView) {
            super(itemView);
            txtMieuta = (TextView) itemView.findViewById(R.id.daubep_txtmieuta);
            txtThoiGian = (TextView) itemView.findViewById(R.id.daubep_thoigian);
            imgAvatar = (ImageView) itemView.findViewById(R.id.daubep_avatar);
            btnDone = (ImageButton) itemView.findViewById(R.id.daubep_done);
            txtMaOrder = (TextView) itemView.findViewById(R.id.daubep_Maorder);
            txtSoLuong = (TextView) itemView.findViewById(R.id.daubep_txtsoluong);

        }


    }
}
