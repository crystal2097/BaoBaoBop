package com.example.hoang.masterdetail_listview_sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.example.hoang.masterdetail_listview_sample.MySQL.Downloader;

public class MainActivity extends AppCompatActivity {

    final static String urlAddress = "https://dochibao1997.000webhostapp.com/displayprofile.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ListView lv = (ListView) findViewById(R.id.lv);
        new Downloader(MainActivity.this, urlAddress, lv).execute();


//        Xu ly su kien Floating Button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent i= new Intent(getApplicationContext(),InsertMenu.class);
               startActivity(i);
            }
        });
    }
    @Override
    public void onResume(){
        super.onResume();
        final ListView lv = (ListView) findViewById(R.id.lv);
        new Downloader(MainActivity.this, urlAddress, lv).execute();

    }


}
