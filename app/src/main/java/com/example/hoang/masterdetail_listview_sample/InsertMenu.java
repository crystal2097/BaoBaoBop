package com.example.hoang.masterdetail_listview_sample;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class InsertMenu extends AppCompatActivity {
    EditText editName, editGia;
    ImageView imageUpload;
    Button btninsertsp,btnchoose,btnuploadIMG;
    final int CODE_GALLERY_REQUEST=999;
    String urlUpload="https://dochibao1997.000webhostapp.com/insertmenu2.php";
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_menu);

        editName = (EditText) findViewById(R.id.etName);
        editGia = (EditText) findViewById(R.id.etGia);
        imageUpload=(ImageView) findViewById(R.id.imageUpload);

        ////
        btnuploadIMG=(Button) findViewById(R.id.btnuploadIMG);
        btnuploadIMG.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                               final String name = editName.getText().toString();
                                               final String gia = editGia.getText().toString();
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
                                                        return params;
                                                    }
                                                };
                                                RequestQueue requestQueue = Volley.newRequestQueue(InsertMenu.this);
                                                requestQueue.add(stringRequest);
                                            }
                                        });



        ////
        btnchoose=(Button) findViewById(R.id.chonanh);
        btnchoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        InsertMenu.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        CODE_GALLERY_REQUEST
                );
            }
        });

        /////
//        btninsertsp = (Button) findViewById(R.id.addsp);
//        btninsertsp.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                String name = editName.getText().toString();
//                String gia = editGia.getText().toString();
//                String Imgurl = editImgUrl.getText().toString();
//                if (name.isEmpty() || (gia.isEmpty())) {
//                    Toast.makeText(InsertMenu.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
//                    editName.setError("Không có tên");
//                    editGia.setError("Không có giá");
//                } else {
//
//                    Ion.with(InsertMenu.this)
//                            .load("https://dochibao1997.000webhostapp.com/insertmenu.php")
//                            .setLogging("LOIQUANGI", Log.DEBUG)
//                            .setBodyParameter("name", name)
//                            .setBodyParameter("gia", gia)
//                            .setBodyParameter("imgurl", Imgurl)
//                            .asJsonObject()
//                            .setCallback(new FutureCallback<JsonObject>() {
//                                @Override
//                                public void onCompleted(Exception e, JsonObject result) {
//
//                                    if(result.get("result").getAsString().equals("Ok")){
//                                        Toast.makeText(InsertMenu.this,"Thêm thành công",Toast.LENGTH_SHORT).show();
//                                        finish();
//                                    }else{
//                                        Toast.makeText(InsertMenu.this,"Không thành công",Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            });
//                }
//            }
//        });

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
                imageUpload.setImageBitmap(bitmap);
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
