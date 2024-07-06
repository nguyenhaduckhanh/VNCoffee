package com.example.vncoffee.DAO;

import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;

import com.example.vncoffee.DTO.ThanhtoanDTO;
import com.example.vncoffee.Database.CreateDatabase;

import java.util.List;
import java.util.ArrayList;

public class ThanhtoanDAO {
    SQLiteDatabase database;
    public ThanhtoanDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public List<ThanhtoanDTO> LayDSMonTheoMaDon(int madondat){
        List<ThanhtoanDTO> thanhToanDTOS = new ArrayList<ThanhtoanDTO>();
        String query = "SELECT * FROM "+CreateDatabase.TBL_CHITIETDONHANG+" ctdh,"+CreateDatabase.TBL_THUCDON+" mon WHERE "
                +"ctdh."+CreateDatabase.TBL_CHITIETDONHANG_MAMON+" = mon."+CreateDatabase.TBL_THUCDON_MAMON+" AND "
                +CreateDatabase.TBL_CHITIETDONHANG_MADON+" = '"+madondat+"'";

        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            ThanhtoanDTO thanhToanDTO = new ThanhtoanDTO();
            thanhToanDTO.setSOLUONGDAT(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_CHITIETDONHANG_SOLUONGDAT)));
            thanhToanDTO.setGIATIEN(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_THUCDON_GIATIEN)));
            thanhToanDTO.setTENMON(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_THUCDON_TENMON)));
            thanhToanDTO.setANH(cursor.getBlob(cursor.getColumnIndex(CreateDatabase.TBL_THUCDON_ANH)));
            thanhToanDTO.setMAMON(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_CHITIETDONHANG_MAMON)));
            thanhToanDTO.setMADON(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_CHITIETDONHANG_MADON)));
            thanhToanDTOS.add(thanhToanDTO);

            cursor.moveToNext();
        }

        return thanhToanDTOS;
    }

    public int LaySoLuongMonTrongDon(int madon, int mamon) {
        int soLuong = 0;
        String query = "SELECT " + CreateDatabase.TBL_CHITIETDONHANG_SOLUONGDAT + " FROM " + CreateDatabase.TBL_CHITIETDONHANG +
                " WHERE " + CreateDatabase.TBL_CHITIETDONHANG_MADON + " = " + madon + " AND " +
                CreateDatabase.TBL_CHITIETDONHANG_MAMON + " = " + mamon;
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            soLuong = cursor.getInt(0);
        }
        cursor.close();
        return soLuong;
    }

    public boolean XoaMon(int mamon, int madon) {
        long result = database.delete(CreateDatabase.TBL_CHITIETDONHANG,
                CreateDatabase.TBL_CHITIETDONHANG_MAMON + " = ? AND " +
                        CreateDatabase.TBL_CHITIETDONHANG_MADON + " = ?",
                new String[]{String.valueOf(mamon), String.valueOf(madon)});
        return result != -1;
    }
}
