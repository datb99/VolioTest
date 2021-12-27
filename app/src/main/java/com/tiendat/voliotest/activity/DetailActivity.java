package com.tiendat.voliotest.activity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;


import android.os.Bundle;



import com.tiendat.voliotest.R;
import com.tiendat.voliotest.adapter.DetailAdapter;
import com.tiendat.voliotest.api.ApiService;
import com.tiendat.voliotest.api.DetailContent;
import com.tiendat.voliotest.api.DetailData;
import com.tiendat.voliotest.api.Section;
import com.tiendat.voliotest.databinding.ActivityDetailBinding;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailActivity extends AppCompatActivity {

    ActivityDetailBinding binding;
    DetailData data;
    DetailAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        binding = DataBindingUtil.setContentView(this , R.layout.activity_detail);

        getData();

        binding.buttonBack.setOnClickListener(v -> onBackPressed());

    }

    private void getData(){
        int cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(getApplicationContext().getCacheDir(), cacheSize);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .build();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/Akaizz/static/master/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        retrofit.create(ApiService.class).getDetailData().enqueue(new Callback<DetailData>() {
            @Override
            public void onResponse(@NonNull Call<DetailData> call, @NonNull Response<DetailData> response) {
                if (response.body() != null){
                    data = response.body();
                    setupDetail();
                }
            }

            @Override
            public void onFailure(@NonNull Call<DetailData> call, @NonNull Throwable t) {

            }
        });
    }

    private void setupDetail(){
        DetailContent detailContent = new DetailContent();
        detailContent.setTitle(data.getTitle());
        detailContent.setDescription(data.getDescription());
        detailContent.setDatePublish(data.getPublishDate());
        Section section = new Section();
        section.setType(4);
        section.setContent(detailContent);
        data.getSections().add(0 , section);
        adapter = new DetailAdapter(getApplicationContext() , data , binding.recycleView.getWidth() , this);
        binding.recycleView.setAdapter(adapter);
        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));
    }
}