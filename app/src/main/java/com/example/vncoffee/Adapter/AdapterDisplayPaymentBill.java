package com.example.vncoffee.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.vncoffee.Activities.PaymentActivity;
import com.example.vncoffee.DAO.DonhangDAO;
import com.example.vncoffee.DAO.ThanhtoanDAO;
import com.example.vncoffee.DTO.ThanhtoanDTO;
import com.example.vncoffee.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterDisplayPaymentBill extends BaseAdapter {

    Context context;
    int layout;
    List<ThanhtoanDTO> thanhtoanDTOList;
    ViewHolder viewHolder;
    ThanhtoanDAO thanhtoanDAO;
    DonhangDAO donhangDAO;
    OnDeleteItemClickListener onDeleteItemClickListener;

    public AdapterDisplayPaymentBill(Context context, int layout, List<ThanhtoanDTO> thanhToanDTOList){
        this.context = context;
        this.layout = layout;
        this.thanhtoanDTOList = thanhToanDTOList;
        this.thanhtoanDAO = new ThanhtoanDAO(context);
        this.donhangDAO = new DonhangDAO(context);
    }

    @Override
    public int getCount() {
        return thanhtoanDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return thanhtoanDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setOnDeleteItemClickListener(OnDeleteItemClickListener listener) {
        this.onDeleteItemClickListener = listener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,parent,false);

            viewHolder.img_custompayment_HinhMon = (CircleImageView)view.findViewById(R.id.img_custompayment_HinhMon);
            viewHolder.txt_custompayment_TenMon = (TextView)view.findViewById(R.id.txt_custompayment_TenMon);
            viewHolder.txt_custompayment_SoLuong = (TextView)view.findViewById(R.id.txt_custompayment_SoLuong);
            viewHolder.txt_custompayment_GiaTien = (TextView)view.findViewById(R.id.txt_custompayment_GiaTien);
            viewHolder.btn_custompayment_XoaMon = (Button)view.findViewById(R.id.btn_custompayment_XoaMon);

            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)view.getTag();
        }

        ThanhtoanDTO thanhtoanDTO = thanhtoanDTOList.get(position);

        viewHolder.txt_custompayment_TenMon.setText(thanhtoanDTO.getTENMON());
        viewHolder.txt_custompayment_SoLuong.setText(String.valueOf(thanhtoanDTO.getSOLUONGDAT()));
        viewHolder.txt_custompayment_GiaTien.setText(String.valueOf(thanhtoanDTO.getGIATIEN())+" đ");

        byte[] paymentimg = thanhtoanDTO.getANH();
        Bitmap bitmap = BitmapFactory.decodeByteArray(paymentimg,0,paymentimg.length);
        viewHolder.img_custompayment_HinhMon.setImageBitmap(bitmap);

        viewHolder.btn_custompayment_XoaMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDeleteItemClickListener != null) {
                    onDeleteItemClickListener.onDeleteItem(thanhtoanDTO.getMADON(), thanhtoanDTO.getMAMON());
                }
                thanhtoanDTOList.remove(position);
                notifyDataSetChanged();
            }
        });

        return view;
    }



    public class ViewHolder{
        CircleImageView img_custompayment_HinhMon;
        TextView txt_custompayment_TenMon, txt_custompayment_SoLuong, txt_custompayment_GiaTien;
        Button btn_custompayment_XoaMon;
    }

    public interface OnDeleteItemClickListener {
        void onDeleteItem(int madon, int mamon);
    }
}
