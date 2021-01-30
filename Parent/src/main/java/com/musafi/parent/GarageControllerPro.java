package com.musafi.parent;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GarageControllerPro {

    static final String BASE_URL = "https://pastebin.com/raw/";

    private CallBack_Garages callBack_garages;

    Callback<MyGarage> garagesArrayCallBack = new Callback<MyGarage>() {
        @Override
        public void onResponse(Call<MyGarage> call, Response<MyGarage> response) {
            if (response.isSuccessful()) {
                MyGarage garages = response.body();

                if (callBack_garages != null) {
                    callBack_garages.garages(garages);
                }
            } else {
                System.out.println(response.errorBody());
            }
        }

        @Override
        public void onFailure(Call<MyGarage> call, Throwable t) {
            t.printStackTrace();
        }
    };

    public void fetchAllMovies(CallBack_Garages callBack_garages) {
        this.callBack_garages = callBack_garages;
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        GaragesAPI garagesAPI = retrofit.create(GaragesAPI.class);

        Call<MyGarage> call = garagesAPI.getAllGarages();
        call.enqueue(garagesArrayCallBack);
    }

    public interface CallBack_Garages {
        void garages(MyGarage garages);
    }


}

