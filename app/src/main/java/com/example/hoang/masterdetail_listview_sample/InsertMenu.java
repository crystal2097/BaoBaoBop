package com.example.hoang.masterdetail_listview_sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class InsertMenu extends AppCompatActivity {
    EditText editName, editGia, editImgUrl;
    Button btninsertsp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_menu);

        editName = (EditText) findViewById(R.id.etName);
        editGia = (EditText) findViewById(R.id.etGia);
        editImgUrl = (EditText) findViewById(R.id.etIMGURL);

        btninsertsp = (Button) findViewById(R.id.addsp);
        btninsertsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getText().toString();
                String gia = editGia.getText().toString();
                String Imgurl = editImgUrl.getText().toString();
                if (name.isEmpty() || (gia.isEmpty())) {
                    Toast.makeText(InsertMenu.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    editName.setError("Không có tên");
                    editGia.setError("Không có giá");
                } else {

                    Ion.with(InsertMenu.this)
                            .load("http://192.168.1.5/QLTS/insertmenu.php")
                            .setLogging("LOIQUANGI", Log.DEBUG)
                            .setBodyParameter("name", name)
                            .setBodyParameter("gia", gia)
                            .setBodyParameter("imgurl", Imgurl)
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {

                                    if(result.get("result").getAsString().equals("Ok")){
                                        Toast.makeText(InsertMenu.this,"Thêm thành công",Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(InsertMenu.this,"Ngu nhu bo",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

    }
}
