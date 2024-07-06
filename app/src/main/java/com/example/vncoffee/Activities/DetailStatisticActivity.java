package com.example.vncoffee.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vncoffee.Adapter.AdapterDisplayPayment;
import com.example.vncoffee.DAO.BananDAO;
import com.example.vncoffee.DAO.TaikhoanDAO;
import com.example.vncoffee.DAO.ThanhtoanDAO;
import com.example.vncoffee.DTO.TaikhoanDTO;
import com.example.vncoffee.DTO.ThanhtoanDTO;
import com.example.vncoffee.R;

import java.util.List;

public class DetailStatisticActivity extends AppCompatActivity{
    ImageView img_detailstatistic_backbtn;
    TextView txt_detailstatistic_MaDon,txt_detailstatistic_NgayTao,txt_detailstatistic_TenBan
            ,txt_detailstatistic_TenTK,txt_detailstatistic_TongTien;
    GridView gvDetailStatistic;
    int madon, matk, maban;
    String ngaytao, tongtien;
    TaikhoanDAO taikhoanDAO;
    BananDAO banAnDAO;
    List<ThanhtoanDTO> thanhToanDTOList;
    ThanhtoanDAO thanhToanDAO;
    AdapterDisplayPayment adapterDisplayPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailstatistic_layout);

        //Lấy thông tin từ display statistic
        Intent intent = getIntent();
        madon = intent.getIntExtra("madon",0);
        matk = intent.getIntExtra("matk",0);
        maban = intent.getIntExtra("maban",0);
        ngaytao = intent.getStringExtra("ngaytao");
        tongtien = intent.getStringExtra("tongtien");

        //region Thuộc tính bên view
        img_detailstatistic_backbtn = (ImageView)findViewById(R.id.img_detailstatistic_backbtn);
        txt_detailstatistic_MaDon = (TextView)findViewById(R.id.txt_detailstatistic_MaDon);
        txt_detailstatistic_NgayTao = (TextView)findViewById(R.id.txt_detailstatistic_NgayTao);
        txt_detailstatistic_TenBan = (TextView)findViewById(R.id.txt_detailstatistic_TenBan);
        txt_detailstatistic_TenTK = (TextView)findViewById(R.id.txt_detailstatistic_TenTK);
        txt_detailstatistic_TongTien = (TextView)findViewById(R.id.txt_detailstatistic_TongTien);
        gvDetailStatistic = (GridView)findViewById(R.id.gvDetailStatistic);
        //endregion

        //khởi tạo lớp dao mở kết nối csdl
        taikhoanDAO = new TaikhoanDAO(this);
        banAnDAO = new BananDAO(this);
        thanhToanDAO = new ThanhtoanDAO(this);

        //chỉ hiển thị nếu lấy đc mã đơn đc chọn
        if (madon !=0){
            txt_detailstatistic_MaDon.setText("Mã đơn: "+madon);
            txt_detailstatistic_NgayTao.setText(ngaytao);
            txt_detailstatistic_TongTien.setText(tongtien+" VNĐ");

            TaikhoanDTO taikhoanDTO = taikhoanDAO.LayTKTheoMa(matk);
            txt_detailstatistic_TenTK.setText(taikhoanDTO.getHOTEN());
            txt_detailstatistic_TenBan.setText(banAnDAO.LayTenBanTheoMa(maban));

            HienThiDSCTDH();
        }

        img_detailstatistic_backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void HienThiDSCTDH(){
        thanhToanDTOList = thanhToanDAO.LayDSMonTheoMaDon(madon);
        adapterDisplayPayment = new AdapterDisplayPayment(this,R.layout.custom_layout_payment,thanhToanDTOList);
        gvDetailStatistic.setAdapter(adapterDisplayPayment);
        adapterDisplayPayment.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        HienThiDSCTDH();
    }
}
