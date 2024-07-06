// PaymentActivity.java
package com.example.vncoffee.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vncoffee.Adapter.AdapterDisplayPaymentBill;
import com.example.vncoffee.DAO.BananDAO;
import com.example.vncoffee.DAO.ChitietdonhangDAO;
import com.example.vncoffee.DAO.DonhangDAO;
import com.example.vncoffee.DAO.ThanhtoanDAO;
import com.example.vncoffee.DTO.ThanhtoanDTO;
import com.example.vncoffee.DAO.ThucdonDAO;
import com.example.vncoffee.R;

import java.util.List;

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener, AdapterDisplayPaymentBill.OnDeleteItemClickListener{
    ImageView IMG_payment_backbtn;
    TextView TXT_payment_TenBan, TXT_payment_NgayTao, TXT_payment_TongTien;
    Button BTN_payment_ThanhToan;
    GridView gvDisplayPayment;
    DonhangDAO donhangDAO;
    BananDAO banAnDAO;
    ThanhtoanDAO thanhToanDAO;
    ThucdonDAO thucdonDAO;
    ChitietdonhangDAO chitietdonhangDAO;
    List<ThanhtoanDTO> thanhToanDTOS;
    AdapterDisplayPaymentBill adapterDisplayPaymentBill;
    long tongtien = 0;
    int maban, madon;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_layout);


        //region thuộc tính view
        gvDisplayPayment= findViewById(R.id.gvDisplayPayment);
        IMG_payment_backbtn = findViewById(R.id.img_payment_backbtn);
        TXT_payment_TenBan = findViewById(R.id.txt_payment_TenBan);
        TXT_payment_NgayTao = findViewById(R.id.txt_payment_NgayTao);
        TXT_payment_TongTien = findViewById(R.id.txt_payment_TongTien);
        BTN_payment_ThanhToan = findViewById(R.id.btn_payment_ThanhToan);
        //endregion

        //khởi tạo kết nối csdl
        donhangDAO = new DonhangDAO(this);
        thanhToanDAO = new ThanhtoanDAO(this);
        banAnDAO = new BananDAO(this);
        chitietdonhangDAO = new ChitietdonhangDAO(this);
        thucdonDAO = new ThucdonDAO(this);


        fragmentManager = getSupportFragmentManager();

        //lấy data từ mã bàn đc chọn
        Intent intent = getIntent();
        maban = intent.getIntExtra("maban",0);
        String tenban = intent.getStringExtra("tenban");
        String ngaytao = intent.getStringExtra("ngaytao");

        TXT_payment_TenBan.setText(tenban);
        TXT_payment_NgayTao.setText(ngaytao);

        //ktra mã bàn tồn tại thì hiển thị
        if(maban != 0 ){
            HienThiThanhToan();
            CapNhatTongTien();
        }

        BTN_payment_ThanhToan.setOnClickListener(this);
        IMG_payment_backbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btn_payment_ThanhToan:
                boolean ktraban = banAnDAO.CapNhatTinhTrangBan(maban,"false");
                boolean ktradondat = donhangDAO.UpdateTThaiDonTheoMaBan(maban,"true");
                if(ktraban && ktradondat){
                    HienThiThanhToan();
                    TXT_payment_TongTien.setText("0 VNĐ");
                    Toast.makeText(getApplicationContext(),"Thanh toán thành công!",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Lỗi thanh toán!",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.img_payment_backbtn:
                finish();
                break;
        }
    }

    //hiển thị data lên gridview
    private void HienThiThanhToan() {
        madon = (int) donhangDAO.LayMaDonTheoMaBan(maban, "false");
        thanhToanDTOS = thanhToanDAO.LayDSMonTheoMaDon(madon);
        adapterDisplayPaymentBill = new AdapterDisplayPaymentBill(this, R.layout.custom_layout_paymentbill, thanhToanDTOS);
        adapterDisplayPaymentBill.setOnDeleteItemClickListener((AdapterDisplayPaymentBill.OnDeleteItemClickListener) this);
        gvDisplayPayment.setAdapter(adapterDisplayPaymentBill);
        adapterDisplayPaymentBill.notifyDataSetChanged();
        CapNhatTongTien();
    }

    // Cập nhật tổng tiền
    public void CapNhatTongTien() {
        madon = (int) donhangDAO.LayMaDonTheoMaBan(maban, "false");
        thanhToanDTOS = thanhToanDAO.LayDSMonTheoMaDon(madon);
        tongtien = 0;
        for (ThanhtoanDTO thanhToanDTO : thanhToanDTOS) {
            int soluong = thanhToanDTO.getSOLUONGDAT();
            int giatien = thanhToanDTO.getGIATIEN();
            tongtien += (soluong * giatien);
        }
        TXT_payment_TongTien.setText(String.valueOf(tongtien) + " VNĐ");
        donhangDAO.CapNhatTongTien(madon,tongtien);
    }

    public void onDeleteItem(int madon, int mamon) {
        int sluongxoa = thanhToanDAO.LaySoLuongMonTrongDon(madon, mamon);
        int sluonghientai = thucdonDAO.LaySoLuongMon(mamon);
        int sluongmoi = sluonghientai + sluongxoa;

        boolean isDeleted = thanhToanDAO.XoaMon(mamon, madon);
        if (isDeleted) {
            boolean isUpdated = thucdonDAO.CapNhatSoLuongMon(mamon, sluongmoi);
            if (isUpdated) {
                CapNhatTongTien();
            } else {
                Toast.makeText(this, "Cập nhật số lượng món thất bại", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Cập nhật lại dữ liệu khi quay lại activity
        if (maban != 0) {
            HienThiThanhToan();
            CapNhatTongTien();
        }
    }
}
