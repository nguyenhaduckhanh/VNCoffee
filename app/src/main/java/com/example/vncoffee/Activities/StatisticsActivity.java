package com.example.vncoffee.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.vncoffee.Fragments.DisplayHomeFragment;
import com.example.vncoffee.Fragments.DisplayStatisticFragment;
import com.example.vncoffee.Fragments.DisplayStatisticProductFragment;
import com.example.vncoffee.Fragments.DisplayStatisticUnpaidFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


import com.example.vncoffee.Adapter.AdapterStatisticPager;
import com.example.vncoffee.Activities.HomeActivity;
import com.example.vncoffee.R;


public class StatisticsActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private AdapterStatisticPager adapterStatisticPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>Thống kê</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        adapterStatisticPager = new AdapterStatisticPager(getSupportFragmentManager());

        // Add fragments
        adapterStatisticPager.addFragment(new DisplayStatisticUnpaidFragment(), "Đơn chưa thanh toán");
        adapterStatisticPager.addFragment(new DisplayStatisticFragment(), "Tất cả đơn");
        adapterStatisticPager.addFragment(new DisplayStatisticProductFragment(), "Thống kê sản phẩm");

        viewPager.setAdapter(adapterStatisticPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
