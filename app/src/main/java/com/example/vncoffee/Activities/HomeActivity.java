package com.example.vncoffee.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.example.vncoffee.DAO.TaikhoanDAO;
import com.example.vncoffee.DTO.ThanhtoanDTO;
import com.example.vncoffee.Fragments.DisplayHomeFragment;
import com.example.vncoffee.Fragments.DisplayAccFragment;
import com.example.vncoffee.Fragments.DisplayInformationFragment;
import com.example.vncoffee.R;

import java.util.List;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    FragmentManager fragmentManager;
    int maquyen = 0, matk;
    SharedPreferences sharedPreferences;
    BottomNavigationView bot_nav;
    List<ThanhtoanDTO> thanhToanDTOList;
    TaikhoanDAO taikhoanDAO;


    private BottomNavigationView.OnNavigationItemSelectedListener
            mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.nav_home:
                    //hiển thị tương ứng trên navigation
                    FragmentTransaction tranDisplayHome = fragmentManager.beginTransaction();
                    DisplayHomeFragment displayHomeFragment = new DisplayHomeFragment();
                    tranDisplayHome.replace(R.id.contentView,displayHomeFragment);
                    tranDisplayHome.commit();
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);

                    return true;


                case R.id.nav_acc:
                    if(maquyen == 1){
                        //hiển thị tương ứng trên navigation
                        FragmentTransaction tranDisplayAcc = fragmentManager.beginTransaction();
                        DisplayAccFragment displayAccFragment = new DisplayAccFragment();
                        tranDisplayAcc.replace(R.id.contentView,displayAccFragment);
                        tranDisplayAcc.commit();
                        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

                    }else {
                        Toast.makeText(getApplicationContext(),"Bạn không có quyền truy cập",Toast.LENGTH_SHORT).show();
                    }

                    return true;

                case R.id.nav_information:
                    //hiển thị tương ứng trên navigation
                    FragmentTransaction tranDisplayStatistic = fragmentManager.beginTransaction();
                    DisplayInformationFragment displayInformationFragment = new DisplayInformationFragment();
                    tranDisplayStatistic.replace(R.id.contentView, displayInformationFragment);
                    tranDisplayStatistic.commit();
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);

                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_nav);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //region thuộc tính bên view
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); //tạo toolbar

        //lấy file share prefer
        sharedPreferences = getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
        maquyen = sharedPreferences.getInt("maquyen",0);



        //hiện thị fragment home mặc định
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction tranDisplayHome = fragmentManager.beginTransaction();
        DisplayHomeFragment displayHomeFragment = new DisplayHomeFragment();
        tranDisplayHome.replace(R.id.contentView,displayHomeFragment);
        tranDisplayHome.commit();



    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        return false;
    }

}