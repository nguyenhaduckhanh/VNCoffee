package com.example.vncoffee.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.vncoffee.Activities.DetailStatisticActivity;
import com.example.vncoffee.Activities.HomeActivity;
import com.example.vncoffee.Adapter.AdapterDisplayStatistic;
import com.example.vncoffee.DAO.DonhangDAO;
import com.example.vncoffee.DTO.DonhangDTO;
import com.example.vncoffee.R;

import java.util.List;

public class DisplayStatisticUnpaidFragment extends Fragment{
    ListView lvUnpaidOrders;
    List<DonhangDTO> unpaidOrders;
    DonhangDAO donhangDAO;
    AdapterDisplayStatistic adapterDisplayStatistic;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.displaystatistic_unpaid_layout, container, false);
//        ((HomeActivity) getActivity()).getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Đơn chưa thanh toán</font>"));
        setHasOptionsMenu(true);
//        ((HomeActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lvUnpaidOrders = view.findViewById(R.id.lvUnpaidOrders);
        donhangDAO = new DonhangDAO(getActivity());

        unpaidOrders = donhangDAO.LayDSDonChuaThanhToan();
        adapterDisplayStatistic = new AdapterDisplayStatistic(getActivity(), R.layout.custom_layout_displaystatistic, unpaidOrders);
        lvUnpaidOrders.setAdapter(adapterDisplayStatistic);
        adapterDisplayStatistic.notifyDataSetChanged();

        lvUnpaidOrders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DonhangDTO donhangDTO = unpaidOrders.get(position);
                Intent intent = new Intent(getActivity(), DetailStatisticActivity.class);
                intent.putExtra("madon", donhangDTO.getMADON());
                intent.putExtra("matk", donhangDTO.getMATK());
                intent.putExtra("maban", donhangDTO.getMABAN());
                intent.putExtra("ngaytao", donhangDTO.getNGAYTAO());
                intent.putExtra("tongtien", donhangDTO.getTONGTIEN());
                startActivity(intent);
            }
        });

        return view;
    }
}
