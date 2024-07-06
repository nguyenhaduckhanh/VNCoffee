package com.example.vncoffee.Fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.vncoffee.Activities.AddTypeActivity;
import com.example.vncoffee.Activities.DetailStatisticActivity;
import com.example.vncoffee.Activities.HomeActivity;
import com.example.vncoffee.Adapter.AdapterDisplayStatistic;
import com.example.vncoffee.DAO.DonhangDAO;
import com.example.vncoffee.DTO.DonhangDTO;
import com.example.vncoffee.R;

import java.util.Calendar;
import java.util.List;

public class DisplayStatisticFragment extends Fragment {

    ListView lvStatistic;
    List<DonhangDTO> donhangDTOS;
    DonhangDAO donhangDAO;
    AdapterDisplayStatistic adapterDisplayStatistic;
    Button btnPickDate;
    int madon, matk, maban;
    String ngaytao, tongtien;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.displaystatistic_layout,container,false);
        setHasOptionsMenu(true);

        lvStatistic = (ListView)view.findViewById(R.id.lvStatistic);
        btnPickDate = view.findViewById(R.id.btnPickDate);
        donhangDAO = new DonhangDAO(getActivity());

        donhangDTOS = donhangDAO.LayDSDonHang();
        adapterDisplayStatistic = new AdapterDisplayStatistic(getActivity(),R.layout.custom_layout_displaystatistic,donhangDTOS);
        lvStatistic.setAdapter(adapterDisplayStatistic);
        adapterDisplayStatistic.notifyDataSetChanged();

        btnPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });


        lvStatistic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                madon = donhangDTOS.get(position).getMADON();
                matk = donhangDTOS.get(position).getMATK();
                maban = donhangDTOS.get(position).getMABAN();
                ngaytao = donhangDTOS.get(position).getNGAYTAO();
                tongtien = donhangDTOS.get(position).getTONGTIEN();

                Intent intent = new Intent(getActivity(), DetailStatisticActivity.class);
                intent.putExtra("madon",madon);
                intent.putExtra("matk",matk);
                intent.putExtra("maban",maban);
                intent.putExtra("ngaytao",ngaytao);
                intent.putExtra("tongtien",tongtien);
                startActivity(intent);
            }
        });

        return view;
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // Chuyển đổi ngày đã chọn thành định dạng dd-MM-yyyy
                String selectedDate = String.format("%02d-%02d-%04d", dayOfMonth, month + 1, year);
                filterOrdersByDate(selectedDate);
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    private void filterOrdersByDate(String ngaythang) {
        donhangDTOS = donhangDAO.LayDSDonTheoNgay(ngaythang);
        if (donhangDTOS.isEmpty()) {
            Toast.makeText(getActivity(), "Không có đơn hàng cho ngày đã chọn", Toast.LENGTH_SHORT).show();
        }
        adapterDisplayStatistic = new AdapterDisplayStatistic(getActivity(), R.layout.custom_layout_displaystatistic, donhangDTOS);
        lvStatistic.setAdapter(adapterDisplayStatistic);
        adapterDisplayStatistic.notifyDataSetChanged();
    }
}

