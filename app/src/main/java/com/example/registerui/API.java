package com.example.registerui;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface API {

    @FormUrlEncoded
    @POST("register.php")
    Call<ResponseBody> registration(
            @Field("EMAIL") String email,
            @Field("PASSWORD") String pass,
            @Field("NUMBER") String num
    );

    @FormUrlEncoded
    @POST("login.php")
    Call<ResponseBody> login(
            @Field("PASSWORD") String pass,
            @Field("NUMBER") String num
    );
}
