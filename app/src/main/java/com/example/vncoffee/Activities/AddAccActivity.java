package com.example.vncoffee.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vncoffee.PasswordUtils;
import com.google.android.material.textfield.TextInputLayout;
import com.example.vncoffee.DAO.TaikhoanDAO;
import com.example.vncoffee.DTO.TaikhoanDTO;
import com.example.vncoffee.R;

import java.util.Calendar;
import java.util.regex.Pattern;

public class AddAccActivity extends AppCompatActivity implements View.OnClickListener{
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=\\S+$)" +            // no white spaces
                    ".{6,}" +                // at least 6 characters
                    "$");

    ImageView IMG_addacc_back;
    TextView TXT_addacc_title, TXT_addacc_Permission;
    TextInputLayout TXTL_addacc_HoVaTen, TXTL_addacc_TenDN, TXTL_addacc_Email, TXTL_addacc_SDT, TXTL_addacc_MatKhau;
    RadioGroup RG_addacc_GioiTinh,rg_addacc_Quyen;
    RadioButton RD_addacc_Nam,RD_addacc_Nu,rd_addacc_QuanLy,rd_addacc_NhanVien;
    DatePicker DT_addacc_NgaySinh;
    Button BTN_addacc_ThemTK;
    TaikhoanDAO taikhoanDAO;
    String hoTen,tenDN,eMail,sDT,matKhau,gioiTinh,ngaySinh;
    int matk = 0,quyen = 0;
    long ktra = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addacc_layout);

        //region Lấy đối tượng trong view
        TXT_addacc_title = (TextView)findViewById(R.id.txt_addacc_title);
        TXT_addacc_Permission= (TextView)findViewById(R.id.txt_addacc_Permission);
        IMG_addacc_back = (ImageView)findViewById(R.id.img_addacc_back);
        TXTL_addacc_HoVaTen = (TextInputLayout)findViewById(R.id.txtl_addacc_HoVaTen);
        TXTL_addacc_TenDN = (TextInputLayout)findViewById(R.id.txtl_addacc_TenDN);
        TXTL_addacc_Email = (TextInputLayout)findViewById(R.id.txtl_addacc_Email);
        TXTL_addacc_SDT = (TextInputLayout)findViewById(R.id.txtl_addacc_SDT);
        TXTL_addacc_MatKhau = (TextInputLayout)findViewById(R.id.txtl_addacc_MatKhau);
        RG_addacc_GioiTinh = (RadioGroup)findViewById(R.id.rg_addacc_GioiTinh);
        rg_addacc_Quyen = (RadioGroup)findViewById(R.id.rg_addacc_Quyen);
        RD_addacc_Nam = (RadioButton)findViewById(R.id.rd_addacc_Nam);
        RD_addacc_Nu = (RadioButton)findViewById(R.id.rd_addacc_Nu);
        rd_addacc_QuanLy = (RadioButton)findViewById(R.id.rd_addacc_QuanLy);
        rd_addacc_NhanVien = (RadioButton)findViewById(R.id.rd_addacc_NhanVien);
        DT_addacc_NgaySinh = (DatePicker)findViewById(R.id.dt_addacc_NgaySinh);
        BTN_addacc_ThemTK = (Button)findViewById(R.id.btn_addacc_ThemTK);

        //endregion

        taikhoanDAO = new TaikhoanDAO(this);

        //region Hiển thị trang sửa nếu được chọn từ context menu sửa
        matk = getIntent().getIntExtra("matk",0);   //lấy matk từ display acc
        if(matk != 0){
            TXT_addacc_title.setText("Sửa tài khoản");
            TaikhoanDTO taikhoanDTO = taikhoanDAO.LayTKTheoMa(matk);

            //Hiển thị thông tin từ csdl
            TXTL_addacc_HoVaTen.getEditText().setText(taikhoanDTO.getHOTEN());
            TXTL_addacc_TenDN.getEditText().setText(taikhoanDTO.getTENDN());
            TXTL_addacc_Email.getEditText().setText(taikhoanDTO.getEMAIL());
            TXTL_addacc_SDT.getEditText().setText(taikhoanDTO.getSDT());

            String decryptedPassword = PasswordUtils.decrypt(taikhoanDTO.getMATKHAU());
            TXTL_addacc_MatKhau.getEditText().setText(decryptedPassword);

            //Hiển thị giới tính từ csdl
            String gioitinh = taikhoanDTO.getGIOITINH();
            if(gioitinh.equals("Nam")){
                RD_addacc_Nam.setChecked(true);
            }else{
                RD_addacc_Nu.setChecked(true);
            }

            if (matk == 1){
                TXT_addacc_Permission.setVisibility(View.INVISIBLE);
                rd_addacc_NhanVien.setVisibility(View.INVISIBLE);
                rd_addacc_QuanLy.setVisibility(View.INVISIBLE);
                rd_addacc_QuanLy.setChecked(true);
            }
            else if(taikhoanDTO.getMAQUYEN() == 1){
                rd_addacc_QuanLy.setChecked(true);
            }else {
                rd_addacc_NhanVien.setChecked(true);
            }

            //Hiển thị ngày sinh từ csdl
            String date = taikhoanDTO.getNGAYSINH();
            String[] items = date.split("/");
            int day = Integer.parseInt(items[0]);
            int month = Integer.parseInt(items[1]) - 1;
            int year = Integer.parseInt(items[2]);
            DT_addacc_NgaySinh.updateDate(year,month,day);
            BTN_addacc_ThemTK.setText("Sửa tài khoản");
        }
        //endregion

        BTN_addacc_ThemTK.setOnClickListener(this);
        IMG_addacc_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        String chucnang;
        switch (id){
            case R.id.btn_addacc_ThemTK:
                if( !validateAge() | !validateEmail() | !validateFullName() | !validateGender() | !validatePassWord() |
                        !validatePermission() | !validatePhone() | !validateUserName()){
                    return;
                }
                //Lấy dữ liệu từ view
                hoTen = TXTL_addacc_HoVaTen.getEditText().getText().toString();
                tenDN = TXTL_addacc_TenDN.getEditText().getText().toString();
                eMail = TXTL_addacc_Email.getEditText().getText().toString();
                sDT = TXTL_addacc_SDT.getEditText().getText().toString();
                matKhau = TXTL_addacc_MatKhau.getEditText().getText().toString();

                String encryptedPassword = PasswordUtils.encrypt(matKhau);

                switch (RG_addacc_GioiTinh.getCheckedRadioButtonId()){
                    case R.id.rd_addacc_Nam: gioiTinh = "Nam"; break;
                    case R.id.rd_addacc_Nu: gioiTinh = "Nữ"; break;
                }
                switch (rg_addacc_Quyen.getCheckedRadioButtonId()){
                    case R.id.rd_addacc_QuanLy: quyen = 1; break;
                    case R.id.rd_addacc_NhanVien: quyen = 2; break;
                }

                ngaySinh = DT_addacc_NgaySinh.getDayOfMonth() + "/" + (DT_addacc_NgaySinh.getMonth() + 1)
                        +"/"+DT_addacc_NgaySinh.getYear();

                //truyền dữ liệu vào obj taikhoanDTO
                TaikhoanDTO taikhoanDTO = new TaikhoanDTO();
                taikhoanDTO.setHOTEN(hoTen);
                taikhoanDTO.setTENDN(tenDN);
                taikhoanDTO.setEMAIL(eMail);
                taikhoanDTO.setSDT(sDT);
                taikhoanDTO.setMATKHAU(encryptedPassword);
                taikhoanDTO.setGIOITINH(gioiTinh);
                taikhoanDTO.setNGAYSINH(ngaySinh);
                taikhoanDTO.setMAQUYEN(quyen);

                if(matk != 0){
                    ktra = taikhoanDAO.SuaTaiKhoan(taikhoanDTO,matk);
                    chucnang = "sua";
                }else {
                    ktra = taikhoanDAO.ThemTaiKhoan(taikhoanDTO);
                    chucnang = "themtk";
                }
                //Thêm, sửa tk dựa theo obj taikhoanDTO
                Intent intent = new Intent();
                intent.putExtra("ketquaktra",ktra);
                intent.putExtra("chucnang",chucnang);
                setResult(RESULT_OK,intent);
                finish();
                break;

            case R.id.img_addacc_back:
                finish();
                break;
        }
    }

    //Kiểm tra nhập
    private boolean validateFullName(){
        String val = TXTL_addacc_HoVaTen.getEditText().getText().toString().trim();

        if(val.isEmpty()){
            TXTL_addacc_HoVaTen.setError(getResources().getString(R.string.not_empty));
            return false;
        }else {
            TXTL_addacc_HoVaTen.setError(null);
            TXTL_addacc_HoVaTen.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateUserName(){
        String val = TXTL_addacc_TenDN.getEditText().getText().toString().trim();
        String checkspaces = "\\A\\w{1,50}\\z";
        TaikhoanDAO taikhoanDAO = new TaikhoanDAO(getApplicationContext());
        TaikhoanDTO taikhoanDTO = taikhoanDAO.LayTKTheoMa(matk);

        if(val.isEmpty()){
            TXTL_addacc_TenDN.setError(getResources().getString(R.string.not_empty));
            return false;
        }else if(val.length()>50){
            TXTL_addacc_TenDN.setError("Phải nhỏ hơn 50 ký tự");
            return false;
        }else if(!val.matches(checkspaces)){
            TXTL_addacc_TenDN.setError("Không được cách chữ!");
            return false;
        } else if (taikhoanDAO != null && !val.equals(taikhoanDTO.getTENDN()) && taikhoanDAO.KiemTraTenDN(val)) {
            TXTL_addacc_TenDN.setError("Tên đăng nhập đã tồn tại!");
            return false;
        } else {
            TXTL_addacc_TenDN.setError(null);
            TXTL_addacc_TenDN.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail(){
        String val = TXTL_addacc_Email.getEditText().getText().toString().trim();
        String checkspaces = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";
        TaikhoanDAO taikhoanDAO = new TaikhoanDAO(getApplicationContext());
        TaikhoanDTO taikhoanDTO = taikhoanDAO.LayTKTheoMa(matk);

        if(val.isEmpty()){
            TXTL_addacc_Email.setError(getResources().getString(R.string.not_empty));
            return false;
        }else if(!val.matches(checkspaces)){
            TXTL_addacc_Email.setError("Email không hợp lệ!");
            return false;
        } else if (taikhoanDAO != null && !val.equals(taikhoanDTO.getEMAIL()) && taikhoanDAO.KiemTraEmail(val)) {
            TXTL_addacc_Email.setError("Email đã tồn tại!");
            return false;
        } else {
            TXTL_addacc_Email.setError(null);
            TXTL_addacc_Email.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePhone(){
        String val = TXTL_addacc_SDT.getEditText().getText().toString().trim();
        TaikhoanDAO taikhoanDAO = new TaikhoanDAO(getApplicationContext());
        TaikhoanDTO taikhoanDTO = taikhoanDAO.LayTKTheoMa(matk);

        if(val.isEmpty()){
            TXTL_addacc_SDT.setError(getResources().getString(R.string.not_empty));
            return false;
        }else if(val.length() != 10){
            TXTL_addacc_SDT.setError("Số điện thoại không hợp lệ!");
            return false;
        } else if (taikhoanDAO != null && !val.equals(taikhoanDTO.getSDT()) && taikhoanDAO.KiemTraSĐT(val)) {
            TXTL_addacc_SDT.setError("Số điện thoại đã tồn tại!");
            return false;
        } else {
            TXTL_addacc_SDT.setError(null);
            TXTL_addacc_SDT.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassWord(){
        String val = TXTL_addacc_MatKhau.getEditText().getText().toString().trim();

        if(val.isEmpty()){
            TXTL_addacc_MatKhau.setError(getResources().getString(R.string.not_empty));
            return false;
        }else if(!PASSWORD_PATTERN.matcher(val).matches()){
            TXTL_addacc_MatKhau.setError("Mật khẩu ít nhất 6 ký tự!");
            return false;
        }
        else {
            TXTL_addacc_MatKhau.setError(null);
            TXTL_addacc_MatKhau.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateGender(){
        if(RG_addacc_GioiTinh.getCheckedRadioButtonId() == -1){
            Toast.makeText(this,"Hãy chọn giới tính",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }

    private boolean validatePermission(){
        if(rg_addacc_Quyen.getCheckedRadioButtonId() == -1){
            Toast.makeText(this,"Hãy chọn quyền",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }

    private boolean validateAge(){
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int userAge = DT_addacc_NgaySinh.getYear();
        int isAgeValid = currentYear - userAge;

        if(isAgeValid < 18){
            Toast.makeText(this,"Bạn không đủ tuổi đăng ký!",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }
}
