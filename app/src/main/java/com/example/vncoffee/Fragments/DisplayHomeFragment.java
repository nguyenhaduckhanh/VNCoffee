package com.example.vncoffee.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vncoffee.Activities.AddTypeActivity;
import com.example.vncoffee.Activities.HomeActivity;
import com.example.vncoffee.Activities.LoginActivity;
import com.example.vncoffee.Activities.StatisticsActivity;
import com.example.vncoffee.Adapter.AdapterRecycleViewType;
import com.example.vncoffee.Adapter.AdapterRecycleViewStatistic;
import com.example.vncoffee.DAO.DonhangDAO;
import com.example.vncoffee.DAO.LoaimonDAO;
import com.example.vncoffee.DTO.DonhangDTO;
import com.example.vncoffee.DTO.LoaimonDTO;
import com.example.vncoffee.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


public class DisplayHomeFragment extends Fragment implements View.OnClickListener {

    RecyclerView rcv_displayhome_LoaiMon, rcv_displayhome_DonTrongNgay;
    RelativeLayout layout_displayhome_ThongKe,layout_displayhome_XemBan, layout_displayhome_XemMenu;
    TextView txt_displayhome_ViewAllType, txt_displayhome_ViewAllStatistic, txt_displayhome_ThongKe,
            txt_displayhome_XemBan, txt_displayhome_XemMenu;
    LoaimonDAO loaiMonDAO;
    DonhangDAO donhangDAO;
    List<LoaimonDTO> loaiMonDTOList;
    List<DonhangDTO> donhangDTOS;
    AdapterRecycleViewType adapterRecycleViewType;
    AdapterRecycleViewStatistic adapterRecycleViewStatistic;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.displayhome_layout,container,false);
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Danh mục chức năng</font>"));
        setHasOptionsMenu(true);



        //region Lấy dối tượng view
        rcv_displayhome_LoaiMon = (RecyclerView)view.findViewById(R.id.rcv_displayhome_LoaiMon);
        rcv_displayhome_DonTrongNgay = (RecyclerView)view.findViewById(R.id.rcv_displayhome_DonTrongNgay);
        layout_displayhome_ThongKe = (RelativeLayout)view.findViewById(R.id.layout_displayhome_ThongKe);
        layout_displayhome_XemBan = (RelativeLayout)view.findViewById(R.id.layout_displayhome_XemBan);
        layout_displayhome_XemMenu = (RelativeLayout)view.findViewById(R.id.layout_displayhome_Menu);
        txt_displayhome_ThongKe = (TextView) view.findViewById(R.id.txt_displayhome_ThongKe);
        txt_displayhome_XemBan = (TextView) view.findViewById(R.id.txt_displayhome_XemBan);
        txt_displayhome_XemMenu = (TextView) view.findViewById(R.id.txt_displayhome_Menu);
        txt_displayhome_ViewAllType = (TextView) view.findViewById(R.id.txt_displayhome_ViewAllType);
        txt_displayhome_ViewAllStatistic = (TextView) view.findViewById(R.id.txt_displayhome_ViewAllStatistic);
        //endregion

        //khởi tạo kết nối
        loaiMonDAO = new LoaimonDAO(getActivity());
        donhangDAO = new DonhangDAO(getActivity());

        HienThiDSLoai();
        HienThiDonTrongNgay();

        layout_displayhome_ThongKe.setOnClickListener(this);
        layout_displayhome_XemBan.setOnClickListener(this);
        layout_displayhome_XemMenu.setOnClickListener(this);
        txt_displayhome_ThongKe.setOnClickListener(this);
        txt_displayhome_XemBan.setOnClickListener(this);
        txt_displayhome_XemMenu.setOnClickListener(this);
        txt_displayhome_ViewAllType.setOnClickListener(this);
        txt_displayhome_ViewAllStatistic.setOnClickListener(this);

        return view;
    }

    private void HienThiDSLoai(){
        rcv_displayhome_LoaiMon.setHasFixedSize(true);
        rcv_displayhome_LoaiMon.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        loaiMonDTOList = loaiMonDAO.LayDSLoaiMon();
        adapterRecycleViewType = new AdapterRecycleViewType(getActivity(),R.layout.custom_layout_displaytype,loaiMonDTOList);
        rcv_displayhome_LoaiMon.setAdapter(adapterRecycleViewType);
        adapterRecycleViewType.notifyDataSetChanged();
    }

    private void HienThiDonTrongNgay(){
        rcv_displayhome_DonTrongNgay.setHasFixedSize(true);
        rcv_displayhome_DonTrongNgay.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String ngaytao= dateFormat.format(calendar.getTime());

        donhangDTOS = donhangDAO.LayDSDonHangNgay(ngaytao);
        adapterRecycleViewStatistic = new AdapterRecycleViewStatistic(getActivity(),R.layout.custom_layout_displaystatistic,donhangDTOS);
        rcv_displayhome_DonTrongNgay.setAdapter(adapterRecycleViewStatistic);
        adapterRecycleViewType.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){

            case R.id.layout_displayhome_ThongKe:

            case R.id.txt_displayhome_ThongKe:

            case R.id.txt_displayhome_ViewAllStatistic:
//                FragmentTransaction tranDisplayStatistic = getActivity().getSupportFragmentManager().beginTransaction();
//                tranDisplayStatistic.replace(R.id.contentView,new DisplayStatisticFragment());
//                tranDisplayStatistic.addToBackStack(null);
//                tranDisplayStatistic.commit();
                Intent intent = new Intent(getActivity().getApplicationContext(), StatisticsActivity.class);
                startActivity(intent);

                break;
            case R.id.txt_displayhome_XemBan:
            case R.id.layout_displayhome_XemBan:
                FragmentTransaction tranDisplayTable = getActivity().getSupportFragmentManager().beginTransaction();
                tranDisplayTable.replace(R.id.contentView,new DisplayTableFragment());
                tranDisplayTable.addToBackStack(null);
                tranDisplayTable.commit();

                break;

            case R.id.txt_displayhome_Menu:
            case R.id.layout_displayhome_Menu:

            case R.id.txt_displayhome_ViewAllType:
                FragmentTransaction tranDisplayTypelist = getActivity().getSupportFragmentManager().beginTransaction();
                tranDisplayTypelist.replace(R.id.contentView,new DisplayTypeFragment());
                tranDisplayTypelist.addToBackStack(null);
                tranDisplayTypelist.commit();

                break;

        }
    }
}

