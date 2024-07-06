package com.example.vncoffee.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vncoffee.R;
import com.example.vncoffee.DTO.TaikhoanDTO;
import com.example.vncoffee.DAO.QuyenDAO;

import java.util.List;

public class AdapterDisplayAcc extends BaseAdapter{
    Context context;
    int layout;
    List<TaikhoanDTO> taikhoanDTOS;
    ViewHolder viewHolder;
    QuyenDAO quyenDAO;

    public AdapterDisplayAcc(Context context, int layout, List<TaikhoanDTO> nhanVienDTOS){
        this.context = context;
        this.layout = layout;
        this.taikhoanDTOS = nhanVienDTOS;
        quyenDAO = new QuyenDAO(context);
    }

    @Override
    public int getCount() {
        return taikhoanDTOS.size();
    }

    @Override
    public Object getItem(int position) {
        return taikhoanDTOS.get(position);
    }

    @Override
    public long getItemId(int position) {
        return taikhoanDTOS.get(position).getMATK();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,parent,false);

            viewHolder.img_customacc_HinhNV = (ImageView)view.findViewById(R.id.img_customacc_HinhNV);
            viewHolder.txt_customacc_TenNV = (TextView)view.findViewById(R.id.txt_customacc_TenNV);
            viewHolder.txt_customacc_TenQuyen = (TextView)view.findViewById(R.id.txt_customacc_TenQuyen);
            viewHolder.txt_customacc_SDT = (TextView)view.findViewById(R.id.txt_customacc_SDT);
            viewHolder.txt_customacc_Email = (TextView)view.findViewById(R.id.txt_customacc_Email);

            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        TaikhoanDTO taikhoanDTO = taikhoanDTOS.get(position);

        viewHolder.txt_customacc_TenNV.setText(taikhoanDTO.getHOTEN());
        viewHolder.txt_customacc_TenQuyen.setText(quyenDAO.LayTenQuyenTheoMa(taikhoanDTO.getMAQUYEN()));
        viewHolder.txt_customacc_SDT.setText(taikhoanDTO.getSDT());
        viewHolder.txt_customacc_Email.setText(taikhoanDTO.getEMAIL());

        return view;
    }

    public class ViewHolder{
        ImageView img_customacc_HinhNV;
        TextView txt_customacc_TenNV, txt_customacc_TenQuyen,txt_customacc_SDT, txt_customacc_Email;
    }
}
