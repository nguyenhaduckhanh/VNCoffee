package com.example.vncoffee.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vncoffee.DTO.ThucdonDTO;
import com.example.vncoffee.R;

import java.util.List;

public class AdapterDisplayMenu extends BaseAdapter {
    Context context;
    int layout;
    List<ThucdonDTO> thucdonDTOList;
    Viewholder viewholder;

    public AdapterDisplayMenu(Context context, int layout, List<ThucdonDTO> thucdonDTOList) {
        this.context = context;
        this.layout = layout;
        this.thucdonDTOList = thucdonDTOList;
    }

    @Override
    public int getCount() {
        return thucdonDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return thucdonDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return thucdonDTOList.get(position).getMAMON();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            viewholder = new Viewholder();
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,parent,false);

            viewholder.img_custommenu_HinhMon = (ImageView)view.findViewById(R.id.img_custommenu_HinhMon);
            viewholder.txt_custommenu_TenMon = (TextView) view.findViewById(R.id.txt_custommenu_TenMon);
            viewholder.txt_custommenu_SoLuong = (TextView)view.findViewById(R.id.txt_custommenu_SoLuong);
            viewholder.txt_custommenu_GiaTien = (TextView)view.findViewById(R.id.txt_custommenu_GiaTien);
            view.setTag(viewholder);
        }else {
            viewholder = (Viewholder) view.getTag();
        }
        ThucdonDTO thucdonDTO = thucdonDTOList.get(position);
        viewholder.txt_custommenu_TenMon.setText(thucdonDTO.getTENMON());
        viewholder.txt_custommenu_GiaTien.setText(thucdonDTO.getGIATIEN()+" VNĐ");
        viewholder.txt_custommenu_SoLuong.setText("Số lượng: " +thucdonDTO.getSOLUONG());

        //lấy hình ảnh
        if(thucdonDTO.getANH() != null){
            String menuimage = thucdonDTO.getANH();
            Bitmap bitmap = BitmapFactory.decodeFile(menuimage);
            viewholder.img_custommenu_HinhMon.setImageBitmap(bitmap);
        }else {
            viewholder.img_custommenu_HinhMon.setImageResource(R.drawable.cafe_americano);
        }

        return view;
    }
    public class Viewholder{
        ImageView img_custommenu_HinhMon;
        TextView txt_custommenu_TenMon, txt_custommenu_GiaTien,txt_custommenu_SoLuong;

    }
}
