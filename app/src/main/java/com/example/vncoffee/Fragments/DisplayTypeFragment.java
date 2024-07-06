package com.example.vncoffee.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.ContextMenu;
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

import com.example.vncoffee.Activities.AddTypeActivity;
import com.example.vncoffee.Activities.HomeActivity;
import com.example.vncoffee.Adapter.AdapterDisplayType;
import com.example.vncoffee.DAO.LoaimonDAO;
import com.example.vncoffee.DTO.LoaimonDTO;
import com.example.vncoffee.R;

import java.util.List;
public class DisplayTypeFragment extends Fragment{
    GridView gvType;
    List<LoaimonDTO> loaiMonDTOList;
    LoaimonDAO loaiMonDAO;
    AdapterDisplayType adapter;
    FragmentManager fragmentManager;
    int maban, maquyen = 0;
    SharedPreferences sharedPreferences;

    ActivityResultLauncher<Intent> resultLauncherType = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent intent = result.getData();
                        boolean ktra = intent.getBooleanExtra("ktra",false);
                        String chucnang = intent.getStringExtra("chucnang");
                        if(chucnang.equals("themloai"))
                        {
                            if(ktra){
                                HienThiDSLoai();
                                Toast.makeText(getActivity(),"Thêm thành công",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getActivity(),"Thêm thất bại",Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            if(ktra){
                                HienThiDSLoai();
                                Toast.makeText(getActivity(),"Sủa thành công",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getActivity(),"sửa thất bại",Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                }
            });
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.displaytype_layout,container,false);
        setHasOptionsMenu(true);
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Quản lý loại món</font>"));
        ((HomeActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //lấy share prefer
        sharedPreferences = getActivity().getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
        maquyen = sharedPreferences.getInt("maquyen",0);

        gvType = (GridView)view.findViewById(R.id.gvType);

        fragmentManager = getActivity().getSupportFragmentManager();

        loaiMonDAO = new LoaimonDAO(getActivity());
        HienThiDSLoai();

        Bundle bDataType = getArguments();
        if(bDataType != null){
            maban = bDataType.getInt("maban");
        }

        gvType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int maloai = loaiMonDTOList.get(position).getMALOAI();
                String tenloai = loaiMonDTOList.get(position).getTENLOAI();
                DisplayMenuFragment displayMenuFragment = new DisplayMenuFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("maloai",maloai);
                bundle.putString("tenloai",tenloai);
                bundle.putInt("maban",maban);
                displayMenuFragment.setArguments(bundle);

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.contentView,displayMenuFragment).addToBackStack("hienthiloai");
                transaction.commit();
            }
        });

        registerForContextMenu(gvType);

        return view;
    }

    //hiển thị contextmenu
    @Override
    public void onCreateContextMenu(ContextMenu menu,View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.edit_context_menu,menu);
    }

    //xử lí context menu
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int vitri = menuInfo.position;
        int maloai = loaiMonDTOList.get(vitri).getMALOAI();

        switch (id){
            case R.id.itEdit:
                if (maquyen == 1) {
                    Intent iEdit = new Intent(getActivity(), AddTypeActivity.class);
                    iEdit.putExtra("maloai", maloai);
                    resultLauncherType.launch(iEdit);
                    break;
                }
                else {
                    Toast.makeText(getActivity().getApplicationContext(),"Bạn không có quyền chỉnh sửa",Toast.LENGTH_SHORT).show();
                }
            case R.id.itDelete:
                if (maquyen ==1) {
                    boolean ktra = loaiMonDAO.XoaLoaiMon(maloai);
                    if (ktra) {
                        HienThiDSLoai();
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

    //khởi tạo nút thêm loại
    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem itAddType = menu.add(1,R.id.itAddType,1,R.string.addType);
        itAddType.setIcon(R.drawable.ic_baseline_add_24);
        itAddType.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

    }

    //xử lý nút thêm loại
    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.itAddType:
                if (maquyen == 1) {
                    Intent intent = new Intent(getActivity(), AddTypeActivity.class);
                    resultLauncherType.launch(intent);
                }
                else {
                    Toast.makeText(getActivity().getApplicationContext(),"Bạn không có quyền chỉnh sửa",Toast.LENGTH_SHORT).show();
                }
                break;
            case android.R.id.home:
                FragmentTransaction tranDisplayType = getActivity().getSupportFragmentManager().beginTransaction();
                tranDisplayType.replace(R.id.contentView,new DisplayHomeFragment());
                tranDisplayType.addToBackStack(null);
                tranDisplayType.commit();
                ((HomeActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        return super.onOptionsItemSelected(item);
    }
    //hiển thị dữ liệu trên gridview
    private void HienThiDSLoai(){
        loaiMonDTOList = loaiMonDAO.LayDSLoaiMon();
        adapter = new AdapterDisplayType(getActivity(),R.layout.custom_layout_displaytype,loaiMonDTOList);
        gvType.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
