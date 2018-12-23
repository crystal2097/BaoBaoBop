package com.example.hoang.masterdetail_listview_sample;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.hoang.masterdetail_listview_sample.DataObject.SanPham;
import com.example.hoang.masterdetail_listview_sample.Interface.OrderAddSubClear;
import com.example.hoang.masterdetail_listview_sample.RecyclerAdapter.RecyclerAdapter_cart;
import com.example.hoang.masterdetail_listview_sample.UI.Cart_sendJson;
import com.google.gson.JsonIOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity implements OrderAddSubClear {
    Toolbar toolbar;
    ArrayList<SanPham> productsList;
    SharedPreferences preferences;
    SharedPreferences Cartketqua;
    RecyclerView mRecyclerView;
    RecyclerAdapter_cart mAdapter;
    Context context;
    Activity activity;
    TextView txtSoLuong;
    Float TongTien;

    FloatingActionButton fab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = this.getSharedPreferences("MYPREFS", Context.MODE_PRIVATE);
        Cartketqua = this.getSharedPreferences("CART_KQ", Context.MODE_PRIVATE);
        productsList = new ArrayList<SanPham>();
        setContentView(R.layout.activity_cart);
        context = this;
        txtSoLuong = (TextView) findViewById(R.id.cart_Soluong);
        productsList = getIntent().getParcelableArrayListExtra("itemcart");
        mRecyclerView = (RecyclerView) findViewById(R.id.cart_recyclerview);
        //Create float button -- comfirm order
        fab = (FloatingActionButton) findViewById(R.id.cart_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject JSonObjcart = new JSONObject();
                JSONArray JsonArrProducts = new JSONArray();
                JSONArray JSonArrcart = new JSONArray();

                try {
                    JSonObjcart.put("User", preferences.getString("user", "Error getting user"));

                    for (int i = 0; i < productsList.size(); i++) {
                        JSONObject JSonObjProduct = new JSONObject();
                        JSonObjProduct.put("ID", productsList.get(i).getId());
                        JSonObjProduct.put("SoLuong", productsList.get(i).getSoluong());
                        JSonObjProduct.put("Gia", SubString(productsList.get(i).getGia()));
                        JsonArrProducts.put(JSonObjProduct);

                    }
                    JSONObject jsonObjectAllProducts = new JSONObject();
                    jsonObjectAllProducts.put("Products", JsonArrProducts);
                    JSonArrcart.put(JSonObjcart);
                    JSonArrcart.put(jsonObjectAllProducts);
                } catch (JsonIOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //send to php
                Cart_sendJson cart_sendJson = new Cart_sendJson(context);
                //cart_sendJson.execute(JSonArrcart);
                Object result = cart_sendJson.execute(JSonArrcart).getStatus();

                if (Cartketqua.getString("Ketqua", "Fail").equals("OK")) {
                    finish();
                }
            }
        });

        toolbar = (Toolbar) findViewById(R.id.cart_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.checkout));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        mRecyclerView = (RecyclerView) findViewById(R.id.cart_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        mRecyclerView.setLayoutManager(mLayoutManager);

        if (!productsList.isEmpty()) {
            mAdapter = new RecyclerAdapter_cart(this, productsList);
            mRecyclerView.setAdapter(mAdapter);
        }


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onAddProduct(int Pos) {
        productsList.get(Pos).setSoluong(productsList.get(Pos).getSoluong() + 1);
    }

    @Override
    public void onSubProduct(int Pos) {
        if ((productsList.get(Pos).getSoluong() - 1) != 0)
            productsList.get(Pos).setSoluong(productsList.get(Pos).getSoluong() - 1);
    }

    @Override
    public void onRemoveProduct(int Pos) {
    }

    public float SubString(String string) {
        String[] split = string.split("VND");
        String firstSubString = split[0].substring(0, split[0].length() - 1);
        return Integer.parseInt(firstSubString);
    }

}




