package com.example.vncoffee.Fragments;

import android.app.Activity;
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

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.example.vncoffee.Activities.AddAccActivity;
import com.example.vncoffee.Activities.HomeActivity;
import com.example.vncoffee.Adapter.AdapterDisplayAcc;
import com.example.vncoffee.DAO.TaikhoanDAO;
import com.example.vncoffee.DTO.TaikhoanDTO;
import com.example.vncoffee.R;

import java.util.List;

public class DisplayAccFragment extends Fragment {
    GridView gvAcc;
    TaikhoanDAO taikhoanDAO;
    List<TaikhoanDTO> taikhoanDTOS;
    AdapterDisplayAcc adapterDisplayAcc;

    ActivityResultLauncher<Intent> resultLauncherAdd = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent intent = result.getData();
                        long ktra = intent.getLongExtra("ketquaktra",0);
                        String chucnang = intent.getStringExtra("chucnang");
                        if(chucnang.equals("themtk"))
                        {
                            if(ktra != 0){
                                HienThiDSTK();
                                Toast.makeText(getActivity(),"Thêm thành công",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getActivity(),"Thêm thất bại",Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            if(ktra != 0){
                                HienThiDSTK();
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
        View view = inflater.inflate(R.layout.displayacc_layout,container,false);
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Quản lý tài khoản</font>"));
        setHasOptionsMenu(true);


        gvAcc = (GridView)view.findViewById(R.id.gvAcc) ;

        taikhoanDAO = new TaikhoanDAO(getActivity());
        HienThiDSTK();

        registerForContextMenu(gvAcc);

        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu,View v,ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.edit_context_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int vitri = menuInfo.position;
        int matk = taikhoanDTOS.get(vitri).getMATK();

        switch (id){
            case R.id.itEdit:
                Intent iEdit = new Intent(getActivity(),AddAccActivity.class);
                iEdit.putExtra("matk",matk);
                resultLauncherAdd.launch(iEdit);
                break;

            case R.id.itDelete:
                boolean ktra = taikhoanDAO.XoaTK(matk);
                if(ktra){
                    HienThiDSTK();
                    Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.delete_sucessful)
                            ,Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.delete_failed)
                            ,Toast.LENGTH_SHORT).show();
                }
                break;
        }

        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem itAddAcc = menu.add(1,R.id.itAddAcc,1,"Thêm tài khoản");
        itAddAcc.setIcon(R.drawable.ic_baseline_add_24);
        itAddAcc.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.itAddAcc:
                Intent iDangky = new Intent(getActivity(), AddAccActivity.class);
                resultLauncherAdd.launch(iDangky);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void HienThiDSTK(){
        taikhoanDTOS = taikhoanDAO.LayDSTK();
        adapterDisplayAcc = new AdapterDisplayAcc(getActivity(),R.layout.custom_layout_displayacc,taikhoanDTOS);
        gvAcc.setAdapter(adapterDisplayAcc);
        adapterDisplayAcc.notifyDataSetChanged();
    }
}
