package com.example.hoang.masterdetail_listview_sample;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.hoang.masterdetail_listview_sample.DataObject.ChiTietOrder;
import com.example.hoang.masterdetail_listview_sample.DataObject.Order;
import com.example.hoang.masterdetail_listview_sample.Interface.SetTinhTrangListener;
import com.example.hoang.masterdetail_listview_sample.RecyclerAdapter.RecyclerAdapter_daubep;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

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


public class DauBepActivity extends AppCompatActivity implements SetTinhTrangListener {
    RecyclerView mRecyclerView;
    RecyclerAdapter_daubep mAdapter;
    Order order;
    ChiTietOrder chiTietOrder;
    Toolbar toolbar;
    String url;
    ArrayList<Order> orders = new ArrayList<Order>();
    ArrayList<ChiTietOrder> chiTietOrders = new ArrayList<ChiTietOrder>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Order order = new Order();
//        order.setMaOrder(1);
//        order.setThoiGian("15:20");
//        order.setUsername("Bao");
//        orders.add(order);

        setContentView(R.layout.activity_daubep);
        mRecyclerView = (RecyclerView) findViewById(R.id.daubep_recyclerview);
        toolbar = (Toolbar) findViewById(R.id.daubep_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.Daubep));
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
        }
        mRecyclerView = (RecyclerView) findViewById(R.id.daubep_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        mRecyclerView.setLayoutManager(mLayoutManager);
        url = "https://dochibao1997.000webhostapp.com/Daubep-select.php";
        new JsonTask_getsp().execute(url);
    }

    @Override
    public void SetTinhTrang(int Pos) {

        Log.d("addd", Integer.toString(orders.get(Pos).getMaOrder()));

        Log.d("addd", Integer.toString(chiTietOrders.get(Pos).getMaSP()));

        Ion.with(DauBepActivity.this)
                .load("https://dochibao1997.000webhostapp.com/Daubep-update.php")
                .setLogging("LOIQUANGI", Log.DEBUG)
                .setBodyParameter("MaOrder", Integer.toString(orders.get(Pos).getMaOrder()))
                .setBodyParameter("MaSP", Integer.toString(chiTietOrders.get(Pos).getMaSP()))
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result.get("result").getAsString().equals("OK")) {
                            Toast.makeText(DauBepActivity.this, "Hoàn thành", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(DauBepActivity.this, "Không thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

    private class JsonTask_getsp extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
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
                    buffer.append(line + "\n");
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
            try {
                JSONArray ja = new JSONArray(result);
                JSONObject jo;
                for (int i = 0; i < ja.length(); i++) {
                    jo = ja.getJSONObject(i);
                    order = new Order();
                    order.setMaOrder(jo.getInt("MAORDER"));
                    order.setUsername(jo.getString("Username"));
                    order.setThoiGian(jo.getString("THOIGIAN"));
                    orders.add(order);
                    chiTietOrder = new ChiTietOrder();
                    chiTietOrder.setTenMon(jo.getString("TenMon"));
                    chiTietOrder.setIMGMon(jo.getString("IMG"));
                    chiTietOrder.setSoLuong(jo.getInt("SoLuong"));
                    chiTietOrder.setMaSP(jo.getInt("MaSanPham"));
                    chiTietOrders.add(chiTietOrder);
                }
                mAdapter = new RecyclerAdapter_daubep(DauBepActivity.this, orders, chiTietOrders);
                mRecyclerView.setAdapter(mAdapter);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.daubep_main, menu);
        MenuItem menuItem2 = menu.findItem(R.id.daubep_logout);
        menuItem2.setIcon(R.drawable.ic_baseline_power_settings_new_24px);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.daubep_logout:
                Intent intent2 = new Intent(this, LoginActivity.class);
                startActivity(intent2);

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
