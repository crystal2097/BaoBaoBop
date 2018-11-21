package com.example.hoang.masterdetail_listview_sample.UI;

import android.content.Context;
import android.widget.ImageView;

import com.example.hoang.masterdetail_listview_sample.R;
import com.squareup.picasso.Picasso;

public class PicassoClient {
    public static void downloadImage(Context c, String imgurl, ImageView img) {
        if (imgurl != null && imgurl.length() > 0) {
            Picasso.get().load(imgurl).into(img);
        } else {
            Picasso.get().load(R.drawable.placeholder).into(img);
        }
    }
}
