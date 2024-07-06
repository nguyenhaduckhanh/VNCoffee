package com.example.vncoffee.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.vncoffee.DTO.LoaimonDTO;
import com.example.vncoffee.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class LoaimonDAO {
    SQLiteDatabase database;
    public LoaimonDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public boolean ThemLoaiMon(LoaimonDTO loaiMonDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_LOAIMON_TENLOAI,loaiMonDTO.getTENLOAI());
        contentValues.put(CreateDatabase.TBL_LOAIMON_ANH,loaiMonDTO.getANH());
        long ktra = database.insert(CreateDatabase.TBL_LOAIMON,null,contentValues);

        if(ktra != 0){
            return true;
        }else {
            return false;
        }
    }

    public boolean XoaLoaiMon(int maloai){
        long ktra = database.delete(CreateDatabase.TBL_LOAIMON,CreateDatabase.TBL_LOAIMON_MALOAI+ " = " +maloai
                ,null);
        if(ktra !=0 ){
            return true;
        }else {
            return false;
        }
    }

    public boolean SuaLoaiMon(LoaimonDTO loaiMonDTO,int maloai){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_LOAIMON_TENLOAI,loaiMonDTO.getTENLOAI());
        contentValues.put(CreateDatabase.TBL_LOAIMON_ANH,loaiMonDTO.getANH());
        long ktra = database.update(CreateDatabase.TBL_LOAIMON,contentValues
                ,CreateDatabase.TBL_LOAIMON_MALOAI+" = "+maloai,null);
        if(ktra != 0){
            return true;
        }else {
            return false;
        }
    }

    public List<LoaimonDTO> LayDSLoaiMon(){
        List<LoaimonDTO> loaiMonDTOList = new ArrayList<LoaimonDTO>();
        String query = "SELECT * FROM " +CreateDatabase.TBL_LOAIMON;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            LoaimonDTO loaiMonDTO = new LoaimonDTO();
            loaiMonDTO.setMALOAI(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_LOAIMON_MALOAI)));
            loaiMonDTO.setTENLOAI(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_LOAIMON_TENLOAI)));
            loaiMonDTO.setANH(cursor.getBlob(cursor.getColumnIndex(CreateDatabase.TBL_LOAIMON_ANH)));
            loaiMonDTOList.add(loaiMonDTO);

            cursor.moveToNext();
        }
        return loaiMonDTOList;
    }

    public LoaimonDTO LayLoaiMonTheoMa(int maloai){
        LoaimonDTO loaiMonDTO = new LoaimonDTO();
        String query = "SELECT * FROM " +CreateDatabase.TBL_LOAIMON+" WHERE "+CreateDatabase.TBL_LOAIMON_MALOAI+" = "+maloai;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            loaiMonDTO.setMALOAI(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_LOAIMON_MALOAI)));
            loaiMonDTO.setTENLOAI(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_LOAIMON_TENLOAI)));
            loaiMonDTO.setANH(cursor.getBlob(cursor.getColumnIndex(CreateDatabase.TBL_LOAIMON_ANH)));

            cursor.moveToNext();
        }
        return loaiMonDTO;
    }

    //check trung
    public boolean KiemTraTenLoai(String ten) {
        String query = "SELECT * FROM " + CreateDatabase.TBL_LOAIMON + " WHERE " + CreateDatabase.TBL_LOAIMON_TENLOAI + " = ?";
        Cursor cursor = database.rawQuery(query, new String[]{ten});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
}
