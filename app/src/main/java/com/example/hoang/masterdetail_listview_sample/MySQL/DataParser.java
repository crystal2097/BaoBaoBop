package com.example.hoang.fetchfromdb_test.MySQL;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hoang.masterdetail_listview_sample.DataObject.SanPham;
import com.example.hoang.masterdetail_listview_sample.UI.CustomAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DataParser extends AsyncTask<Void, Void, Boolean> {
    Context c;
    String jsonData;
    ListView lv;

    ProgressDialog pd;
    ArrayList<SanPham> sanphams = new ArrayList<>();


    public DataParser(Context c, String jsonData, ListView lv) {
        this.c = c;
        this.jsonData = jsonData;
        this.lv = lv;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(c);
        pd.setTitle("Parse");
        pd.setMessage("Parsing..Please wait");
        pd.show();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        return this.parseData();
    }

    @Override
    protected void onPostExecute(Boolean parsed) {
        super.onPostExecute(parsed);

        pd.dismiss();

        if (parsed) {
            //BIND
            CustomAdapter adapter = new CustomAdapter(c, sanphams);
            lv.setAdapter(adapter);
        } else {
            Toast.makeText(c, "Unable To Parse", Toast.LENGTH_SHORT).show();
        }
    }

    private Boolean parseData() {
        try {
            JSONArray ja = new JSONArray(jsonData);
            JSONObject jo;

            sanphams.clear();
            SanPham sanPham;

            for (int i = 0; i < ja.length(); i++) {
                jo = ja.getJSONObject(i);
                int id = jo.getInt("MaSanPham");
                String tensp = jo.getString("TenMon");
                String imgurl = jo.getString("IMG");
                String gia = jo.getString("Gia");
                sanPham = new SanPham();
                sanPham.setId(id);
                sanPham.setTensp(tensp);
                sanPham.setImgurl(imgurl);
                sanPham.setGia(gia);
                sanphams.add(sanPham);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}