package com.example.hoang.masterdetail_listview_sample.UI;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.example.hoang.masterdetail_listview_sample.R;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
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

public class Cart_sendJson extends AsyncTask<JSONArray, Void, String> {
    Context context;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private ProgressDialog pdWaiting;


    public Cart_sendJson(Context ctx) {
        this.context = ctx;
    }

    @Override
    protected String doInBackground(JSONArray... jsonArrays) {

        String urlLogin = "https://dochibao1997.000webhostapp.com/Cart_InsertOrder.php";
        try {
            URL url = new URL(urlLogin);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            //send the email and password to the database
            OutputStream outputStream = httpURLConnection.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            String myData = URLEncoder.encode("Item", "UTF-8") + "=" + URLEncoder.encode(jsonArrays[0].toString(), "UTF-8");

            Log.d("weblog", myData);
            bufferedWriter.write(myData);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            //get response from the database
            InputStream inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String dataResponse = "";
            String inputLine = "";
            while ((inputLine = bufferedReader.readLine()) != null) {
                dataResponse += inputLine;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();

            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            System.out.println(dataResponse);

            return dataResponse;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pdWaiting = new ProgressDialog(context);
        pdWaiting.setMessage(context.getString(R.string.pleasewait));
        pdWaiting.show();
        preferences = context.getSharedPreferences("CART_KQ", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (pdWaiting.isShowing()) pdWaiting.dismiss();

        if (s.equals("OK")) {
            editor.putString("Ketqua", "OK");
            editor.commit();
        } else {
            editor.putString("Ketqua", "Fail");
            editor.commit();
        }

    }


    public void display(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
