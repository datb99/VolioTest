package com.tiendat.voliotest.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("newsfeed.json")
    Call<AllItems> getAllItem();

    @GET("detail.json")
    Call<DetailData> getDetailData();
}
