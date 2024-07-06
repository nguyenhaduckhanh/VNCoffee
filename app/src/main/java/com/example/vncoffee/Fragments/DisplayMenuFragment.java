package com.example.vncoffee.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import android.content.SharedPreferences;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.vncoffee.Activities.AddMenuActivity;
import com.example.vncoffee.Activities.AmountMenuActivity;
import com.example.vncoffee.Activities.HomeActivity;
import com.example.vncoffee.Adapter.AdapterDisplayMenu;
import com.example.vncoffee.DAO.ThucdonDAO;
import com.example.vncoffee.DTO.ThucdonDTO;
import com.example.vncoffee.R;

import java.util.List;

public class DisplayMenuFragment extends Fragment {

    int maloai, maban, soluong;
    String tenloai;
    GridView gvDisplayMenu;
    ThucdonDAO thucdonDAO;
    List<ThucdonDTO> thucdonDTOList;
    AdapterDisplayMenu adapterDisplayMenu;
    int maquyen=0;
    SharedPreferences sharedPreferences;

    ActivityResultLauncher<Intent> resultLauncherMenu = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent intent = result.getData();
                        boolean ktra = intent.getBooleanExtra("ktra",false);
                        String chucnang = intent.getStringExtra("chucnang");
                        if(chucnang.equals("themmon"))
                        {
                            if(ktra){
                                HienThiDSMon();
                                Toast.makeText(getActivity(),"Thêm thành công",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getActivity(),"Thêm thất bại",Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            if(ktra){
                                HienThiDSMon();
                                Toast.makeText(getActivity(),"Sửa thành công",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getActivity(),"Sửa thất bại",Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                }
            });


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.displaymenu_layout,container,false);
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Quản lý món</font>"));
        thucdonDAO = new ThucdonDAO(getActivity());
        ((HomeActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //lấy share prefer
        sharedPreferences = getActivity().getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
        maquyen = sharedPreferences.getInt("maquyen",0);

        gvDisplayMenu = (GridView)view.findViewById(R.id.gvDisplayMenu);

        Bundle bundle = getArguments();
        if(bundle !=null){
            maloai = bundle.getInt("maloai");
            tenloai = bundle.getString("tenloai");
            maban = bundle.getInt("maban");
            HienThiDSMon();

            gvDisplayMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //nếu lấy đc mã bàn mới mở
                    soluong = thucdonDTOList.get(position).getSOLUONG();
                    if(maban != 0){
                        if(soluong > 0){
                            Intent iAmount = new Intent(getActivity(), AmountMenuActivity.class);
                            iAmount.putExtra("maban",maban);
                            iAmount.putExtra("mamon",thucdonDTOList.get(position).getMAMON());
                            startActivity(iAmount);
                        }else {
                            Toast.makeText(getActivity(),"Món đã hết, không thể thêm", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
        setHasOptionsMenu(true);
        registerForContextMenu(gvDisplayMenu);
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN){
                    getParentFragmentManager().popBackStack("hienthiloai", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
                return false;
            }
        });

        return view;
    }

    //tạo 1 menu context show lựa chọn
    @Override
    public void onCreateContextMenu(ContextMenu menu,View v,ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.edit_context_menu,menu);
    }

    //Tạo phần sửa và xóa trong menu context
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int vitri = menuInfo.position;
        int mamon = thucdonDTOList.get(vitri).getMAMON();

        switch (id){
            case R.id.itEdit:
                if (maquyen ==1) {
                    Intent iEdit = new Intent(getActivity(), AddMenuActivity.class);
                    iEdit.putExtra("mamon", mamon);
                    iEdit.putExtra("maLoai", maloai);
                    iEdit.putExtra("tenLoai", tenloai);
                    resultLauncherMenu.launch(iEdit);
                    break;
                }
                else {
                    Toast.makeText(getActivity().getApplicationContext(),"Bạn không có quyền chỉnh sửa",Toast.LENGTH_SHORT).show();
                }
            case R.id.itDelete:
                if (maquyen == 1) {
                    boolean ktra = thucdonDAO.XoaMon(mamon);
                    if (ktra) {
                        HienThiDSMon();
                        Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.delete_sucessful)
                                , Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.delete_failed)
                                , Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                else {
                    Toast.makeText(getActivity().getApplicationContext(),"Bạn không có quyền chỉnh sửa",Toast.LENGTH_SHORT).show();
                }
        }
        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem itAddMenu = menu.add(1,R.id.itAddMenu,1,R.string.addMenu);
        itAddMenu.setIcon(R.drawable.ic_baseline_add_24);
        itAddMenu.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.itAddMenu:
                if (maquyen == 1) {
                    Intent intent = new Intent(getActivity(), AddMenuActivity.class);
                    intent.putExtra("maLoai", maloai);
                    intent.putExtra("tenLoai", tenloai);
                    resultLauncherMenu.launch(intent);
                }
                else {
                    Toast.makeText(getActivity().getApplicationContext(),"Bạn không có quyền chỉnh sửa",Toast.LENGTH_SHORT).show();
                }
                break;
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void HienThiDSMon(){
        thucdonDTOList = thucdonDAO.LayDSMonTheoLoai(maloai);
        adapterDisplayMenu = new AdapterDisplayMenu(getActivity(),R.layout.custom_layout_displaymenu,thucdonDTOList);
        gvDisplayMenu.setAdapter(adapterDisplayMenu);
        adapterDisplayMenu.notifyDataSetChanged();
    }

}
