package com.example.vncoffee.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.vncoffee.PasswordUtils;

public class CreateDatabase extends SQLiteOpenHelper {

    public static String TBL_TAIKHOAN = "TAIKHOAN";
    public static String TBL_THUCDON = "THUCDON";
    public static String TBL_LOAIMON = "LOAIMON";
    public static String TBL_BAN = "BAN";
    public static String TBL_DONHANG = "DONHANG";
    public static String TBL_CHITIETDONHANG = "CHITIETDONHANG";
    public static String TBL_QUYEN = "QUYEN";

    //Bảng tài khoản
    public static String TBL_TAIKHOAN_MATK = "MATK";
    public static String TBL_TAIKHOAN_HOTEN = "HOTEN";
    public static String TBL_TAIKHOAN_TENDN = "TENDN";
    public static String TBL_TAIKHOAN_MATKHAU = "MATKHAU";
    public static String TBL_TAIKHOAN_EMAIL = "EMAIL";
    public static String TBL_TAIKHOAN_SDT = "SDT";
    public static String TBL_TAIKHOAN_GIOITINH = "GIOITINH";
    public static String TBL_TAIKHOAN_NGAYSINH = "NGAYSINH";
    public static String TBL_TAIKHOAN_MAQUYEN= "MAQUYEN";

    //Bảng quyền
    public static String TBL_QUYEN_MAQUYEN = "MAQUYEN";
    public static String TBL_QUYEN_TENQUYEN = "TENQUYEN";

    //Bảng thực đơn
    public static String TBL_THUCDON_MAMON = "MAMON";
    public static String TBL_THUCDON_TENMON = "TENMON";
    public static String TBL_THUCDON_GIATIEN = "GIATIEN";
    public static String TBL_THUCDON_SOLUONG = "SOLUONG";
    public static String TBL_THUCDON_ANH = "ANH";
    public static String TBL_THUCDON_MALOAI = "MALOAI";

    //Bảng loại món
    public static String TBL_LOAIMON_MALOAI = "MALOAI";
    public static String TBL_LOAIMON_TENLOAI = "TENLOAI";
    public static String TBL_LOAIMON_ANH = "ANH";

    //Bảng bàn
    public static String TBL_BAN_MABAN = "MABAN";
    public static String TBL_BAN_TENBAN = "TENBAN";
    public static String TBL_BAN_TINHTRANG = "TINHTRANG";

    //Bảng đơn hàng
    public static String TBL_DONHANG_MADON = "MADON";
    public static String TBL_DONHANG_MATK = "MATK";
    public static String TBL_DONHANG_NGAYTAO = "NGAYTAO";
    public static String TBL_DONHANG_TINHTRANG = "TINHTRANG";
    public static String TBL_DONHANG_TONGTIEN = "TONGTIEN";
    public static String TBL_DONHANG_MABAN = "MABAN";

    //Bảng chi tiết đơn đặt
    public static String TBL_CHITIETDONHANG_MADON = "MADON";
    public static String TBL_CHITIETDONHANG_MAMON = "MAMON";
    public static String TBL_CHITIETDONHANG_SOLUONGDAT = "SOLUONGDAT";

    private Context context;


    public CreateDatabase(Context context) {
        super(context, "VNCoffee", null, 1);
    }

    //thực hiện tạo bảng
    @Override
    public void onCreate(SQLiteDatabase db) {
        String tblTAIKHOAN = "CREATE TABLE " +TBL_TAIKHOAN+ " ( " +TBL_TAIKHOAN_MATK+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +TBL_TAIKHOAN_HOTEN+ " TEXT, " +TBL_TAIKHOAN_TENDN+ " TEXT, " +TBL_TAIKHOAN_MATKHAU+ " TEXT, " +TBL_TAIKHOAN_EMAIL+ " TEXT, "
                +TBL_TAIKHOAN_SDT+ " TEXT, " +TBL_TAIKHOAN_GIOITINH+ " TEXT, " +TBL_TAIKHOAN_NGAYSINH+ " TEXT , "+TBL_TAIKHOAN_MAQUYEN+" INTEGER)";

        String tblQUYEN = "CREATE TABLE " +TBL_QUYEN+ " ( " +TBL_QUYEN_MAQUYEN+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +TBL_QUYEN_TENQUYEN+ " TEXT)" ;

        String tblBAN = "CREATE TABLE " +TBL_BAN+ " ( " +TBL_BAN_MABAN+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +TBL_BAN_TENBAN+ " TEXT, " +TBL_BAN_TINHTRANG+ " TEXT )";

        String tblTHUCDON = "CREATE TABLE " +TBL_THUCDON+ " ( " +TBL_THUCDON_MAMON+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +TBL_THUCDON_TENMON+ " TEXT, " +TBL_THUCDON_GIATIEN+ " TEXT, " +TBL_THUCDON_SOLUONG+ " INTEGER, "
                +TBL_THUCDON_ANH+ " BLOB, "+TBL_THUCDON_MALOAI+ " INTEGER )";

        String tblLOAIMON = "CREATE TABLE " +TBL_LOAIMON+ " ( " +TBL_LOAIMON_MALOAI+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +TBL_LOAIMON_ANH+ " BLOB, " +TBL_LOAIMON_TENLOAI+ " TEXT)" ;

        String tblDONHANG = "CREATE TABLE " +TBL_DONHANG+ " ( " +TBL_DONHANG_MADON+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +TBL_DONHANG_MABAN+ " INTEGER, " +TBL_DONHANG_MATK+ " INTEGER, " +TBL_DONHANG_NGAYTAO+ " TEXT, "+TBL_DONHANG_TONGTIEN+" TEXT,"
                +TBL_DONHANG_TINHTRANG+ " TEXT )" ;

        String tblCHITIETDONHANG = " CREATE TABLE " +TBL_CHITIETDONHANG+ " ( " +TBL_CHITIETDONHANG_MADON+ " INTEGER, "
                +TBL_CHITIETDONHANG_MAMON+ " INTEGER, " +TBL_CHITIETDONHANG_SOLUONGDAT+ " INTEGER, "
                + " PRIMARY KEY ( " +TBL_CHITIETDONHANG_MADON+ "," +TBL_CHITIETDONHANG_MAMON+ "))";

        db.execSQL(tblTAIKHOAN);
        db.execSQL(tblQUYEN);
        db.execSQL(tblBAN);
        db.execSQL(tblTHUCDON);
        db.execSQL(tblLOAIMON);
        db.execSQL(tblDONHANG);
        db.execSQL(tblCHITIETDONHANG);


        //Chèn bản ghi đầu tiên
        ContentValues quanlyValues = new ContentValues();
        quanlyValues.put(TBL_QUYEN_TENQUYEN,"Quản lý");
        db.insert(TBL_QUYEN,null,quanlyValues);

        ContentValues nhanvienValues = new ContentValues();
        nhanvienValues.put(TBL_QUYEN_TENQUYEN,"Nhân viên");
        db.insert(TBL_QUYEN,null,nhanvienValues);

        ContentValues adminvalues = new ContentValues();
        adminvalues.put(TBL_TAIKHOAN_MATK,"1");
        adminvalues.put(TBL_TAIKHOAN_HOTEN,"admin");
        adminvalues.put(TBL_TAIKHOAN_TENDN,"admin");
        adminvalues.put(TBL_TAIKHOAN_MATKHAU,PasswordUtils.encrypt("abc123456"));
        adminvalues.put(TBL_TAIKHOAN_EMAIL,"admin@gmail.com");
        adminvalues.put(TBL_TAIKHOAN_SDT,"0947852136");
        adminvalues.put(TBL_TAIKHOAN_GIOITINH,"Nam");
        adminvalues.put(TBL_TAIKHOAN_NGAYSINH,"20/10/2000");
        adminvalues.put(TBL_TAIKHOAN_MAQUYEN,"1");
        db.insert(TBL_TAIKHOAN,null,adminvalues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    //mở kết nối csdl
    public SQLiteDatabase open(){
        return this.getWritableDatabase();
    }
}
