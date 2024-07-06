package com.example.vncoffee.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.vncoffee.Activities.HomeActivity;
import com.example.vncoffee.Activities.PaymentActivity;
import com.example.vncoffee.DAO.BananDAO;
import com.example.vncoffee.DAO.DonhangDAO;
import com.example.vncoffee.DTO.BananDTO;
import com.example.vncoffee.DTO.DonhangDTO;
import com.example.vncoffee.Fragments.DisplayTypeFragment;
import com.example.vncoffee.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class AdapterDisplayTable extends BaseAdapter implements View.OnClickListener{

    Context context;
    int layout;
    List<BananDTO> banAnDTOList;
    ViewHolder viewHolder;
    BananDAO banAnDAO;
    DonhangDAO donhangDAO;
    FragmentManager fragmentManager;

    public AdapterDisplayTable(Context context, int layout, List<BananDTO> banAnDTOList){
        this.context = context;
        this.layout = layout;
        this.banAnDTOList = banAnDTOList;
        banAnDAO = new BananDAO(context);
        donhangDAO = new DonhangDAO(context);
        fragmentManager = ((HomeActivity)context).getSupportFragmentManager();
    }

    @Override
    public int getCount() {
        return banAnDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return banAnDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return banAnDTOList.get(position).getMABAN();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewHolder = new ViewHolder();
            view = inflater.inflate(layout,parent,false);

            viewHolder.imgBanAn = (ImageView) view.findViewById(R.id.img_customtable_BanAn);
            viewHolder.imgGoiMon = (ImageView) view.findViewById(R.id.img_customtable_GoiMon);
            viewHolder.imgThanhToan = (ImageView) view.findViewById(R.id.img_customtable_ThanhToan);
            viewHolder.imgAnNut = (ImageView) view.findViewById(R.id.img_customtable_AnNut);
            viewHolder.txtTenBanAn = (TextView)view.findViewById(R.id.txt_customtable_TenBanAn);

            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if(banAnDTOList.get(position).isDUOCCHON()){
            HienThiButton();
        }else {
            AnButton();
        }

        BananDTO banAnDTO = banAnDTOList.get(position);

        String kttinhtrang = banAnDAO.LayTinhTrangBanTheoMa(banAnDTO.getMABAN());
        //đổi hình theo tình trạng
        if(kttinhtrang.equals("true")){
            viewHolder.imgBanAn.setImageResource(R.drawable.ic_baseline_airline_seat_legroom_normal_40);
        }else {
            viewHolder.imgBanAn.setImageResource(R.drawable.ic_baseline_event_seat_40);
        }

        viewHolder.txtTenBanAn.setText(banAnDTO.getTENBAN());
        viewHolder.imgBanAn.setTag(position);

        //sự kiện click
        viewHolder.imgBanAn.setOnClickListener(this);
        viewHolder.imgGoiMon.setOnClickListener(this);
        viewHolder.imgThanhToan.setOnClickListener(this);
        viewHolder.imgAnNut.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        viewHolder = (ViewHolder) ((View) v.getParent()).getTag();

        int vitri1 = (int) viewHolder.imgBanAn.getTag();

        int maban = banAnDTOList.get(vitri1).getMABAN();
        String tenban = banAnDTOList.get(vitri1).getTENBAN();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String ngaytao= dateFormat.format(calendar.getTime());

        switch (id){
            case R.id.img_customtable_BanAn:
                int vitri = (int)v.getTag();
                banAnDTOList.get(vitri).setDUOCCHON(true);
                HienThiButton();
                break;

            case R.id.img_customtable_AnNut:
                AnButton();
                break;

            case R.id.img_customtable_GoiMon:
                Intent getIHome = ((HomeActivity)context).getIntent();
                int matk = getIHome.getIntExtra("matk",0);
                String tinhtrang = banAnDAO.LayTinhTrangBanTheoMa(maban);

                if(tinhtrang.equals("false")){
                    //Thêm bảng gọi món và update tình trạng bàn
                    DonhangDTO donhangDTO = new DonhangDTO();
                    donhangDTO.setMABAN(maban);
                    donhangDTO.setMATK(matk);
                    donhangDTO.setNGAYTAO(ngaytao);
                    donhangDTO.setTINHTRANG("false");
                    donhangDTO.setTONGTIEN("0");

                    long ktra = donhangDAO.ThemDonHang(donhangDTO);
                    banAnDAO.CapNhatTinhTrangBan(maban,"true");
                    if(ktra == 0){ Toast.makeText(context,context.getResources().getString(R.string.add_failed),Toast.LENGTH_SHORT).show(); }
                }
                //chuyển qua trang category
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                DisplayTypeFragment displayTypeFragment = new DisplayTypeFragment();

                Bundle bDataType = new Bundle();
                bDataType.putInt("maban",maban);
                displayTypeFragment.setArguments(bDataType);

                transaction.replace(R.id.contentView,displayTypeFragment).addToBackStack("hienthibanan");
                transaction.commit();
                break;

            case R.id.img_customtable_ThanhToan:
                //chuyển dữ liệu qua trang thanh toán
                Intent iThanhToan = new Intent(context, PaymentActivity.class);
                iThanhToan.putExtra("maban",maban);
                iThanhToan.putExtra("tenban",tenban);
                iThanhToan.putExtra("ngaytao",ngaytao);
                context.startActivity(iThanhToan);
                break;
        }
    }

    private void HienThiButton(){
        viewHolder.imgGoiMon.setVisibility(View.VISIBLE);
        viewHolder.imgThanhToan.setVisibility(View.VISIBLE);
        viewHolder.imgAnNut.setVisibility(View.VISIBLE);
    }
    private void AnButton(){
        viewHolder.imgGoiMon.setVisibility(View.INVISIBLE);
        viewHolder.imgThanhToan.setVisibility(View.INVISIBLE);
        viewHolder.imgAnNut.setVisibility(View.INVISIBLE);
    }

    public class ViewHolder{
        ImageView imgBanAn, imgGoiMon, imgThanhToan, imgAnNut;
        TextView txtTenBanAn;
    }
}
