package com.example.hoang.masterdetail_listview_sample;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

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

public class BackgroundActivity extends AsyncTask<String, Void, String> {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context context;
    private ProgressDialog pdWaiting;

    BackgroundActivity(Context ctx) {
        this.context = ctx;
    }

    @Override
    protected String doInBackground(String... strings) {
        preferences = context.getSharedPreferences("MYPREFS", Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString("flag", "0");
        editor.commit();
        String urlLogin = "https://dochibao1997.000webhostapp.com/QLTS-Login.php";
        String task = strings[0];
        Log.d("weblog", strings[0] + strings[1] + strings[2]);
        Log.d("task", task);

        if (task.equals("Login")) {
            String loginUser = strings[1];
            String loginPassword = strings[2];
            Log.d("login", loginPassword + loginUser);
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
                String myData = URLEncoder.encode("identifier_loginUser", "UTF-8") + "=" + URLEncoder.encode(loginUser, "UTF-8") + "&"
                        + URLEncoder.encode("identifier_loginPassword", "UTF-8") + "=" + URLEncoder.encode(loginPassword, "UTF-8");
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

                editor.putString("flag", "login");
                editor.commit();
                return dataResponse;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pdWaiting = new ProgressDialog(context);
        pdWaiting.setMessage(context.getString(R.string.pleasewait));
        pdWaiting.show();
    }

    //This method willbe called when doInBackground completes... and it will return the completion string which
    //will display this toast.
    @Override
    protected void onPostExecute(String s) {
        String flag = preferences.getString("flag", "0");

        if (flag.equals("login")) {
            String test = "false";
            String user = "";
            String quyen = "";
            String[] serverResponse = s.split("[,]");
            test = serverResponse[0];
            user = serverResponse[1];
            quyen = serverResponse[2];
            Log.d("webLoginresp", test + " " + user + " " + quyen);
            if (test.equals("true")) {
                editor.putString("user", user);
                editor.commit();
                editor.putString("quyen", quyen);
                editor.commit();
                Log.d("editor", preferences.getString("user", "nothing"));
                // Quyen Admin
                if (quyen.equals("0")) {
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                }
                // Quyen nhan vien phuc vu
                if (quyen.equals("1")) {
                    Intent intent = new Intent(context, OrderActivity.class);
                    context.startActivity(intent);
                }
                // Quyen nhan vien order
            } else {
                display("Đăng nhập thất bại", "User và mật khẩu không đúng");
            }
        } else {
            display("Đăng nhập thất bại", "Lỗi không xác định E01");
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        if (pdWaiting.isShowing()) pdWaiting.dismiss();
    }

    public void display(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}

