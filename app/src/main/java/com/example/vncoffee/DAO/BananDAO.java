package com.example.vncoffee.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.vncoffee.DTO.BananDTO;
import com.example.vncoffee.DTO.LoaimonDTO;
import com.example.vncoffee.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class BananDAO {
    SQLiteDatabase database;
    public BananDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    //Hàm thêm bàn ăn mới
    public boolean ThemBanAn(String tenban){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_BAN_TENBAN,tenban);
        contentValues.put(CreateDatabase.TBL_BAN_TINHTRANG,"false");

        long ktra = database.insert(CreateDatabase.TBL_BAN,null,contentValues);
        if(ktra != 0){
            return true;
        }else {
            return false;
        }
    }

    //Hàm xóa bàn ăn theo mã
    public boolean XoaBanTheoMa(int maban){
        long ktra =database.delete(CreateDatabase.TBL_BAN,CreateDatabase.TBL_BAN_MABAN+" = "+maban,null);
        if(ktra != 0){
            return true;
        }else {
            return false;
        }
    }

    //Sửa tên bàn
    public boolean CapNhatTenBan(int maban, String tenban){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_BAN_TENBAN,tenban);

        long ktra = database.update(CreateDatabase.TBL_BAN,contentValues,CreateDatabase.TBL_BAN_MABAN+ " = '"+maban+"' ",null);
        if(ktra != 0){
            return true;
        }else {
            return false;
        }
    }

    //Hàm lấy ds các bàn ăn đổ vào gridview
    public List<BananDTO> LayTatCaBanAn(){
        List<BananDTO> banAnDTOList = new ArrayList<BananDTO>();
        String query = "SELECT * FROM " +CreateDatabase.TBL_BAN;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            BananDTO banAnDTO = new BananDTO();
            banAnDTO.setMABAN(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_BAN_MABAN)));
            banAnDTO.setTENBAN(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_BAN_TENBAN)));

            banAnDTOList.add(banAnDTO);
            cursor.moveToNext();
        }
        return banAnDTOList;
    }

    public String LayTinhTrangBanTheoMa(int maban){
        String tinhtrang="";
        String query = "SELECT * FROM "+CreateDatabase.TBL_BAN + " WHERE " +CreateDatabase.TBL_BAN_MABAN+ " = '" +maban+ "' ";
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            tinhtrang = cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_BAN_TINHTRANG));
            cursor.moveToNext();
        }

        return tinhtrang;
    }

    public boolean CapNhatTinhTrangBan(int maban, String tinhtrang){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_BAN_TINHTRANG,tinhtrang);

        long ktra = database.update(CreateDatabase.TBL_BAN,contentValues,CreateDatabase.TBL_BAN_MABAN+ " = '"+maban+"' ",null);
        if(ktra != 0){
            return true;
        }else {
            return false;
        }
    }

    public String LayTenBanTheoMa(int maban){
        String tenban="";
        String query = "SELECT * FROM "+CreateDatabase.TBL_BAN + " WHERE " +CreateDatabase.TBL_BAN_MABAN+ " = '" +maban+ "' ";
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            tenban = cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_BAN_TENBAN));
            cursor.moveToNext();
        }

        return tenban;
    }

    public BananDTO TraVeTenBan(int maban){
        BananDTO bananDTO = new BananDTO();
        String query = "SELECT * FROM "+CreateDatabase.TBL_BAN + " WHERE " +CreateDatabase.TBL_BAN_MABAN+ " = '" +maban+ "' ";
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
           bananDTO.setMABAN(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_BAN_MABAN)));
           bananDTO.setTENBAN(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_BAN_TENBAN)));
           cursor.moveToNext();
        }

        return bananDTO;
    }

    //check trung
    public boolean KiemTraTenBan(String ten) {
        String query = "SELECT * FROM " + CreateDatabase.TBL_BAN + " WHERE " + CreateDatabase.TBL_BAN_TENBAN + " = ?";
        Cursor cursor = database.rawQuery(query, new String[]{ten});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
}
