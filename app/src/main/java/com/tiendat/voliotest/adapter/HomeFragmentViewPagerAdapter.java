package com.tiendat.voliotest.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.tiendat.voliotest.fragment.ForYouFragment;
import com.tiendat.voliotest.fragment.OtherFragment;

public class HomeFragmentViewPagerAdapter extends FragmentStateAdapter {


    public HomeFragmentViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
            case 2:
            case 3:
            case 4:
                fragment = new OtherFragment();
                break;
            case 1:
                fragment = new ForYouFragment();
                break;
            default:
                break;
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
