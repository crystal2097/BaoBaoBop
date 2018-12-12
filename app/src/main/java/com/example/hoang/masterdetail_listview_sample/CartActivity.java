package com.example.hoang.masterdetail_listview_sample;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.hoang.masterdetail_listview_sample.DataObject.SanPham;
import com.example.hoang.masterdetail_listview_sample.RecyclerAdapter.RecyclerAdapter_cart;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    Toolbar toolbar;
    ArrayList<SanPham> productsList;
    RecyclerView mRecyclerView;
    RecyclerAdapter_cart mAdapter;
    Context context;
    Activity activity;

    FloatingActionButton fab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productsList = new ArrayList<SanPham>();
        setContentView(R.layout.activity_cart);
        context = this;

        SanPham sp = new SanPham();
        sp.setTensp("teest");
        sp.setSoluong(2);
        sp.setGia("1111");
        sp.setImgurl("https://phannguyenkhanhdan.files.wordpress.com/2017/10/milktea-ryanradley.png");
        sp.setId(1);

        productsList = getIntent().getParcelableArrayListExtra("itemcart");

        //Create float button -- comfirm order
        fab = (FloatingActionButton) findViewById(R.id.cart_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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

//    @Override
//    public void setAdd(Integer position) {
//        Integer a = productsList.get(position).getSoluong();
//        productsList.get(position).setSoluong(a++);
//        mAdapter.notifyDataSetChanged();
//    }
}

