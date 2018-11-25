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
import com.example.hoang.masterdetail_listview_sample.Update_Menu;

public class DetailActivity extends AppCompatActivity {
    TextView nameTxt;
    ImageView img;
    TextView gia;
    String namep,price,pic;
    int masp;

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
        final int id=i.getExtras().getInt("ID");
        final String name = ((Intent) i).getExtras().getString("TENMON_KEY");
        final String imgurl = ((Intent) i).getExtras().getString("IMGURL_KEY");
        String giasp = i.getExtras().getString("GIA_KEY");
        //BIND
        nameTxt.setText(name);
        gia.setText(giasp);
        PicassoClient.downloadImage(this, imgurl, img);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(DetailActivity.this,Update_Menu.class);
                masp=id;
                namep=nameTxt.getText().toString();
                price=gia.getText().toString();
                pic=imgurl;
                intent.putExtra("Tenmon_key", namep);
                intent.putExtra("Gia_key",price);
                intent.putExtra("IMG_Key",pic);
                intent.putExtra("ID_Key",masp);
                startActivity(intent);
                finish();
            }
        });
    }
}