package com.example.vncoffee.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.example.vncoffee.R;
import com.example.vncoffee.DAO.BananDAO;

public class AddTableActivity extends AppCompatActivity {
    TextInputLayout TXTL_addtable_tenban;
    Button BTN_addtable_TaoBan;
    BananDAO banAnDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addtable_layout);

        //region Lấy đối tượng trong view
        TXTL_addtable_tenban = (TextInputLayout)findViewById(R.id.txtl_addtable_tenban);
        BTN_addtable_TaoBan = (Button)findViewById(R.id.btn_addtable_TaoBan);

        banAnDAO = new BananDAO(this);
        BTN_addtable_TaoBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateName()){
                    return;
                }
                String sTenBanAn = TXTL_addtable_tenban.getEditText().getText().toString();
                boolean ktra = banAnDAO.ThemBanAn(sTenBanAn);
                //trả về result cho displaytable
                Intent intent = new Intent();
                intent.putExtra("ketquathem",ktra);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    //validate dữ liệu
    private boolean validateName(){
        String val = TXTL_addtable_tenban.getEditText().getText().toString().trim();
        BananDAO bananDAO = new BananDAO(getApplicationContext());
        if(val.isEmpty()){
            TXTL_addtable_tenban.setError(getResources().getString(R.string.not_empty));
            return false;
        } else if (bananDAO.KiemTraTenBan(val)) {
            TXTL_addtable_tenban.setError("Tên bàn đã tồn tại!");
            return false;
        } else {
            TXTL_addtable_tenban.setError(null);
            TXTL_addtable_tenban.setErrorEnabled(false);
            return true;
        }
    }
}
