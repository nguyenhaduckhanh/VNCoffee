package com.example.vncoffee.Fragments;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import com.example.vncoffee.Activities.HomeActivity;
import com.example.vncoffee.Activities.LoginActivity;
import com.example.vncoffee.Activities.PasswordActivity;
import com.example.vncoffee.DAO.TaikhoanDAO;
import com.example.vncoffee.DAO.QuyenDAO;
import com.example.vncoffee.DTO.TaikhoanDTO;
import com.example.vncoffee.DTO.QuyenDTO;
import com.example.vncoffee.R;

public class DisplayInformationFragment extends Fragment {
    TextView i4_Hoten, i4_Birth, i4_Gender, i4_Email, i4_Phonenum, i4_Logout, i4_ChucVu, i4_Password;
    TaikhoanDAO taikhoanDAO;
    int matk;
    QuyenDAO quyenDAO;
    TaikhoanDTO taikhoanDTO;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.displayinformation_layout,container,false);
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Thông tin</font>"));
        setHasOptionsMenu(false);

        i4_Hoten = view.findViewById(R.id.i4_hoten);
        i4_Birth = view.findViewById(R.id.i4_birth);
        i4_Gender = view.findViewById(R.id.i4_gender);
        i4_Email = view.findViewById(R.id.i4_mail);
        i4_Phonenum = view.findViewById(R.id.i4_phonenum);
        i4_Logout = view.findViewById(R.id.i4_logout);
        i4_ChucVu = view.findViewById(R.id.i4_chucvu);
        i4_Password = view.findViewById(R.id.i4_password);

        taikhoanDAO = new TaikhoanDAO(getActivity());
        quyenDAO = new QuyenDAO(getActivity());
        Intent intent = getActivity().getIntent();
        matk = intent.getIntExtra("matk",0);
        TaikhoanDTO TaikhoanDTO = taikhoanDAO.LayTKTheoMa(matk);
        int maQuyen = taikhoanDAO.LayQuyenTK(matk);
        String tenQuyen = quyenDAO.LayTenQuyenTheoMa(maQuyen);
        i4_Hoten.setText(TaikhoanDTO.getHOTEN());
        i4_Birth.setText(TaikhoanDTO.getNGAYSINH());
        i4_Gender.setText(TaikhoanDTO.getGIOITINH());
        i4_ChucVu.setText(tenQuyen);
        i4_Email.setText(TaikhoanDTO.getEMAIL());
        i4_Phonenum.setText(TaikhoanDTO.getSDT());

        i4_Password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity().getApplicationContext(), PasswordActivity.class);
                intent1.putExtra("matk",matk);
                startActivity(intent1);
            }
        });


        i4_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });



        return  view;
    }
}
