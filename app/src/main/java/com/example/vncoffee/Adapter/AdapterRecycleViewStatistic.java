package com.example.vncoffee.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vncoffee.DAO.BananDAO;
import com.example.vncoffee.DAO.TaikhoanDAO;
import com.example.vncoffee.DTO.DonhangDTO;
import com.example.vncoffee.DTO.TaikhoanDTO;
import com.example.vncoffee.R;

import java.util.List;

public class AdapterRecycleViewStatistic extends RecyclerView.Adapter<AdapterRecycleViewStatistic.ViewHolder>{

    Context context;
    int layout;
    List<DonhangDTO> donhangDTOList;
    TaikhoanDAO taikhoanDAO;
    BananDAO banAnDAO;

    public AdapterRecycleViewStatistic(Context context, int layout, List<DonhangDTO> donhangDTOList){

        this.context =context;
        this.layout = layout;
        this.donhangDTOList = donhangDTOList;
        taikhoanDAO = new TaikhoanDAO(context);
        banAnDAO = new BananDAO(context);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterRecycleViewStatistic.ViewHolder holder, int position) {
        DonhangDTO donhangDTO = donhangDTOList.get(position);
        holder.txt_customstatistic_MaDon.setText("Mã đơn: "+donhangDTO.getMADON());
        holder.txt_customstatistic_NgayTao.setText(donhangDTO.getNGAYTAO());
        if(donhangDTO.getTONGTIEN().equals("0"))
        {
            holder.txt_customstatistic_TongTien.setVisibility(View.INVISIBLE);
        }else {
            holder.txt_customstatistic_TongTien.setVisibility(View.VISIBLE);
        }

        if (donhangDTO.getTINHTRANG().equals("true"))
        {
            holder.txt_customstatistic_TrangThai.setText("Đã thanh toán");
        }else {
            holder.txt_customstatistic_TrangThai.setText("Chưa thanh toán");
        }
        TaikhoanDTO taikhoanDTO = taikhoanDAO.LayTKTheoMa(donhangDTO.getMATK());
        holder.txt_customstatistic_TenTK.setText(taikhoanDTO.getHOTEN());
        holder.txt_customstatistic_BanDat.setText(banAnDAO.LayTenBanTheoMa(donhangDTO.getMABAN()));
    }

    @Override
    public int getItemCount() {
        return donhangDTOList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txt_customstatistic_MaDon, txt_customstatistic_NgayTao, txt_customstatistic_TenTK,
                txt_customstatistic_BanDat, txt_customstatistic_TongTien,txt_customstatistic_TrangThai;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            txt_customstatistic_MaDon = itemView.findViewById(R.id.txt_customstatistic_MaDon);
            txt_customstatistic_NgayTao = itemView.findViewById(R.id.txt_customstatistic_NgayTao);
            txt_customstatistic_TenTK = itemView.findViewById(R.id.txt_customstatistic_TenTK);
            txt_customstatistic_BanDat = itemView.findViewById(R.id.txt_customstatistic_BanDat);
            txt_customstatistic_TongTien = itemView.findViewById(R.id.txt_customstatistic_TongTien);
            txt_customstatistic_TrangThai = itemView.findViewById(R.id.txt_customstatistic_TrangThai);
        }
    }
}
