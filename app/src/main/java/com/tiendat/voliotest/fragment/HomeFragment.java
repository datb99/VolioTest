package com.tiendat.voliotest.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.tiendat.voliotest.R;
import com.tiendat.voliotest.adapter.HomeFragmentViewPagerAdapter;
import com.tiendat.voliotest.databinding.FragmentHomeBinding;


public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    HomeFragmentViewPagerAdapter adapter;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_home,
                container,
                false);
        binding.setLifecycleOwner(getActivity());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addFragmentTab();
    }

    private void addFragmentTab(){
        adapter = new HomeFragmentViewPagerAdapter(getActivity());
        binding.homeFragmentViewPager.setAdapter(adapter);
        new TabLayoutMediator(binding.homeFragmentTabLayout, binding.homeFragmentViewPager, (tab ,position ) -> {
            switch (position){
                case 0:
                    tab.setText("Theo dõi");
                    break;
                case 1:
                    tab.setText("Cho bạn");
                    break;
                case 2:
                    tab.setText("Bóng đá");
                    break;
                case 3:
                    tab.setText("Công nghệ");
                    break;
                case 4:
                    tab.setText("Đời sống");
                    break;
            }
        }).attach();

    }
}