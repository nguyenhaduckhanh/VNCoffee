package com.example.vncoffee.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.vncoffee.DTO.TaikhoanDTO;
import com.example.vncoffee.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class TaikhoanDAO {
    SQLiteDatabase database;
    public TaikhoanDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public long ThemTaiKhoan(TaikhoanDTO taikhoanDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_TAIKHOAN_HOTEN,taikhoanDTO.getHOTEN());
        contentValues.put(CreateDatabase.TBL_TAIKHOAN_TENDN,taikhoanDTO.getTENDN());
        contentValues.put(CreateDatabase.TBL_TAIKHOAN_MATKHAU,taikhoanDTO.getMATKHAU());
        contentValues.put(CreateDatabase.TBL_TAIKHOAN_EMAIL,taikhoanDTO.getEMAIL());
        contentValues.put(CreateDatabase.TBL_TAIKHOAN_SDT,taikhoanDTO.getSDT());
        contentValues.put(CreateDatabase.TBL_TAIKHOAN_GIOITINH,taikhoanDTO.getGIOITINH());
        contentValues.put(CreateDatabase.TBL_TAIKHOAN_NGAYSINH,taikhoanDTO.getNGAYSINH());
        contentValues.put(CreateDatabase.TBL_TAIKHOAN_MAQUYEN,taikhoanDTO.getMAQUYEN());

        long ktra = database.insert(CreateDatabase.TBL_TAIKHOAN,null,contentValues);
        return ktra;
    }

    public long SuaTaiKhoan(TaikhoanDTO taikhoanDTO, int matk){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_TAIKHOAN_HOTEN,taikhoanDTO.getHOTEN());
        contentValues.put(CreateDatabase.TBL_TAIKHOAN_TENDN,taikhoanDTO.getTENDN());
        contentValues.put(CreateDatabase.TBL_TAIKHOAN_MATKHAU,taikhoanDTO.getMATKHAU());
        contentValues.put(CreateDatabase.TBL_TAIKHOAN_EMAIL,taikhoanDTO.getEMAIL());
        contentValues.put(CreateDatabase.TBL_TAIKHOAN_SDT,taikhoanDTO.getSDT());
        contentValues.put(CreateDatabase.TBL_TAIKHOAN_GIOITINH,taikhoanDTO.getGIOITINH());
        contentValues.put(CreateDatabase.TBL_TAIKHOAN_NGAYSINH,taikhoanDTO.getNGAYSINH());
        contentValues.put(CreateDatabase.TBL_TAIKHOAN_MAQUYEN,taikhoanDTO.getMAQUYEN());

        long ktra = database.update(CreateDatabase.TBL_TAIKHOAN,contentValues,CreateDatabase.TBL_TAIKHOAN_MATK+ "=" +matk,null);
        return ktra;
    }

    public int KiemTraDN(String tenDN, String matKhau){
        String query = "SELECT * FROM " +CreateDatabase.TBL_TAIKHOAN+ " WHERE "
                +CreateDatabase.TBL_TAIKHOAN_TENDN +" = '"+ tenDN+"' AND "+CreateDatabase.TBL_TAIKHOAN_MATKHAU +" = '" +matKhau +"'";
        int matk = 0;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            matk = cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_TAIKHOAN_MATK)) ;
            cursor.moveToNext();
        }
        return matk;
    }



    public boolean KtraTonTaiTK(){
        String query = "SELECT * FROM "+CreateDatabase.TBL_TAIKHOAN;
        Cursor cursor =database.rawQuery(query,null);
        if(cursor.getCount() != 0){
            return true;
        }else {
            return false;
        }
    }

    public List<TaikhoanDTO> LayDSTK(){
        List<TaikhoanDTO> TaikhoanDTOS = new ArrayList<TaikhoanDTO>();
        String query = "SELECT * FROM "+CreateDatabase.TBL_TAIKHOAN;

        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            TaikhoanDTO TaikhoanDTO = new TaikhoanDTO();
            TaikhoanDTO.setHOTEN(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_TAIKHOAN_HOTEN)));
            TaikhoanDTO.setEMAIL(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_TAIKHOAN_EMAIL)));
            TaikhoanDTO.setGIOITINH(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_TAIKHOAN_GIOITINH)));
            TaikhoanDTO.setNGAYSINH(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_TAIKHOAN_NGAYSINH)));
            TaikhoanDTO.setSDT(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_TAIKHOAN_SDT)));
            TaikhoanDTO.setTENDN(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_TAIKHOAN_TENDN)));
            TaikhoanDTO.setMATKHAU(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_TAIKHOAN_MATKHAU)));
            TaikhoanDTO.setMATK(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_TAIKHOAN_MATK)));
            TaikhoanDTO.setMAQUYEN(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_TAIKHOAN_MAQUYEN)));

            TaikhoanDTOS.add(TaikhoanDTO);
            cursor.moveToNext();
        }
        return TaikhoanDTOS;
    }

    public boolean XoaTK(int matk){
        if (matk != 1) {
            long ktra = database.delete(CreateDatabase.TBL_TAIKHOAN, CreateDatabase.TBL_TAIKHOAN_MATK + " = " + matk
                    , null);
            if (ktra != 0) {
                return true;
            } else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    public TaikhoanDTO LayTKTheoMa(int matk){
        TaikhoanDTO TaikhoanDTO = new TaikhoanDTO();
        String query = "SELECT * FROM "+CreateDatabase.TBL_TAIKHOAN+" WHERE "+CreateDatabase.TBL_TAIKHOAN_MATK+" = "+matk;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            TaikhoanDTO.setHOTEN(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_TAIKHOAN_HOTEN)));
            TaikhoanDTO.setEMAIL(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_TAIKHOAN_EMAIL)));
            TaikhoanDTO.setGIOITINH(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_TAIKHOAN_GIOITINH)));
            TaikhoanDTO.setNGAYSINH(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_TAIKHOAN_NGAYSINH)));
            TaikhoanDTO.setSDT(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_TAIKHOAN_SDT)));
            TaikhoanDTO.setTENDN(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_TAIKHOAN_TENDN)));
            TaikhoanDTO.setMATKHAU(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_TAIKHOAN_MATKHAU)));
            TaikhoanDTO.setMATK(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_TAIKHOAN_MATK)));
            TaikhoanDTO.setMAQUYEN(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_TAIKHOAN_MAQUYEN)));

            cursor.moveToNext();
        }
        return TaikhoanDTO;
    }

    public int LayQuyenTK(int matk){
        int maquyen = 0;
        String query = "SELECT * FROM "+CreateDatabase.TBL_TAIKHOAN+" WHERE "+CreateDatabase.TBL_TAIKHOAN_MATK+" = "+matk;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            maquyen = cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_TAIKHOAN_MAQUYEN));

            cursor.moveToNext();
        }
        return maquyen;
    }

    public boolean CapNhapMatKhau(TaikhoanDTO taikhoanDTO, int matk){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_TAIKHOAN_MATKHAU,taikhoanDTO.getMATKHAU());

        long ktra = database.update(CreateDatabase.TBL_TAIKHOAN,contentValues,
                CreateDatabase.TBL_TAIKHOAN_MATK+" = "+matk,null);
        if (ktra != 0){
            return true;
        }else {
            return false;
        }
    }

    public String LayMatKhau(int matk) {
        String matkhau = null;
        String query = "SELECT * FROM " + CreateDatabase.TBL_TAIKHOAN + " WHERE " + CreateDatabase.TBL_TAIKHOAN_MATK + " = " + matk;
        Cursor cursor = database.rawQuery(query, null);
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    matkhau = cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_TAIKHOAN_MATKHAU));
                }
            } finally {
                cursor.close();
            }
        }
        return matkhau;
    }

    //Check trùng
    public boolean KiemTraTenDN(String username) {
        String query = "SELECT * FROM " + CreateDatabase.TBL_TAIKHOAN + " WHERE " + CreateDatabase.TBL_TAIKHOAN_TENDN + " = ?";
        Cursor cursor = database.rawQuery(query, new String[]{username});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public boolean KiemTraEmail(String email) {
        String query = "SELECT * FROM " + CreateDatabase.TBL_TAIKHOAN + " WHERE " + CreateDatabase.TBL_TAIKHOAN_EMAIL + " = ?";
        Cursor cursor = database.rawQuery(query, new String[]{email});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public boolean KiemTraSĐT(String sdt) {
        String query = "SELECT * FROM " + CreateDatabase.TBL_TAIKHOAN + " WHERE " + CreateDatabase.TBL_TAIKHOAN_SDT + " = ?";
        Cursor cursor = database.rawQuery(query, new String[]{sdt});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
}
