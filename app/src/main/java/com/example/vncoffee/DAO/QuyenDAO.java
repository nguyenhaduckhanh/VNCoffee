package com.example.vncoffee.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.vncoffee.Database.CreateDatabase;

public class QuyenDAO {

    SQLiteDatabase database;
    public QuyenDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    //Hàm thêm quyền
    public void ThemQuyen(String tenquyen){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_QUYEN_TENQUYEN,tenquyen);
        database.insert(CreateDatabase.TBL_QUYEN,null,contentValues);
    }

    //Hàm lấy quyền
    public String LayTenQuyenTheoMa(int maquyen){
        String tenquyen ="";
        String query = "SELECT * FROM "+CreateDatabase.TBL_QUYEN+" WHERE "+CreateDatabase.TBL_QUYEN_MAQUYEN+" = "+maquyen;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            tenquyen = cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_QUYEN_TENQUYEN));
            cursor.moveToNext();
        }
        return tenquyen;
    }
}
