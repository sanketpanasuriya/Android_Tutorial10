package com.example.tutorial10;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    ListView lstData;
    MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lstData = findViewById(R.id.lstData);
        new MyAsyncTask().execute();

        lstData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this,Userdata.class);
                intent.putExtra("id",i);
                startActivity(intent);
            }
        });
    }

    class MyAsyncTask extends AsyncTask{
        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setTitle("Please wait...");
            dialog.show();
        }

       @Override
        protected Object doInBackground(Object[] objects) {


            StringBuilder response = new StringBuilder();
            try {
                URL url = new URL(MyUtil.USER_URL);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStreamReader ir = new InputStreamReader(urlConnection.getInputStream());
                BufferedReader br = new BufferedReader(ir);
                String inputline = null;

                while((inputline = br.readLine())!=null){
                    response.append(inputline);
                }
                br.close();
                ir.close();

                MyUtil.UserData = new JSONArray(response.toString());


            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
           return null;
        }
        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            adapter = new MyAdapter(MainActivity.this,MyUtil.UserData);
            lstData.setAdapter(adapter);
            if(dialog.isShowing()) dialog.dismiss();
        }
    }
}