package com.tiendat.voliotest.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tiendat.voliotest.R;
import com.tiendat.voliotest.adapter.ItemPostAdapter;
import com.tiendat.voliotest.api.AllItems;
import com.tiendat.voliotest.api.ApiService;
import com.tiendat.voliotest.api.Item;
import com.tiendat.voliotest.databinding.FragmentForYouBinding;

import java.util.ArrayList;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ForYouFragment extends Fragment {

    private FragmentForYouBinding binding;
    private ArrayList<Item> items = new ArrayList<>();


    public ForYouFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadData();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_for_you,
                container,
                false);
        binding.setLifecycleOwner(getActivity());
        return binding.getRoot();
    }

    private void loadData(){
        int cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(requireContext().getCacheDir(), cacheSize);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .build();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/Akaizz/static/master/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        retrofit.create(ApiService.class).getAllItem().enqueue(new Callback<AllItems>() {
            @Override
            public void onResponse(@NonNull Call<AllItems> call, @NonNull Response<AllItems> response) {
                if (response.body() != null){
                    items = (ArrayList<Item>)response.body().getItems();
                    setRecycle();
                }
            }

            @Override
            public void onFailure(@NonNull Call<AllItems> call, @NonNull Throwable t) {

            }
        });
    }

    private void setRecycle(){
        ItemPostAdapter adapter = new ItemPostAdapter(getContext(), items, binding.recycleView.getWidth());
        binding.recycleView.setAdapter(adapter);
        binding.recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}