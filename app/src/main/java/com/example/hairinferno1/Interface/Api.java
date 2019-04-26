package com.example.hairinferno1.Interface;

import com.example.hairinferno1.Modal.Example;
import com.example.hairinferno1.Modal.ExampleHome;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    String BASE_URL="http://hairinfernodev.appinventive.com/";

    @FormUrlEncoded
    @POST("signup")
    Call<Example> postSignup(@Field("first_name") String first_name, @Field("email") String email, @Field("password") String password, @Field("user_type")String user_type, @Field("device_id")String device_id
            , @Field("platform")String platform, @Field("address")String address, @Field("latitude")String latitude, @Field("longitude")String longitude);

    @FormUrlEncoded
    @POST("login")
    Call<ResponseBody> postLogin(@Field("email")String email,@Field("password")String password,@Field("device_id")String device_id,
                                 @Field("device_token")String device_token,@Field("platform")String platform);

    @GET("post-list")
    Call<ExampleHome> getData(@Query("type")String type, @Query("page")String page,
                              @Query("user_type")String user_type);

}
