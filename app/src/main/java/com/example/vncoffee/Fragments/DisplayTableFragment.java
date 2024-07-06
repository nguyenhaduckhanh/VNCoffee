package com.example.vncoffee.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.vncoffee.Activities.AddTableActivity;
import com.example.vncoffee.Activities.EditTableActivity;
import com.example.vncoffee.Activities.HomeActivity;
import com.example.vncoffee.Adapter.AdapterDisplayTable;
import com.example.vncoffee.DAO.BananDAO;
import com.example.vncoffee.DTO.BananDTO;
import com.example.vncoffee.R;

import java.util.List;

public class DisplayTableFragment extends Fragment {
    GridView GVDisplayTable;
    List<BananDTO> banAnDTOList;
    BananDAO banAnDAO;
    AdapterDisplayTable adapterDisplayTable;
    int maquyen = 0;
    SharedPreferences sharedPreferences;

    //Dùng activity result để nhận data gửi từ activity addtable
    ActivityResultLauncher<Intent> resultLauncherAdd = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent intent = result.getData();
                        boolean ktra = intent.getBooleanExtra("ketquathem",false);
                        if(ktra){
                            HienThiDSBan();
                            Toast.makeText(getActivity(),"Thêm thành công",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getActivity(),"Thêm thất bại",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

    ActivityResultLauncher<Intent> resultLauncherEdit = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent intent = result.getData();
                        boolean ktra = intent.getBooleanExtra("ketquasua",false);
                        if(ktra){
                            HienThiDSBan();
                            Toast.makeText(getActivity(),getResources().getString(R.string.edit_sucessful),Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getActivity(),getResources().getString(R.string.edit_failed),Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.displaytable_layout,container,false);
        setHasOptionsMenu(true);
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Quản lý bàn</font>"));
        ((HomeActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //lấy share prefer
        sharedPreferences = getActivity().getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
        maquyen = sharedPreferences.getInt("maquyen",0);

        GVDisplayTable = (GridView)view.findViewById(R.id.gvDisplayTable);
        banAnDAO = new BananDAO(getActivity());

        HienThiDSBan();

        registerForContextMenu(GVDisplayTable);
        return view;
    }

    //tạo ra context menu khi longclick
    @Override
    public void onCreateContextMenu(ContextMenu menu,View v,ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.edit_context_menu,menu);
    }

    //Xử lí cho từng trường hợp trong contextmenu
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int vitri = menuInfo.position;
        int maban = banAnDTOList.get(vitri).getMABAN();
        switch(id){
            case R.id.itEdit:
                if(maquyen == 1) {
                    Intent intent = new Intent(getActivity(), EditTableActivity.class);
                    intent.putExtra("maban", maban);
                    resultLauncherEdit.launch(intent);
                    break;
                }
                else {
                    Toast.makeText(getActivity().getApplicationContext(),"Bạn không có quyền chỉnh sửa",Toast.LENGTH_SHORT).show();
                }
            case R.id.itDelete:
                if(maquyen == 1) {
                    boolean ktraxoa = banAnDAO.XoaBanTheoMa(maban);
                    if (ktraxoa) {
                        HienThiDSBan();
                        Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.delete_sucessful), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.delete_failed), Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                else {
                    Toast.makeText(getActivity().getApplicationContext(),"Bạn không có quyền chỉnh sửa",Toast.LENGTH_SHORT).show();
                }
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem itAddTable = menu.add(1,R.id.itAddTable,1,"Thêm bàn");
        itAddTable.setIcon(R.drawable.ic_baseline_add_24);
        itAddTable.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id){
            case R.id.itAddTable:
                if(maquyen ==1) {
                    Intent iAddTable = new Intent(getActivity(), AddTableActivity.class);
                    resultLauncherAdd.launch(iAddTable);
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

    @Override
    public void onResume() {
        super.onResume();
        adapterDisplayTable.notifyDataSetChanged();
    }

    private void HienThiDSBan(){
        banAnDTOList = banAnDAO.LayTatCaBanAn();
        adapterDisplayTable = new AdapterDisplayTable(getActivity(),R.layout.custom_layout_displaytable,banAnDTOList);
        GVDisplayTable.setAdapter(adapterDisplayTable);
        adapterDisplayTable.notifyDataSetChanged();
    }
}
