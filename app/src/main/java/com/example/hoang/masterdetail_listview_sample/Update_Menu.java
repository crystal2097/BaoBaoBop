package com.example.hoang.masterdetail_listview_sample;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hoang.masterdetail_listview_sample.UI.PicassoClient;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Update_Menu extends AppCompatActivity {
    EditText etName,etGia;
    Button btnUpdate,btnDelete,btnchoose;
    ImageView imageView;
    final int CODE_GALLERY_REQUEST=999;
    String urlUpload="https://dochibao1997.000webhostapp.com/updatemenu.php";
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__menu);

        etName = (EditText) findViewById(R.id.etName);
        etGia = (EditText) findViewById(R.id.etGia);
        imageView=(ImageView) findViewById(R.id.imageView);

        String name=getIntent().getExtras().getString("Tenmon_key");
        String gia=getIntent().getExtras().getString("Gia_key");
        final int id=getIntent().getExtras().getInt("ID_Key");
        String imgurl=getIntent().getExtras().getString("IMG_Key");
        etName.setText(name);
        etGia.setText(gia);
        PicassoClient.downloadImage(this, imgurl, imageView);


        //Chọn ảnh update
        btnchoose=(Button) findViewById(R.id.imgChoose);
        btnchoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        Update_Menu.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        CODE_GALLERY_REQUEST
                );
            }
        });

        //Update
        btnUpdate=(Button) findViewById(R.id.btnupdatemenu);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = etName.getText().toString();
                final String gia = etGia.getText().toString();

                //post img to sv
                StringRequest stringRequest = new StringRequest(Request.Method.POST, urlUpload, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                        finish();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "error" + error.toString(), Toast.LENGTH_LONG).show();

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        String imageData = imageToString(bitmap);
                        params.put("name",name);
                        params.put("gia",gia);
                        params.put("image", imageData);
                        params.put("id",String.valueOf(id));
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(Update_Menu.this);
                requestQueue.add(stringRequest);
            }
        });


        ///Delete
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==CODE_GALLERY_REQUEST){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent,"Vui lòng chọn ảnh"),CODE_GALLERY_REQUEST);
            }else {
                Toast.makeText(getApplicationContext(),"Bạn không có quyền truy cập thư mục ảnh!",Toast.LENGTH_LONG).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==CODE_GALLERY_REQUEST && resultCode== RESULT_OK && data!=null){
            Uri filepath=data.getData();
            try {
                InputStream inputStream=getContentResolver().openInputStream(filepath);
                bitmap= BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private String imageToString(Bitmap bitmap){
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        bitmap .compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        byte[] imageBytes= outputStream.toByteArray();
        String encodedImage= Base64.encodeToString(imageBytes,Base64.DEFAULT);
        return encodedImage;
    }
}
