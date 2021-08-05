package com.cyberburyatenterprise.pepegacafe.model.WebService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static Retrofit retrofit = null;

    private static String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";

    public static PepegaService getService(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit.create(PepegaService.class);
    }
}
