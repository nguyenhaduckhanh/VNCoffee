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

import com.example.vncoffee.R;
import com.example.vncoffee.DTO.LoaimonDTO;

import java.util.List;

public class AdapterDisplayType extends BaseAdapter {
    Context context;
    int layout;
    List<LoaimonDTO> loaimonDTOList ;
    ViewHolder viewHolder;

    public AdapterDisplayType(Context context, int layout, List<LoaimonDTO> loaiMonDTOList){
        this.context = context;
        this.layout = layout;
        this.loaimonDTOList = loaiMonDTOList;
    }
    @Override
    public int getCount() {
        return loaimonDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return loaimonDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return loaimonDTOList.get(position).getMALOAI();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        //nếu lần đầu gọi view
        if(view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,parent,false);

            //truyền component vào viewholder để ko gọi findview ở những lần hiển thị khác
            viewHolder.img_customtype_HinhLoai = (ImageView)view.findViewById(R.id.img_customtype_HinhLoai);
            viewHolder.txt_customtype_TenLoai = (TextView)view.findViewById(R.id.txt_customtype_TenLoai);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        LoaimonDTO loaiMonDTO = loaimonDTOList.get(position);

        viewHolder.txt_customtype_TenLoai.setText(loaiMonDTO.getTENLOAI());

        byte[] categoryimage = loaiMonDTO.getANH();
        Bitmap bitmap = BitmapFactory.decodeByteArray(categoryimage,0,categoryimage.length);
        viewHolder.img_customtype_HinhLoai.setImageBitmap(bitmap);

        return view;
    }

    //tạo viewholer lưu trữ component
    public class ViewHolder{
        TextView txt_customtype_TenLoai;
        ImageView img_customtype_HinhLoai;
    }
}
