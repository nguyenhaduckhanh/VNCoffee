package com.example.vncoffee.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.example.vncoffee.DAO.TaikhoanDAO;
import com.example.vncoffee.DTO.TaikhoanDTO;
import com.example.vncoffee.R;
import com.example.vncoffee.PasswordUtils;

import java.util.regex.Pattern;

public class PasswordActivity extends AppCompatActivity implements View.OnClickListener{
    TextInputLayout TXTL_UPDATEPASS_MKcu, TXTL_UPDATEPASS_MKmoi, TXTL_UPDATEPASS_xacnhanMK;
    ImageView IMG_UPDATEPASS_BACK;
    private Button BTN_UPDATEPASS_XACNHAN;
    int matk ;
    TaikhoanDAO taikhoanDAO;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=\\S+$)" +            // no white spaces
                    ".{6,}" +                // at least 4 characters
                    "$");
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_layout);

        //lấy đối tượng từ view
        IMG_UPDATEPASS_BACK = (ImageView)findViewById(R.id.img_updatepass_backbtn);
        TXTL_UPDATEPASS_MKcu = (TextInputLayout)findViewById(R.id.txtl_updatepass_MKcu);
        TXTL_UPDATEPASS_MKmoi = (TextInputLayout)findViewById(R.id.txtl_updatepass_MKmoi);
        TXTL_UPDATEPASS_xacnhanMK = (TextInputLayout)findViewById(R.id.txtl_updatepass_xacnhanMK);
        BTN_UPDATEPASS_XACNHAN = (Button)findViewById(R.id.btn_updatepass_xacnhan);

        //khởi tạo kết nối csdl
        taikhoanDAO = new TaikhoanDAO(this);

        //lấy thông tin tài khoản
        Intent intent = getIntent();
        matk = intent.getIntExtra("matk",0);

        BTN_UPDATEPASS_XACNHAN.setOnClickListener(this);
        IMG_UPDATEPASS_BACK.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v){
        int id = v.getId();
        switch (id){
            case R.id.img_updatepass_backbtn:
                finish();
                break;
            case R.id.btn_updatepass_xacnhan:
                if(!validatePassWord() | !validateConfirmPassWord() | !validateUpdatePassWord()){
                    return;
                }
                String mkmoi = TXTL_UPDATEPASS_MKmoi.getEditText().getText().toString().trim();
                String encryptmkmoi = PasswordUtils.encrypt(mkmoi);

                // Cập nhật mật khẩu mới
                TaikhoanDTO taikhoanDTO = new TaikhoanDTO();
                taikhoanDTO.setMATK(matk);
                taikhoanDTO.setMATKHAU(encryptmkmoi);

                boolean ktra = taikhoanDAO.CapNhapMatKhau(taikhoanDTO,matk);
                if (ktra) {
                    Toast.makeText(PasswordActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(PasswordActivity.this, "Đổi mật khẩu không thành công", Toast.LENGTH_SHORT).show();
                }
        }
    }

    private boolean validatePassWord(){
        String mkcu = TXTL_UPDATEPASS_MKcu.getEditText().getText().toString().trim();
        TaikhoanDAO taikhoanDAO = new TaikhoanDAO(getApplicationContext());
        String mkhientai = taikhoanDAO.LayMatKhau(matk);

        String encryptmkcu = PasswordUtils.encrypt(mkcu);

        if(mkcu.isEmpty()){
            TXTL_UPDATEPASS_MKcu.setError(getResources().getString(R.string.not_empty));
            return false;
        }else if(!encryptmkcu.equals(mkhientai)){
            TXTL_UPDATEPASS_MKcu.setError("Mật khẩu không đúng");
            return false;
        }
        else {
            TXTL_UPDATEPASS_MKcu.setError(null);
            TXTL_UPDATEPASS_MKcu.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateUpdatePassWord(){
        String mkmoi = TXTL_UPDATEPASS_MKmoi.getEditText().getText().toString().trim();

        if (mkmoi.isEmpty()){
            TXTL_UPDATEPASS_MKmoi.setError(getResources().getString(R.string.not_empty));
            return false;
        }else if(!PASSWORD_PATTERN.matcher(mkmoi).matches()){
            TXTL_UPDATEPASS_MKmoi.setError("Mật khẩu ít nhất 6 ký tự!");
            return false;
        }else {
            TXTL_UPDATEPASS_MKmoi.setError(null);
            TXTL_UPDATEPASS_MKmoi.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateConfirmPassWord(){
        String mkmoi = TXTL_UPDATEPASS_MKmoi.getEditText().getText().toString().trim();
        String xacnhanmk = TXTL_UPDATEPASS_xacnhanMK.getEditText().getText().toString().trim();

        if(xacnhanmk.isEmpty()){
            TXTL_UPDATEPASS_xacnhanMK.setError(getResources().getString(R.string.not_empty));
            return false;
        }else if(!xacnhanmk.equals(mkmoi)){
            TXTL_UPDATEPASS_xacnhanMK.setError("Xác nhận mật khẩu không khớp");
            return false;
        }
        else {
            TXTL_UPDATEPASS_xacnhanMK.setError(null);
            TXTL_UPDATEPASS_xacnhanMK.setErrorEnabled(false);
            return true;
        }
    }
}
