package com.example.registerui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText num , pass ;
    Button but;


    String email , password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        num  = findViewById(R.id.number);
        pass = findViewById(R.id.password);
        but = findViewById(R.id.login);

        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performLogin();
            }
        });


    }


    public void performLogin(){
        email = num.getText().toString();
        password = pass.getText().toString();


        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .login(password,email);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String resp = null;
                try {
                    resp = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.d("MY_APP_DEBUG" , resp);

                Toast.makeText(LoginActivity.this , resp , Toast.LENGTH_SHORT ).show();
                if(response.body().toString().equals("ok")){

                    Toast.makeText(LoginActivity.this , "login hoise" , Toast.LENGTH_SHORT).show();
//                    try {
//                        resp = response.body().string();
//                        try {
//                            JSONObject jsonObject = new JSONObject(resp);
//
//                            Toast.makeText(MainActivity.this,jsonObject.getString("message"),Toast.LENGTH_LONG).show();
//
//                        } catch (JSONException e1) {
//                            e1.printStackTrace();
//                        }
//
//                    } catch (IOException e1) {
//                        e1.printStackTrace();
//                    }
                }else {
                    Toast.makeText(LoginActivity.this , "kisui hoi nai " , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }
}
