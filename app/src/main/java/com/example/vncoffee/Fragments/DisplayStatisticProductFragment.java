package com.example.vncoffee.Fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.vncoffee.Activities.HomeActivity;
import com.example.vncoffee.Adapter.AdapterDisplayProductStatistic;
import com.example.vncoffee.Adapter.AdapterDisplayStatistic;
import com.example.vncoffee.DAO.DonhangDAO;
import com.example.vncoffee.DAO.ThucdonDAO;
import com.example.vncoffee.DTO.ThucdonDTO;
import com.example.vncoffee.R;

import java.util.Calendar;
import java.util.List;

public class DisplayStatisticProductFragment extends Fragment{
    ListView lvProductStatistics;
    Button btnPickDateProduct;
    List<ThucdonDTO> thucdonDTO;
    ThucdonDAO thucdonDAO;
    DonhangDAO donhangDAO;
    AdapterDisplayProductStatistic adapterDisplayProductStatistic;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.displaystatistic_product_layout, container, false);
        setHasOptionsMenu(true);


        lvProductStatistics = view.findViewById(R.id.lvProductStatistics);
        btnPickDateProduct = view.findViewById(R.id.btnPickDateProduct);
        donhangDAO = new DonhangDAO(getActivity());
        thucdonDAO = new ThucdonDAO(getActivity());

        thucdonDTO = thucdonDAO.LayTongSoLuongDatCuaMon();
        adapterDisplayProductStatistic = new AdapterDisplayProductStatistic(getActivity(), R.layout.custom_layout_productstatistic, thucdonDTO);
        lvProductStatistics.setAdapter(adapterDisplayProductStatistic);
        adapterDisplayProductStatistic.notifyDataSetChanged();

        btnPickDateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
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
        thucdonDTO = thucdonDAO.LayTongSoLuongDatCuaMonTheoNgay(ngaythang);
        if (thucdonDTO.isEmpty()) {
            Toast.makeText(getActivity(), "Không có thống kê cho ngày đã chọn", Toast.LENGTH_SHORT).show();
        }
        adapterDisplayProductStatistic = new AdapterDisplayProductStatistic(getActivity(), R.layout.custom_layout_productstatistic, thucdonDTO);
        lvProductStatistics.setAdapter(adapterDisplayProductStatistic);
        adapterDisplayProductStatistic.notifyDataSetChanged();
    }
}
