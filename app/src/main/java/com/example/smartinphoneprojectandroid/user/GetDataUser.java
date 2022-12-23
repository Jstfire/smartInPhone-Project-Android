package com.example.smartinphoneprojectandroid.user;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface GetDataUser {

    @Multipart
    @POST("register")
    Call<RegisterResponse> register(
            @Part MultipartBody.Part avatar,
            @Part("email")RequestBody email,
            @Part("name")RequestBody name,
            @Part("username")RequestBody username,
            @Part("password")RequestBody password,
            @Part("confpassword")RequestBody confpassword
            );

    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> login(
            @Field("email") String email,
            @Field("password") String password
    );

    @GET("me")
    Call<UserModel> getUserInfo(
            @Header("Authorization") String token
    );

    @Multipart
    @POST("user/updateAvatar/{id}")
    Call<LoginResponse> changeAvatar(
            @Header("Authorization") String token,
            @Path("id") String id,
            @Part MultipartBody.Part avatar
    );

    @Multipart
    @POST("user/updateProfile/{id}")
    Call<LoginResponse> changeProfile(
            @Header("Authorization") String token,
            @Path("id") String id,
            @Part("email")RequestBody email,
            @Part("name")RequestBody name,
            @Part("username")RequestBody username
    );

    @Multipart
    @POST("user/updatePassword/{id}")
    Call<LoginResponse> changePassword(
            @Header("Authorization") String token,
            @Path("id") String id,
            @Part("password")RequestBody password,
            @Part("confpassword")RequestBody confpassword
    );
}
