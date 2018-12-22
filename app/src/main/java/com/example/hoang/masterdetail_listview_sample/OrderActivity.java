package com.example.hoang.masterdetail_listview_sample;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import android.view.*;
import android.os.AsyncTask;
import android.content.Context;


import com.example.hoang.masterdetail_listview_sample.DataObject.LoaiSanPham;
import com.example.hoang.masterdetail_listview_sample.DataObject.SanPham;
import com.example.hoang.masterdetail_listview_sample.Interface.AddorRemoveCallbacks;
import com.example.hoang.masterdetail_listview_sample.RecyclerAdapter.RecyclerAdapter;
import com.example.hoang.masterdetail_listview_sample.UI.Cart_sendJson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class OrderActivity extends AppCompatActivity implements AddorRemoveCallbacks {
    Toolbar toolbar;
    TabLayout tabLayout;
    Context context;
    private static int cart_count=0;
    ProgressDialog pd;
    int intScene = 0;
    LoaiSanPham loaiSanPham;
    SanPham sanPham;
    RecyclerView mRecyclerView;
    RecyclerAdapter mAdapter;
    public static int OPEN_NEW_ACTIVITY = 1;
    SharedPreferences Cartketqua;
    SharedPreferences.Editor editor;
    ArrayList<LoaiSanPham> loaiSanPhams = new ArrayList<>();
    ArrayList<SanPham> sanPhams = new ArrayList<>();
    ArrayList<SanPham> itemcart = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Cartketqua = this.getSharedPreferences("CART_KQ", Context.MODE_PRIVATE);
        initDrawer();
        editor = Cartketqua.edit();
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

    // Start activity cart
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cart_action:
                Intent intent = new Intent(this, CartActivity.class);
                intent.putParcelableArrayListExtra("itemcart", itemcart);
                startActivityForResult(intent, OPEN_NEW_ACTIVITY);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == OPEN_NEW_ACTIVITY) {
            String KQ = Cartketqua.getString("Ketqua", "Fail");

            if (KQ.equals("OK")) {
                itemcart.clear();
                editor.clear();
                cart_count = 0;
                invalidateOptionsMenu();
            }

        }
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
    public void onAddProduct(int maSP) {
        cart_count++;
        invalidateOptionsMenu();

        SanPham sanPham = new SanPham();
        sanPham = sanPhams.get(maSP);
        if (sanPham.getSoluong() >= 1) {
            sanPham.setSoluong(sanPham.getSoluong() + 1);
            sanPhams.set(maSP, sanPham);
            int IDcartitem = sanPham.getId();
            for (SanPham sanPham1 : sanPhams) {
                if (sanPham1.getId() == IDcartitem) {
                    itemcart.indexOf(sanPham1);
                }
            }
        } else {
            sanPham.setSoluong(1);
            sanPhams.set(maSP, sanPham);
            itemcart.add(sanPham);
        }


        Snackbar.make(findViewById(R.id.drawer_design_support_layout), "Đã thêm vào hóa đơn " + sanPham.getSoluong(), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public void onRemoveProduct(int maSP) {
        if (cart_count != 0) {
            cart_count--;
            invalidateOptionsMenu();
            Snackbar.make(findViewById(R.id.drawer_design_support_layout), "Đã xóa khỏi hóa đơn ", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
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
                    sanPham.setGia(gia.concat(" VND"));
                    sanPham.setSoluong(0);
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



