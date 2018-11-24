package com.example.hoang.masterdetail_listview_sample;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import android.view.*;
import android.app.ActionBar;
import android.os.AsyncTask;

import com.example.hoang.masterdetail_listview_sample.DataObject.LoaiSanPham;
import com.example.hoang.masterdetail_listview_sample.DataObject.SanPham;
import com.example.hoang.masterdetail_listview_sample.Interface.AddorRemoveCallbacks;
import com.example.hoang.masterdetail_listview_sample.RecyclerAdapter_order.RecyclerAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;


public class OrderActivity extends AppCompatActivity implements AddorRemoveCallbacks {
    Toolbar toolbar;
    TabLayout tabLayout;
    SharedPreferences preferences;
    private static int cart_count=0;
    ProgressDialog pd;
    int intScene = 0;
    LoaiSanPham loaiSanPham;
    SanPham sanPham;
    RecyclerView mRecyclerView;
    RecyclerAdapter mAdapter;
    ArrayList<LoaiSanPham> loaiSanPhams = new ArrayList<>();
    ArrayList<SanPham> sanPhams = new ArrayList<>();


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
        initProduct();



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

        new JsonTask_getloaisp().execute("https://dochibao1997.000webhostapp.com/Order-SelectLoaiSP.php");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_manu, menu);
        MenuItem menuItem = menu.findItem(R.id.cart_action);
        menuItem.setIcon(OrderConverter.convertLayoutToImage(OrderActivity.this,cart_count,R.drawable.ic_cart));
        return true;
    }

    private void initProduct()
    {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);
        GridLayoutManager mLayoutManager = new GridLayoutManager(this,2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        new JsonTask_getsp().execute("https://dochibao1997.000webhostapp.com/displayprofile.php");

    }
    @Override
    public void onAddProduct() {
        cart_count++;
        invalidateOptionsMenu();
        Snackbar.make(findViewById(R.id.drawer_design_support_layout), "Đã thêm vào hóa đơn", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();


    }

    @Override
    public void onRemoveProduct() {
        cart_count--;
        invalidateOptionsMenu();
        Snackbar.make(findViewById(R.id.drawer_design_support_layout), "Đã xóa khỏi hóa đơn ", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

    }

    private class JsonTask_getloaisp extends AsyncTask <String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(OrderActivity.this);
            pd.setMessage("Please wait");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)
                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            //Display sp
            if (pd.isShowing()){pd.dismiss();}

            if(loaiSanPhams.size()>0) {
                for (int i = 0; i <= loaiSanPhams.size(); i++) {
                    String tenloai = loaiSanPhams.get(i).getTenloai();
                    tabLayout.addTab(tabLayout.newTab().setText(tenloai));
                }
            }
            try {
                JSONArray ja = new JSONArray(result);
                JSONObject jo;

                loaiSanPhams.clear();
                LoaiSanPham loaiSanPham;
                tabLayout.addTab(tabLayout.newTab().setText("Tất cả"));

                for (int i = 0; i < ja.length(); i++) {
                    jo = ja.getJSONObject(i);
                    int id = jo.getInt("MaLoai");
                    String tenloai = jo.getString("TenLoai");
                    loaiSanPham = new LoaiSanPham();
                    loaiSanPham.setId(id);
                    loaiSanPham.setTenloai(tenloai);

                    loaiSanPhams.add(loaiSanPham);
                    tabLayout.addTab(tabLayout.newTab().setText(loaiSanPham.getTenloai()));

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            }
    }

    private class JsonTask_getsp extends AsyncTask <String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)
                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            //Display loaisp

            try {
                JSONArray ja = new JSONArray(result);
                JSONObject jo;

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
                    sanPhams.add(sanPham);
                }
                mAdapter = new RecyclerAdapter(OrderActivity.this,sanPhams);
                mRecyclerView.setAdapter(mAdapter);



            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}



