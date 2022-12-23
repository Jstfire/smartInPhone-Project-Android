package com.example.smartinphoneprojectandroid.handphone;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface GetData {
    @GET("handphone")
    Call<List<HandphoneModel>> getHandphone();

    @FormUrlEncoded
    @POST("handphone/update/{id}")
    Call<UpdateHPResponse> updateHP(
            @Header("Authorization") String token,
            @Path("id") String id,
            @Field("name") String name,
            @Field("network") String network,
            @Field("launch") String launch,
            @Field("body") String body,
            @Field("display") String display,
            @Field("platform") String platform,
            @Field("memory") String memory,
            @Field("maincam") String maincam,
            @Field("selfcam") String selfcam,
            @Field("sound") String sound,
            @Field("comms") String comms,
            @Field("features") String features,
            @Field("battery") String battery,
            @Field("tests") String tests
            );

    @DELETE("handphone/delete/{id}")
    Call<UpdateHPResponse> deleteHP(
            @Header("Authorization") String token,
            @Path("id") String id
    );

    @Multipart
    @POST("createHP")
    Call<UpdateHPResponse> addHP(
            @Header("Authorization") String token,
            @Part MultipartBody.Part phone_photo,
            @Part("name") RequestBody name,
            @Part("network")RequestBody network,
            @Part("launch")RequestBody launch,
            @Part("body")RequestBody body,
            @Part("display")RequestBody display,
            @Part("platform")RequestBody platform,
            @Part("memory")RequestBody memory,
            @Part("maincam")RequestBody maincam,
            @Part("selfcam")RequestBody selfcam,
            @Part("sound")RequestBody sound,
            @Part("comms")RequestBody comms,
            @Part("features")RequestBody features,
            @Part("battery")RequestBody battery,
            @Part("tests")RequestBody tests
    );
}
