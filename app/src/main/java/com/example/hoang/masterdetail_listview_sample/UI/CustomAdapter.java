package com.example.hoang.masterdetail_listview_sample.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hoang.masterdetail_listview_sample.DataObject.SanPham;
import com.example.hoang.masterdetail_listview_sample.DetailActivity.DetailActivity;
import com.example.hoang.masterdetail_listview_sample.R;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    Context c;
    ArrayList<SanPham> sanphams;

    public CustomAdapter(Context c, ArrayList<SanPham> sanphams) {
        this.c = c;
        this.sanphams = sanphams;
    }

    @Override
    public int getCount() {
        return sanphams.size();
    }

    @Override
    public Object getItem(int i) {
        return sanphams.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(c).inflate(R.layout.model, viewGroup, false);
        }

        TextView nametxt = (TextView) view.findViewById(R.id.nameTxt);
        ImageView img = (ImageView) view.findViewById(R.id.productImg);

        final SanPham s = (SanPham) this.getItem(i);
        nametxt.setText(s.getTensp());
        PicassoClient.downloadImage(c, s.getImgurl(), img);
        //ITEM CLICK
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //OPEN Detail Master
                openDetailActivity(s.getId(),s.getTensp(), s.getImgurl(), s.getGia());
            }
        });
        return view;
    }

    //Open Detail Activity
    private void openDetailActivity(int id,String tensp, String imgurl, String giasp) {
        Intent i = new Intent(c, DetailActivity.class);

        //Data
        i.putExtra("ID",id);
        i.putExtra("TENMON_KEY", tensp);
        i.putExtra("IMGURL_KEY", imgurl);
        i.putExtra("GIA_KEY", giasp);
        c.startActivity(i);
    }
}
