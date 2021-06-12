package com.faizurazadri.pustakaku.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.faizurazadri.pustakaku.R;
import com.faizurazadri.pustakaku.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityHomeBinding homeBinding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(homeBinding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), this);

        homeBinding.viewPagerPustakaKu.setAdapter(sectionsPagerAdapter);
        homeBinding.tabsPustaku.setupWithViewPager(homeBinding.viewPagerPustakaKu);

        if (getSupportActionBar()!=null){
            getSupportActionBar().setElevation(0);
        }


    }
}