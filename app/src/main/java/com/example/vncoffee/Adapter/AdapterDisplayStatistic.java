package com.example.vncoffee.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.vncoffee.DAO.BananDAO;
import com.example.vncoffee.DAO.TaikhoanDAO;
import com.example.vncoffee.DTO.DonhangDTO;
import com.example.vncoffee.DTO.TaikhoanDTO;
import com.example.vncoffee.R;

import java.util.List;

public class AdapterDisplayStatistic extends BaseAdapter{
    Context context;
    int layout;
    List<DonhangDTO> donhangDTOS;
    ViewHolder viewHolder;
    TaikhoanDAO taikhoanDAO;
    BananDAO banAnDAO;

    public AdapterDisplayStatistic(Context context, int layout, List<DonhangDTO> donhangDTOS){
        this.context = context;
        this.layout = layout;
        this.donhangDTOS = donhangDTOS;
        taikhoanDAO = new TaikhoanDAO(context);
        banAnDAO = new BananDAO(context);
    }

    @Override
    public int getCount() {
        return donhangDTOS.size();
    }

    @Override
    public Object getItem(int position) {
        return donhangDTOS.get(position);
    }

    @Override
    public long getItemId(int position) {
        return donhangDTOS.get(position).getMADON();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, parent, false);

            viewHolder.txt_customstatistic_MaDon = (TextView) view.findViewById(R.id.txt_customstatistic_MaDon);
            viewHolder.txt_customstatistic_NgayTao = (TextView) view.findViewById(R.id.txt_customstatistic_NgayTao);
            viewHolder.txt_customstatistic_TenTK = (TextView) view.findViewById(R.id.txt_customstatistic_TenTK);
            viewHolder.txt_customstatistic_TongTien = (TextView) view.findViewById(R.id.txt_customstatistic_TongTien);
            viewHolder.txt_customstatistic_TrangThai = (TextView) view.findViewById(R.id.txt_customstatistic_TrangThai);
            viewHolder.txt_customstatistic_BanDat = (TextView) view.findViewById(R.id.txt_customstatistic_BanDat);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        DonhangDTO donhangDTO = donhangDTOS.get(position);

        viewHolder.txt_customstatistic_MaDon.setText("Mã đơn: " + donhangDTO.getMADON());
        viewHolder.txt_customstatistic_NgayTao.setText(donhangDTO.getNGAYTAO());
        viewHolder.txt_customstatistic_TongTien.setText(donhangDTO.getTONGTIEN() + " VNĐ");
        if (donhangDTO.getTINHTRANG().equals("true")) {
            viewHolder.txt_customstatistic_TrangThai.setText("Đã thanh toán");
        } else {
            viewHolder.txt_customstatistic_TrangThai.setText("Chưa thanh toán");
        }
        TaikhoanDTO taikhoanDTO = taikhoanDAO.LayTKTheoMa(donhangDTO.getMATK());
        viewHolder.txt_customstatistic_TenTK.setText(taikhoanDTO.getHOTEN());
        viewHolder.txt_customstatistic_BanDat.setText(banAnDAO.LayTenBanTheoMa(donhangDTO.getMABAN()));

        return view;
    }
    public class ViewHolder{
        TextView txt_customstatistic_MaDon, txt_customstatistic_NgayTao, txt_customstatistic_TenTK
                ,txt_customstatistic_TongTien,txt_customstatistic_TrangThai, txt_customstatistic_BanDat;

    }
}
