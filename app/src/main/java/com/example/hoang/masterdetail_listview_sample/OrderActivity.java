package com.example.hoang.masterdetail_listview_sample;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import org.w3c.dom.Text;

public class OrderActivity extends AppCompatActivity {
    TextView user, quyen;
    Toolbar toolbar;
    TabLayout tabLayout;
    SharedPreferences preferences;
    int intScene = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
//        user = (TextView) findViewById(R.id.user);
//        quyen = (TextView) findViewById(R.id.quyen);
//        preferences = this.getSharedPreferences("MYPREFS", Context.MODE_PRIVATE);
//
//        String mUser = preferences.getString("user", "Error getting user");
//        Log.d("Orderactivi", preferences.getString("user", "t"));
//        String mQuyen = preferences.getString("quyen", "Error getting quyen");
//        user.setText(mUser);
//        quyen.setText(mQuyen);
        initDrawer();


    }

    @Override
    public void onBackPressed() {
        if (intScene == 0) {
            Toast.makeText(getApplicationContext(), "Không thể back", Toast.LENGTH_SHORT).show();
        }
    }

    private void initDrawer() {
        toolbar = (Toolbar) findViewById(R.id.order_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Order");

        tabLayout = (TabLayout) findViewById(R.id.order_tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("Trà sữa"));
        tabLayout.addTab(tabLayout.newTab().setText("Macchiatto"));
        tabLayout.addTab(tabLayout.newTab().setText("Ăn vặt"));


    }
}

