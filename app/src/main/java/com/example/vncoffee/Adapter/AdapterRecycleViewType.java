package com.example.vncoffee.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vncoffee.DTO.LoaimonDTO;
import com.example.vncoffee.R;

import java.util.List;

public class AdapterRecycleViewType extends RecyclerView.Adapter<AdapterRecycleViewType.ViewHolder>{

    Context context;
    int layout;
    List<LoaimonDTO> loaiMonDTOList;

    public AdapterRecycleViewType(Context context, int layout, List<LoaimonDTO> loaiMonDTOList){
        this.context = context;
        this.layout = layout;
        this.loaiMonDTOList = loaiMonDTOList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterRecycleViewType.ViewHolder holder, int position) {
        LoaimonDTO loaiMonDTO = loaiMonDTOList.get(position);
        holder.txt_customtype_TenLoai.setText(loaiMonDTO.getTENLOAI());

        if (loaiMonDTO.getANH() != null) {
            String typeimage = loaiMonDTO.getANH();
            Bitmap bitmap = BitmapFactory.decodeFile(typeimage);
            holder.img_customtype_HinhLoai.setImageBitmap(bitmap);
        }
        else {
            holder.img_customtype_HinhLoai.setImageResource(R.drawable.espresso);
        }
    }

    @Override
    public int getItemCount() {
        return loaiMonDTOList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txt_customtype_TenLoai;
        ImageView img_customtype_HinhLoai;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            txt_customtype_TenLoai = itemView.findViewById(R.id.txt_customtype_TenLoai);
            img_customtype_HinhLoai = itemView.findViewById(R.id.img_customtype_HinhLoai);
        }
    }

}
