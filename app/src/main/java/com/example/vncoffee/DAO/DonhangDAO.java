package com.example.vncoffee.DAO;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;

import com.example.vncoffee.DTO.DonhangDTO;
import com.example.vncoffee.Database.CreateDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class DonhangDAO {
    SQLiteDatabase database;
    public DonhangDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public long ThemDonHang(DonhangDTO donDatDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_DONHANG_MABAN,donDatDTO.getMABAN());
        contentValues.put(CreateDatabase.TBL_DONHANG_MATK,donDatDTO.getMATK());
        contentValues.put(CreateDatabase.TBL_DONHANG_NGAYTAO,donDatDTO.getNGAYTAO());
        contentValues.put(CreateDatabase.TBL_DONHANG_TINHTRANG,donDatDTO.getTINHTRANG());
        contentValues.put(CreateDatabase.TBL_DONHANG_TONGTIEN,donDatDTO.getTONGTIEN());

        long madonhang = database.insert(CreateDatabase.TBL_DONHANG,null,contentValues);

        return madonhang;
    }

    public List<DonhangDTO> LayDSDonHang(){
        List<DonhangDTO> donhangDTOS = new ArrayList<DonhangDTO>();
        String query = "SELECT * FROM "+CreateDatabase.TBL_DONHANG;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            DonhangDTO donhangDTO = new DonhangDTO();
            donhangDTO.setMADON(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONHANG_MADON)));
            donhangDTO.setMABAN(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONHANG_MABAN)));
            donhangDTO.setTONGTIEN(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_DONHANG_TONGTIEN)));
            donhangDTO.setTINHTRANG(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_DONHANG_TINHTRANG)));
            donhangDTO.setNGAYTAO(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_DONHANG_NGAYTAO)));
            donhangDTO.setMATK(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONHANG_MATK)));
            donhangDTOS.add(donhangDTO);

            cursor.moveToNext();
        }
        return donhangDTOS;
    }

    public List<DonhangDTO> LayDSDonChuaThanhToan(){
        List<DonhangDTO> donhangDTOS = new ArrayList<DonhangDTO>();
        String query = "SELECT * FROM "+CreateDatabase.TBL_DONHANG+" WHERE " + CreateDatabase.TBL_DONHANG_TINHTRANG + "= 'false'";
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            DonhangDTO donhangDTO = new DonhangDTO();
            donhangDTO.setMADON(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONHANG_MADON)));
            donhangDTO.setMABAN(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONHANG_MABAN)));
            donhangDTO.setTONGTIEN(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_DONHANG_TONGTIEN)));
            donhangDTO.setTINHTRANG(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_DONHANG_TINHTRANG)));
            donhangDTO.setNGAYTAO(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_DONHANG_NGAYTAO)));
            donhangDTO.setMATK(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONHANG_MATK)));
            donhangDTOS.add(donhangDTO);

            cursor.moveToNext();
        }
        return donhangDTOS;
    }

    public List<DonhangDTO> LayDSDonHangNgay(String ngaythang){
        List<DonhangDTO> donhangDTOS = new ArrayList<DonhangDTO>();
        String query = "SELECT * FROM "+CreateDatabase.TBL_DONHANG+" WHERE "+CreateDatabase.TBL_DONHANG_NGAYTAO+" like '"+ngaythang+"'";
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            DonhangDTO donhangDTO = new DonhangDTO();
            donhangDTO.setMADON(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONHANG_MADON)));
            donhangDTO.setMABAN(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONHANG_MABAN)));
            donhangDTO.setTONGTIEN(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_DONHANG_TONGTIEN)));
            donhangDTO.setTINHTRANG(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_DONHANG_TINHTRANG)));
            donhangDTO.setNGAYTAO(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_DONHANG_NGAYTAO)));
            donhangDTO.setMATK(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONHANG_MATK)));
            donhangDTOS.add(donhangDTO);

            cursor.moveToNext();
        }
        return donhangDTOS;
    }

    public long LayMaDonTheoMaBan(int maban, String tinhtrang){
        String query = "SELECT * FROM " +CreateDatabase.TBL_DONHANG+ " WHERE " +CreateDatabase.TBL_DONHANG_MABAN+ " = '" +maban+ "' AND "
                +CreateDatabase.TBL_DONHANG_TINHTRANG+ " = '" +tinhtrang+ "' ";
        long magoimon = 0;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            magoimon = cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONHANG_MADON));

            cursor.moveToNext();
        }
        return magoimon;
    }


    public boolean UpdateTThaiDonTheoMaBan(int maban,String tinhtrang){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_DONHANG_TINHTRANG,tinhtrang);
        long ktra = database.update(CreateDatabase.TBL_DONHANG,contentValues,CreateDatabase.TBL_DONHANG_MABAN+
                " = '"+maban+"'",null);
        if(ktra !=0){
            return true;
        }else {
            return false;
        }
    }

    // Hàm lấy tổng tiền của đơn hàng dựa trên mã đơn hàng
    public int LayTongTien(int madonhang) {
        int tongtien = 0;
        String query = "SELECT " + CreateDatabase.TBL_DONHANG_TONGTIEN +
                " FROM " + CreateDatabase.TBL_DONHANG +
                " WHERE " + CreateDatabase.TBL_DONHANG_MADON + " = " + madonhang;

        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            tongtien = cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONHANG_TONGTIEN));
        }
        cursor.close();
        return tongtien;
    }

    // Hàm cập nhật tổng tiền của đơn hàng
    public boolean CapNhatTongTien(int madonhang, long tongtien) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_DONHANG_TONGTIEN, tongtien);

        int result = database.update(CreateDatabase.TBL_DONHANG, contentValues,
                CreateDatabase.TBL_DONHANG_MADON + " = ?", new String[]{String.valueOf(madonhang)});
        return result != 0;
    }

    public List<DonhangDTO> LayDSDonTheoNgay(String ngaythang) {
        List<DonhangDTO> donhangList = new ArrayList<>();

        // Đảm bảo định dạng ngày tháng vào đúng định dạng của cơ sở dữ liệu
        SimpleDateFormat sdfInput = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            date = sdfInput.parse(ngaythang);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedDate = sdfInput.format(date);

        String query = "SELECT * FROM " + CreateDatabase.TBL_DONHANG + " WHERE " + CreateDatabase.TBL_DONHANG_NGAYTAO + " = ?";
        Cursor cursor = null;
        try {
            cursor = database.rawQuery(query, new String[]{formattedDate});
            if (cursor.moveToFirst()) {
                do {
                    DonhangDTO donhangDTO = new DonhangDTO();
                    donhangDTO.setMADON(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONHANG_MADON)));
                    donhangDTO.setMABAN(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONHANG_MABAN)));
                    donhangDTO.setTONGTIEN(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_DONHANG_TONGTIEN)));
                    donhangDTO.setTINHTRANG(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_DONHANG_TINHTRANG)));
                    donhangDTO.setNGAYTAO(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_DONHANG_NGAYTAO)));
                    donhangDTO.setMATK(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONHANG_MATK)));
                    donhangList.add(donhangDTO);
                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return donhangList;
    }

}
