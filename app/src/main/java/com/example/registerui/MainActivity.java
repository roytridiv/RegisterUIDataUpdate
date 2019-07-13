package com.example.registerui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView haveAccount , reg;
    EditText e , p , n ;

    public static  API api;


    MyPreferenceManager myPreferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        e = findViewById(R.id.email);
        p = findViewById(R.id.password);
        n = findViewById(R.id.phone);

        myPreferenceManager = new MyPreferenceManager(MainActivity.this);

        if(myPreferenceManager.getLoginStatus()){
            final android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(MainActivity.this);
            alertDialogBuilder.setTitle("Message");
            alertDialogBuilder.setMessage("Hi you are logged in");
            alertDialogBuilder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            final AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        if(!myPreferenceManager.getEmail().equals("DN")){
            e.setText(myPreferenceManager.getEmail());
        }

        if(!myPreferenceManager.getMobile().equals("DN")){
            n.setText(myPreferenceManager.getMobile());
        }

        api = RetrofitClient.getInstance().getApi();

        haveAccount = findViewById(R.id.have_account);
        reg = findViewById(R.id.regBut);

        haveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registration();
            }
        });
    }

    public void registration(){
        String email = e.getText().toString();
        String pass = p.getText().toString();
        String num = n.getText().toString();

        myPreferenceManager.setData(email,num);

        myPreferenceManager.setLoginStatus();

        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .registration(email,pass,num);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String resp = null;
                if(response.body() != null){
                    try {
                        resp = response.body().string();
                        try {
                            JSONObject jsonObject = new JSONObject(resp);

                            Toast.makeText(MainActivity.this,jsonObject.getString("message"),Toast.LENGTH_LONG).show();

                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }

                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });


        e.setText("");
        p.setText("");
        n.setText("");

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
