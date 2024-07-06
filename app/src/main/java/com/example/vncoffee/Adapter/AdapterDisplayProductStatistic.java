package com.example.vncoffee.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.vncoffee.DTO.ThucdonDTO;
import com.example.vncoffee.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterDisplayProductStatistic extends BaseAdapter {
    Context context;
    int layout;
    List<ThucdonDTO> thucdonDTOList;
    ViewHolder viewHolder;

    public AdapterDisplayProductStatistic(Context context, int layout, List<ThucdonDTO> thucdonDTOList) {
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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.img_customproduct_HinhMon = convertView.findViewById(R.id.img_customproduct_HinhMon);
            viewHolder.txt_customproduct_TenMon = convertView.findViewById(R.id.txt_customproduct_TenMon);
            viewHolder.txt_customproduct_SoLuong = convertView.findViewById(R.id.txt_customproduct_Soluong);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ThucdonDTO thucdonDTO = thucdonDTOList.get(position);

        viewHolder.txt_customproduct_TenMon.setText(thucdonDTO.getTENMON());
        viewHolder.txt_customproduct_SoLuong.setText(String.valueOf(thucdonDTO.getSOLUONG()));

        byte[] productimg = thucdonDTO.getANH();
        if (productimg != null && productimg.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(productimg, 0, productimg.length);
            viewHolder.img_customproduct_HinhMon.setImageBitmap(bitmap);
        } else {
            // Đặt một hình ảnh mặc định hoặc xử lý trường hợp không có ảnh
            viewHolder.img_customproduct_HinhMon.setImageResource(R.drawable.cafe_americano); // Thay thế default_image bằng hình ảnh mặc định của bạn
        }

        return convertView;
    }

    private static class ViewHolder {
        CircleImageView img_customproduct_HinhMon;
        TextView txt_customproduct_TenMon, txt_customproduct_SoLuong;
    }
}
