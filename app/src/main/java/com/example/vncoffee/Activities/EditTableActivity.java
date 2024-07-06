package com.example.vncoffee.Activities;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.example.vncoffee.R;
import com.example.vncoffee.DAO.BananDAO;
import com.example.vncoffee.DTO.BananDTO;

public class EditTableActivity extends AppCompatActivity{
    TextInputLayout TXTL_edittable_tenban;
    Button BTN_edittable_SuaBan;
    BananDAO banAnDAO;
    BananDTO bananDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edittable_layout);

        //thuộc tính view
        TXTL_edittable_tenban = (TextInputLayout)findViewById(R.id.txtl_edittable_tenban);
        BTN_edittable_SuaBan = (Button)findViewById(R.id.btn_edittable_SuaBan);

        //khởi tạo dao mở kết nối csdl
        banAnDAO = new BananDAO(this);
        int maban = getIntent().getIntExtra("maban",0); //lấy maban từ bàn đc chọn

        //hiển thị thông tin từ csdl
        if(maban != 0){
            BananDTO bananDTO = banAnDAO.TraVeTenBan(maban);
            TXTL_edittable_tenban.getEditText().setText(bananDTO.getTENBAN());
        }

        BTN_edittable_SuaBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateName()){
                    return;
                }

                String tenban = TXTL_edittable_tenban.getEditText().getText().toString();
                boolean ktra = banAnDAO.CapNhatTenBan(maban,tenban);
                Intent intent = new Intent();
                intent.putExtra("ketquasua",ktra);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    //validate dữ liệu
    private boolean validateName(){
        String val = TXTL_edittable_tenban.getEditText().getText().toString().trim();
        BananDAO bananDAO =new BananDAO(getApplicationContext());
        if(val.isEmpty()){
            TXTL_edittable_tenban.setError(getResources().getString(R.string.not_empty));
            return false;
        } else if (bananDAO.KiemTraTenBan(val)) {
            TXTL_edittable_tenban.setError("Tên bàn đã tồn tại!");
            return false;
        } else {
            TXTL_edittable_tenban.setError(null);
            TXTL_edittable_tenban.setErrorEnabled(false);
            return true;
        }
    }
}
