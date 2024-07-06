package com.example.vncoffee.DAO;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;

import com.example.vncoffee.DTO.ChitietdonhangDTO;
import com.example.vncoffee.Database.CreateDatabase;


public class ChitietdonhangDAO {
    SQLiteDatabase database;
    public ChitietdonhangDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public boolean KiemTraMonTonTai(int madondat, int mamon){
        String query = "SELECT * FROM " +CreateDatabase.TBL_CHITIETDONHANG+ " WHERE " +CreateDatabase.TBL_CHITIETDONHANG_MAMON+
                " = " +mamon+ " AND " +CreateDatabase.TBL_CHITIETDONHANG_MADON+ " = "+madondat;
        Cursor cursor = database.rawQuery(query,null);
        if(cursor.getCount() != 0){
            return true;
        }else {
            return false;
        }
    }

    public int LaySLMonTheoMaDon(int madondat, int mamon){
        int soluong = 0;
        String query = "SELECT * FROM " +CreateDatabase.TBL_CHITIETDONHANG+ " WHERE " +CreateDatabase.TBL_CHITIETDONHANG_MAMON+
                " = " +mamon+ " AND " +CreateDatabase.TBL_CHITIETDONHANG_MADON+ " = "+madondat;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            soluong = cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_CHITIETDONHANG_SOLUONGDAT));
            cursor.moveToNext();
        }
        return soluong;
    }

    public boolean CapNhatSL(ChitietdonhangDTO chitietdonhangDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_CHITIETDONHANG_SOLUONGDAT, chitietdonhangDTO.getSOLUONGDAT());

        long ktra = database.update(CreateDatabase.TBL_CHITIETDONHANG,contentValues,CreateDatabase.TBL_CHITIETDONHANG_MADON+ " = "
                +chitietdonhangDTO.getMADON()+ " AND " +CreateDatabase.TBL_CHITIETDONHANG_MAMON+ " = "
                +chitietdonhangDTO.getMAMON(),null);
        if(ktra !=0){
            return true;
        }else {
            return false;
        }
    }

    public boolean ThemChiTietDonHang(ChitietdonhangDTO chitietdonhangDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_CHITIETDONHANG_SOLUONGDAT,chitietdonhangDTO.getSOLUONGDAT());
        contentValues.put(CreateDatabase.TBL_CHITIETDONHANG_MADON,chitietdonhangDTO.getMADON());
        contentValues.put(CreateDatabase.TBL_CHITIETDONHANG_MAMON,chitietdonhangDTO.getMAMON());

        long ktra = database.insert(CreateDatabase.TBL_CHITIETDONHANG,null,contentValues);
        if(ktra !=0){
            return true;
        }else {
            return false;
        }
    }
}
