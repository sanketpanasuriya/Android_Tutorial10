package com.example.tutorial10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class Userdata extends AppCompatActivity {
    TextView ID,Name,Username,Email,Address,Phone,Web,CompanyName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdata);

        Email= findViewById(R.id.writeEmail);
        CompanyName= findViewById(R.id.writeCompanyName);
        ID= findViewById(R.id.writeID);
        Name= findViewById(R.id.writeName);
        Username= findViewById(R.id.writeUsername);
        Address= findViewById(R.id.writeAddress);
        Phone= findViewById(R.id.writePhone);
        Web= findViewById(R.id.writeWeb);

        Intent intent = getIntent();
        int i = intent.getIntExtra("id",0);

        try {
            JSONObject userObj = MyUtil.UserData.getJSONObject(i);
            JSONObject address = userObj.getJSONObject("address");
            JSONObject geoAddress = address.getJSONObject("geo");
            JSONObject company = userObj.getJSONObject("company");
            ID.setText(userObj.getString("id"));
            Name.setText(userObj.getString("name"));
            Username.setText(userObj.getString("username"));
            Email.setText(userObj.getString("email"));
            Address.setText(address.getString("street") +", "+address.getString("suite")+",\n"+address.getString("city") +"-"+address.getString("zipcode")+",\n"+geoAddress.getString("lat") +" "+geoAddress.getString("lng"));
            //CityZipcode.setText();
            //LatLng.setText();
            Phone.setText(userObj.getString("phone"));
            Web.setText(userObj.getString("website"));
            CompanyName.setText("name : "+company.getString("name")+",\ncatchPhrase : "+company.getString("catchPhrase")+",\nbs : "+company.getString("bs"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void btnBackClick(View view) {
        Intent intent = new Intent(Userdata.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}