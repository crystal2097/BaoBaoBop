package com.example.hoang.masterdetail_listview_sample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class Update_Menu extends AppCompatActivity {
EditText etName,etGia,etIMGURL;
Button btnUpdate,btnDelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__menu);

        etName = (EditText) findViewById(R.id.etName);
        etGia = (EditText) findViewById(R.id.etGia);
        etIMGURL = (EditText) findViewById(R.id.etIMGURL);

        String name=getIntent().getExtras().getString("Tenmon_key");
        String gia=getIntent().getExtras().getString("Gia_key");
        String imgurl=getIntent().getExtras().getString("IMG_Key");
        final int id=getIntent().getExtras().getInt("ID_Key");
        etName.setText(name);
        etGia.setText(gia);
        etIMGURL.setText(imgurl);

        btnUpdate=(Button) findViewById(R.id.btnupdatemenu);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String name = etName.getText().toString();
            String gia = etGia.getText().toString();
            String Imgurl = etIMGURL.getText().toString();
            String masp=String.valueOf(id);
                if (name.isEmpty() || (gia.isEmpty())) {
                    Toast.makeText(Update_Menu.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    etName.setError("Không có tên");
                    etGia.setError("Không có giá");
                }else
                {
                    Ion.with(Update_Menu.this)
                            .load("https://dochibao1997.000webhostapp.com/updatemenu.php")
                            .setLogging("LOIUpdate", Log.DEBUG)
                            .setBodyParameter("name", name)
                            .setBodyParameter("gia", gia)
                            .setBodyParameter("imgurl", Imgurl)
                            .setBodyParameter("id",masp)
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {

                                    if(result.get("result").getAsString().equals("Ok")){
                                        Toast.makeText(Update_Menu.this,"Sửa thành công",Toast.LENGTH_SHORT).show();
                                        finish();
                                    }else{
                                        Toast.makeText(Update_Menu.this,"Không thành công",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
        btnDelete=(Button) findViewById(R.id.btndelete);
        btnDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String masp=String.valueOf(id);
                Ion.with(Update_Menu.this)
                        .load("https://dochibao1997.000webhostapp.com/deletemenu.php")
                        .setLogging("LOIDelete", Log.DEBUG)
                        .setBodyParameter("id",masp)
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {

                                if(result.get("result").getAsString().equals("Ok")){
                                    Toast.makeText(Update_Menu.this,"Xóa thành công",Toast.LENGTH_SHORT).show();
                                    finish();
                                }else{
                                    Toast.makeText(Update_Menu.this,"Không thành công",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
