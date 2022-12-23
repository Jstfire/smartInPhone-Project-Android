package com.example.smartinphoneprojectandroid.utils;

import static com.example.smartinphoneprojectandroid.utils.Credentials.FIX_URL;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private  static Retrofit retrofit;

    //Define the Base Url

    //create the Retrofit Instance
    public static Retrofit myRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(FIX_URL)
                    //Add the converter
                    //you can use multiple converters in a single Retrofit instance
                    .addConverterFactory(GsonConverterFactory.create())
                    //Build the Retrofit instance
                    .build();

        }
        return retrofit;
    }
}
