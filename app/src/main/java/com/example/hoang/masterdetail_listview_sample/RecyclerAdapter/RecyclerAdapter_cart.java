package com.example.hoang.masterdetail_listview_sample.RecyclerAdapter;

import android.content.Context;
import android.support.constraint.solver.SolverVariable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hoang.masterdetail_listview_sample.DataObject.SanPham;
import com.example.hoang.masterdetail_listview_sample.Interface.OrderAddSubClear;
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
        holder.txtSoLuong.setText(Integer.toString(productsList.get(position).getSoluong()));
        holder.txtSubTotal.setText(Integer.toString(SubTotalCalc(position)));
        holder.btnAddPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((OrderAddSubClear) mContext).onAddProduct(position);
                holder.txtSoLuong.setText(String.valueOf(productsList.get(position).getSoluong()));
                holder.txtSubTotal.setText(String.valueOf(Integer.toString(SubTotalCalc(position))));
            }
        });
        holder.btnSubPro.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    ((OrderAddSubClear) mContext).onSubProduct(position);
                                                    holder.txtSoLuong.setText(Integer.toString(productsList.get(position).getSoluong()));
                                                    holder.txtSubTotal.setText(String.valueOf(Integer.toString(SubTotalCalc(position))));

                                                }
                                            }

        );
        holder.btnRemovePro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((OrderAddSubClear) mContext).onAddProduct(position);
                removeAt(position);

            }
        });

    }

    public void removeAt(int position) {
        productsList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, productsList.size());
    }


    public int SubTotalCalc(int position) {
        int SoLuong = productsList.get(position).getSoluong();
        String substr = productsList.get(position).getGia();
        String[] split = substr.split("VND");
        String firstSubString = split[0].substring(0, split[0].length() - 1);
        int Gia = Integer.parseInt(firstSubString);
        return SoLuong * Gia;
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
        ImageButton btnAddPro;
        ImageButton btnSubPro;
        ImageButton btnRemovePro;
        TextView txtSubTotal;

        public ViewHolder(View itemView) {
            super(itemView);
            productImage = (ImageView) itemView.findViewById(R.id.cart_avatar);
            productName = (TextView) itemView.findViewById(R.id.cart_name);
            productPrice = (TextView) itemView.findViewById(R.id.cart_price);
            recycler = (RecyclerView) itemView.findViewById(R.id.cart_recyclerview);
            txtSoLuong = (TextView) itemView.findViewById(R.id.cart_Soluong);
            btnAddPro = (ImageButton) itemView.findViewById(R.id.cart_buttonAdd);
            btnSubPro = (ImageButton) itemView.findViewById(R.id.cart_buttonSub);
            btnRemovePro = (ImageButton) itemView.findViewById(R.id.cart_buttonDelete);
            txtSubTotal = (TextView) itemView.findViewById(R.id.cart_subtotal);
        }


    }
}
