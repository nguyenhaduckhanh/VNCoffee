package com.example.vncoffee.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.vncoffee.Database.CreateDatabase;
import com.example.vncoffee.R;
import com.google.android.material.textfield.TextInputLayout;
import com.example.vncoffee.DAO.TaikhoanDAO;
import com.example.vncoffee.PasswordUtils;

public class LoginActivity extends AppCompatActivity {
    Button BTN_login_DangNhap;
    TextInputLayout TXTL_login_TenDN, TXTL_login_MatKhau;
    TaikhoanDAO taikhoanDAO;
    private View view;
    public static final String BUNDLE = "BUNDLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
//        CreateDatabase dbHelper = new CreateDatabase(getApplicationContext());
//        dbHelper.deleteDatabase();

        //thuộc tính view
        TXTL_login_TenDN = (TextInputLayout)findViewById(R.id.txtl_login_TenDN);
        TXTL_login_MatKhau = (TextInputLayout)findViewById(R.id.txtl_login_MatKhau);
        BTN_login_DangNhap = (Button)findViewById(R.id.btn_login_DangNhap);

        //khởi tạo kết nối csdl
        taikhoanDAO = new TaikhoanDAO(this);

        BTN_login_DangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateUserName() | !validatePassWord()){
                    return;
                }

                String tenDN = TXTL_login_TenDN.getEditText().getText().toString();
                String matKhau = TXTL_login_MatKhau.getEditText().getText().toString();
                String encryptmatkhau = PasswordUtils.encrypt(matKhau);

                int ktra = taikhoanDAO.KiemTraDN(tenDN,encryptmatkhau);
                int maquyen = taikhoanDAO.LayQuyenTK(ktra);
                if(ktra != 0){
                    // lưu mã quyền vào shareprefer
                    SharedPreferences sharedPreferences = getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor =sharedPreferences.edit();
                    editor.putInt("maquyen",maquyen);
                    editor.commit();

                    //gửi dữ liệu user qua trang chủ
                    Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                    intent.putExtra("tendn",TXTL_login_TenDN.getEditText().getText().toString());
                    intent.putExtra("matkhau",TXTL_login_MatKhau.getEditText().getText().toString());
                    intent.putExtra("matk",ktra);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(),"Tên đăng nhập, mật khẩu không đúng",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //kiểm tra nhập
    private boolean validateUserName(){
        String val = TXTL_login_TenDN.getEditText().getText().toString().trim();

        if(val.isEmpty()){
            TXTL_login_TenDN.setError(getResources().getString(R.string.not_empty));
            return false;
        }else {
            TXTL_login_TenDN.setError(null);
            TXTL_login_TenDN.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassWord(){
        String val = TXTL_login_MatKhau.getEditText().getText().toString().trim();

        if(val.isEmpty()){
            TXTL_login_MatKhau.setError(getResources().getString(R.string.not_empty));
            return false;
        }else{
            TXTL_login_MatKhau.setError(null);
            TXTL_login_MatKhau.setErrorEnabled(false);
            return true;
        }
    }
}