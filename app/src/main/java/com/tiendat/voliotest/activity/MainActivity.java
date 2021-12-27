package com.tiendat.voliotest.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.tiendat.voliotest.R;
import com.tiendat.voliotest.databinding.ActivityMainBinding;
import com.tiendat.voliotest.fragment.HomeFragment;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this , R.layout.activity_main);

        setupTabLayout();

        setupFragment();

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void setupTabLayout(){

        binding.mainFragmentTabLayout
                .addTab(binding.mainFragmentTabLayout.newTab()
                        .setText("Home")
                        .setIcon(AppCompatResources.getDrawable(getApplicationContext() , R.drawable.ic_baseline_home_24)));
        binding.mainFragmentTabLayout
                .addTab(binding.mainFragmentTabLayout.newTab()
                        .setText("Account")
                        .setIcon(AppCompatResources.getDrawable(getApplicationContext() , R.drawable.ic_baseline_person_outline_24)));

        for (int i = 0 ; i < binding.mainFragmentTabLayout.getTabCount() ; i ++){
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) Objects.requireNonNull(binding.mainFragmentTabLayout.getTabAt(i)).view.getChildAt(0).getLayoutParams();
            params.bottomMargin = 0;
            Objects.requireNonNull(binding.mainFragmentTabLayout.getTabAt(i)).view.getChildAt(0).setLayoutParams(params);
        }
    }

    private void setupFragment(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(binding.fragmentHolder.getId() , new HomeFragment());
        transaction.addToBackStack("HomeFragment");
        transaction.commit();
    }

}