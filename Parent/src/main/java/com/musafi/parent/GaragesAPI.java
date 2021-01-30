package com.musafi.parent;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GaragesAPI {

    @GET("WypPzJCt")
    Call<MyGarage> getAllGarages();


}
