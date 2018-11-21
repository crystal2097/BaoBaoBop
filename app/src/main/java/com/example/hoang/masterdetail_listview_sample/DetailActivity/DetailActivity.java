package com.example.hoang.masterdetail_listview_sample.DetailActivity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.hoang.masterdetail_listview_sample.R;
import com.example.hoang.masterdetail_listview_sample.UI.PicassoClient;

public class DetailActivity extends AppCompatActivity {
    TextView nameTxt;
    ImageView img;
    TextView gia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        nameTxt = (TextView) findViewById(R.id.nameTxt);
        img = (ImageView) findViewById(R.id.productdetailImg);
        gia = (TextView) findViewById(R.id.giaTxt);
        //Recieve Data
        Intent i = this.getIntent();
        String name = ((Intent) i).getExtras().getString("TENMON_KEY");
        String imgurl = ((Intent) i).getExtras().getString("IMGURL_KEY");
        String giasp = i.getExtras().getString("GIA_KEY");
        //BIND
        nameTxt.setText(name);
        gia.setText(giasp);
        PicassoClient.downloadImage(this, imgurl, img);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }
}