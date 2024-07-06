package com.example.vncoffee.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.example.vncoffee.DAO.ChitietdonhangDAO;
import com.example.vncoffee.DAO.DonhangDAO;
import com.example.vncoffee.DTO.ChitietdonhangDTO;
import com.example.vncoffee.DTO.ThucdonDTO;
import com.example.vncoffee.DAO.ThucdonDAO;
import com.example.vncoffee.R;

public class AmountMenuActivity extends AppCompatActivity {
    TextInputLayout TXTL_amountmenu_SoLuong;
    Button BTN_amountmenu_DongY;
    int maban, mamon;
    DonhangDAO donhangDAO;
    ChitietdonhangDAO chitietdonhangDAO;
    ThucdonDAO thucdonDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.amount_menu_layout);

        //Lấy đối tượng view
        TXTL_amountmenu_SoLuong = (TextInputLayout) findViewById(R.id.txtl_amountmenu_SoLuong);
        BTN_amountmenu_DongY = (Button) findViewById(R.id.btn_amountmenu_DongY);

        //khởi tạo kết nối csdl
        donhangDAO = new DonhangDAO(this);
        chitietdonhangDAO = new ChitietdonhangDAO(this);
        thucdonDAO = new ThucdonDAO(this);

        //Lấy thông tin từ bàn được chọn
        Intent intent = getIntent();
        maban = intent.getIntExtra("maban", 0);
        mamon = intent.getIntExtra("mamon", 0);

        BTN_amountmenu_DongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateAmount()) {
                    return;
                }

                int madonhang = (int) donhangDAO.LayMaDonTheoMaBan(maban, "false");
                boolean ktra = chitietdonhangDAO.KiemTraMonTonTai(madonhang, mamon);
                int sluongmoi = Integer.parseInt(TXTL_amountmenu_SoLuong.getEditText().getText().toString());

                int giathucdon = thucdonDAO.LayGiaMon(mamon);
                int tongtienmoi = giathucdon * sluongmoi;


                if (ktra) {
                    //update số lượng món đã chọn
                    int sluongcu = chitietdonhangDAO.LaySLMonTheoMaDon(madonhang, mamon);
                    int tongsl = sluongcu + sluongmoi;

                    ChitietdonhangDTO chitietdonhangDTO = new ChitietdonhangDTO();
                    chitietdonhangDTO.setMAMON(mamon);
                    chitietdonhangDTO.setMADON(madonhang);
                    chitietdonhangDTO.setSOLUONGDAT(tongsl);

                    boolean ktracapnhat = chitietdonhangDAO.CapNhatSL(chitietdonhangDTO);
                    if (ktracapnhat) {
                        // Cập nhật tổng tiền
                        int tongtiencu = donhangDAO.LayTongTien(madonhang);
                        int tongtien = tongtiencu + tongtienmoi;
                        donhangDAO.CapNhatTongTien(madonhang, tongtien);

                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.add_sucessful), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.add_failed), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //thêm số lượng món nếu chưa chọn món này
                    int sluong = Integer.parseInt(TXTL_amountmenu_SoLuong.getEditText().getText().toString());
                    ChitietdonhangDTO chitietdonhangDTO = new ChitietdonhangDTO();
                    chitietdonhangDTO.setMAMON(mamon);
                    chitietdonhangDTO.setMADON(madonhang);
                    chitietdonhangDTO.setSOLUONGDAT(sluong);

                    boolean ktracapnhat = chitietdonhangDAO.ThemChiTietDonHang(chitietdonhangDTO);

                    if (ktracapnhat) {
                        // Cập nhật tổng tiền
                        int tongtiencu = donhangDAO.LayTongTien(madonhang);
                        int tongtien = tongtiencu + tongtienmoi;
                        donhangDAO.CapNhatTongTien(madonhang, tongtien);

                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.add_sucessful), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.add_failed), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    //validate số lượng
    private boolean validateAmount() {
        String val = TXTL_amountmenu_SoLuong.getEditText().getText().toString().trim();

        // Kiểm tra nếu giá trị nhập vào là trống
        if (val.isEmpty()) {
            TXTL_amountmenu_SoLuong.setError(getResources().getString(R.string.not_empty));
            return false;
            // Kiểm tra nếu giá trị nhập vào không phải là số hợp lệ
        } else if (!val.matches(("\\d+"))) {
            TXTL_amountmenu_SoLuong.setError("Số lượng không hợp lệ");
            return false;
        } else {
            // Kiểm tra số lượng nhập vào có lớn hơn số lượng hiện có không
            int sluongmoi = Integer.parseInt(val);
            ThucdonDAO thucdonDAO = new ThucdonDAO(getApplicationContext());
            int sluonghientai = thucdonDAO.LaySoLuongMon(mamon);

            if (sluongmoi > sluonghientai) {
                TXTL_amountmenu_SoLuong.setError("Số lượng yêu cầu vượt quá số lượng hiện có");
                return false;
            } else {
                // Cập nhật số lượng trong thucdon
                thucdonDAO.CapNhatSoLuongMon(mamon, sluonghientai - sluongmoi);
            }

            TXTL_amountmenu_SoLuong.setError(null);
            TXTL_amountmenu_SoLuong.setErrorEnabled(false);
            return true;
        }
    }
}
