package com.example.vncoffee.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.vncoffee.DTO.ThucdonDTO;
import com.example.vncoffee.Database.CreateDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ThucdonDAO {
    SQLiteDatabase database;
    public ThucdonDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database= createDatabase.open();
    }

    public boolean ThemMon(ThucdonDTO thucdonDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_THUCDON_MALOAI,thucdonDTO.getMALOAI());
        contentValues.put(CreateDatabase.TBL_THUCDON_TENMON,thucdonDTO.getTENMON());
        contentValues.put(CreateDatabase.TBL_THUCDON_GIATIEN,thucdonDTO.getGIATIEN());
        contentValues.put(CreateDatabase.TBL_THUCDON_ANH,thucdonDTO.getANH());
        contentValues.put(CreateDatabase.TBL_THUCDON_SOLUONG,thucdonDTO.getSOLUONG() );

        long ktra = database.insert(CreateDatabase.TBL_THUCDON,null,contentValues);

        if(ktra !=0){
            return true;
        }else {
            return false;
        }
    }

    public boolean XoaMon(int mamon){
        long ktra = database.delete(CreateDatabase.TBL_THUCDON,CreateDatabase.TBL_THUCDON_MAMON+ " = " +mamon
                ,null);
        if(ktra !=0 ){
            return true;
        }else {
            return false;
        }
    }

    public boolean SuaMon(ThucdonDTO thucdonDTO,int mamon){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_THUCDON_MALOAI,thucdonDTO.getMALOAI());
        contentValues.put(CreateDatabase.TBL_THUCDON_TENMON,thucdonDTO.getTENMON());
        contentValues.put(CreateDatabase.TBL_THUCDON_GIATIEN,thucdonDTO.getGIATIEN());
        contentValues.put(CreateDatabase.TBL_THUCDON_ANH,thucdonDTO.getANH());
        contentValues.put(CreateDatabase.TBL_THUCDON_SOLUONG,thucdonDTO.getSOLUONG() );

        long ktra = database.update(CreateDatabase.TBL_THUCDON,contentValues,
                CreateDatabase.TBL_THUCDON_MAMON+" = "+mamon,null);
        if(ktra !=0){
            return true;
        }else {
            return false;
        }
    }

    public List<ThucdonDTO> LayDSMonTheoLoai(int maloai){
        List<ThucdonDTO> thucdonDTOList = new ArrayList<ThucdonDTO>();
        String query = "SELECT * FROM " +CreateDatabase.TBL_THUCDON+ " WHERE " +CreateDatabase.TBL_THUCDON_MALOAI+ " = '" +maloai+ "' ";
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            ThucdonDTO thucdonDTO = new ThucdonDTO();
            thucdonDTO.setANH(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_THUCDON_ANH)));
            thucdonDTO.setTENMON(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_THUCDON_TENMON)));
            thucdonDTO.setMALOAI(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_THUCDON_MALOAI)));
            thucdonDTO.setMAMON(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_THUCDON_MAMON)));
            thucdonDTO.setGIATIEN(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_THUCDON_GIATIEN)));
            thucdonDTO.setSOLUONG(cursor.getInt((cursor.getColumnIndexOrThrow(CreateDatabase.TBL_THUCDON_SOLUONG))));
            thucdonDTOList.add(thucdonDTO);

            cursor.moveToNext();
        }
        return thucdonDTOList;
    }

    public ThucdonDTO LayMonTheoMa(int mamon){
        ThucdonDTO thucdonDTO = new ThucdonDTO();
        String query = "SELECT * FROM "+CreateDatabase.TBL_THUCDON+" WHERE "+CreateDatabase.TBL_THUCDON_MAMON+" = "+mamon;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            thucdonDTO.setANH(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_THUCDON_ANH)));
            thucdonDTO.setTENMON(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_THUCDON_TENMON)));
            thucdonDTO.setMALOAI(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_THUCDON_MALOAI)));
            thucdonDTO.setMAMON(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_THUCDON_MAMON)));
            thucdonDTO.setGIATIEN(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_THUCDON_GIATIEN)));
            thucdonDTO.setSOLUONG(cursor.getInt((cursor.getColumnIndexOrThrow(CreateDatabase.TBL_THUCDON_SOLUONG))));

            cursor.moveToNext();
        }
        return thucdonDTO;
    }

    public int LaySoLuongMon(int mamon) {
        int soluong = 0;
        String query = " SELECT " +CreateDatabase.TBL_THUCDON_SOLUONG + " FROM " +CreateDatabase.TBL_THUCDON+
                " WHERE " +CreateDatabase.TBL_THUCDON_MAMON + " = " + mamon;
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            soluong = cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_THUCDON_SOLUONG));
        }
        cursor.close();
        return soluong;
    }

    public boolean CapNhatSoLuongMon(int mamon, int soluongmoi) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_THUCDON_SOLUONG, soluongmoi);
        int rowsAffected = database.update(CreateDatabase.TBL_THUCDON, contentValues, CreateDatabase.TBL_THUCDON_MAMON + " = ?", new String[]{String.valueOf(mamon)});
        return rowsAffected > 0;
    }

    public int LayGiaMon(int mamon) {
        int gia = 0;
        String query = "SELECT " + CreateDatabase.TBL_THUCDON_GIATIEN +
                " FROM " + CreateDatabase.TBL_THUCDON +
                " WHERE " + CreateDatabase.TBL_THUCDON_MAMON + " = " + mamon;

        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            gia = cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_THUCDON_GIATIEN));
        }
        cursor.close();
        return gia;
    }



    public List<ThucdonDTO> LayTongSoLuongDatCuaMon() {
        List<ThucdonDTO> productList = new ArrayList<>();
        String query = "SELECT SP." + CreateDatabase.TBL_THUCDON_MAMON + ", SP." + CreateDatabase.TBL_THUCDON_TENMON + ", IFNULL(SUM(CHITIETDONHANG." + CreateDatabase.TBL_CHITIETDONHANG_SOLUONGDAT + "), 0) AS TotalSold, SP." + CreateDatabase.TBL_THUCDON_ANH + " " +
                "FROM " + CreateDatabase.TBL_THUCDON + " SP " +
                "LEFT JOIN " +
                "(SELECT " + CreateDatabase.TBL_CHITIETDONHANG_MAMON + ", SUM(" + CreateDatabase.TBL_CHITIETDONHANG_SOLUONGDAT + ") AS SOLUONGDAT " +
                "FROM " + CreateDatabase.TBL_CHITIETDONHANG +
                " GROUP BY " + CreateDatabase.TBL_CHITIETDONHANG_MAMON + ") CHITIETDONHANG " +
                "ON SP." + CreateDatabase.TBL_THUCDON_MAMON + " = CHITIETDONHANG." + CreateDatabase.TBL_CHITIETDONHANG_MAMON + " " +
                "GROUP BY SP." + CreateDatabase.TBL_THUCDON_MAMON + ", SP." + CreateDatabase.TBL_THUCDON_TENMON + ", SP." + CreateDatabase.TBL_THUCDON_ANH;
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                ThucdonDTO thucdonDTO = new ThucdonDTO();
                thucdonDTO.setMAMON(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_THUCDON_MAMON)));
                thucdonDTO.setTENMON(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_THUCDON_TENMON)));
                thucdonDTO.setSOLUONG(cursor.getInt(cursor.getColumnIndexOrThrow("TotalSold")));
                thucdonDTO.setANH(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_THUCDON_ANH))); // Lấy ảnh từ cursor
                productList.add(thucdonDTO);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return productList;
    }

    public List<ThucdonDTO> LayTongSoLuongDatCuaMonTheoNgay(String ngaythang) {
        List<ThucdonDTO> productList = new ArrayList<>();

        // Đảm bảo định dạng ngày tháng vào đúng định dạng của cơ sở dữ liệu
        SimpleDateFormat sdfInput = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            date = sdfInput.parse(ngaythang);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedDate = sdfInput.format(date);


        String query = "SELECT SP." + CreateDatabase.TBL_THUCDON_MAMON + ", SP." + CreateDatabase.TBL_THUCDON_TENMON + ", " +
                "IFNULL(SUM(CHITIETDONHANG." + CreateDatabase.TBL_CHITIETDONHANG_SOLUONGDAT + "), 0) AS TotalSold, " +
                "SP." + CreateDatabase.TBL_THUCDON_ANH + " " +
                "FROM " + CreateDatabase.TBL_THUCDON + " SP " +
                "LEFT JOIN (" +
                "SELECT " + CreateDatabase.TBL_CHITIETDONHANG_MAMON + ", SUM(" + CreateDatabase.TBL_CHITIETDONHANG_SOLUONGDAT + ") AS SOLUONGDAT " +
                "FROM " + CreateDatabase.TBL_CHITIETDONHANG + " " +
                "INNER JOIN " + CreateDatabase.TBL_DONHANG +
                " ON " + CreateDatabase.TBL_CHITIETDONHANG + "." + CreateDatabase.TBL_CHITIETDONHANG_MADON + " = " + CreateDatabase.TBL_DONHANG + "." + CreateDatabase.TBL_DONHANG_MADON + " " +
                "WHERE " + CreateDatabase.TBL_DONHANG + "." + CreateDatabase.TBL_DONHANG_NGAYTAO + " = ? " +
                "GROUP BY " + CreateDatabase.TBL_CHITIETDONHANG_MAMON + ") CHITIETDONHANG " +
                "ON SP." + CreateDatabase.TBL_THUCDON_MAMON + " = CHITIETDONHANG." + CreateDatabase.TBL_CHITIETDONHANG_MAMON + " " +
                "GROUP BY SP." + CreateDatabase.TBL_THUCDON_MAMON + ", SP." + CreateDatabase.TBL_THUCDON_TENMON + ", SP." + CreateDatabase.TBL_THUCDON_ANH;

        Cursor cursor = null;
        try {
            cursor = database.rawQuery(query, new String[]{formattedDate});
            if (cursor.moveToFirst()) {
                do {
                    ThucdonDTO thucdonDTO = new ThucdonDTO();
                    thucdonDTO.setMAMON(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_THUCDON_MAMON)));
                    thucdonDTO.setTENMON(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_THUCDON_TENMON)));
                    thucdonDTO.setSOLUONG(cursor.getInt(cursor.getColumnIndexOrThrow("TotalSold")));
                    thucdonDTO.setANH(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_THUCDON_ANH)));
                    productList.add(thucdonDTO);
                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return productList;
    }

    //check trung
    public boolean KiemTraTenMon(String ten) {
        String query = "SELECT * FROM " + CreateDatabase.TBL_THUCDON + " WHERE " + CreateDatabase.TBL_THUCDON_TENMON + " = ?";
        Cursor cursor = database.rawQuery(query, new String[]{ten});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
}
