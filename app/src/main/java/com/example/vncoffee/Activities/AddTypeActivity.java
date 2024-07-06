package com.example.vncoffee.Activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.example.vncoffee.R;
import com.example.vncoffee.DAO.LoaimonDAO;
import com.example.vncoffee.DTO.LoaimonDTO;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddTypeActivity extends AppCompatActivity implements View.OnClickListener{
    Button BTN_addtype_TaoLoai;
    ImageView IMG_addtype_back, IMG_addtype_ThemHinh;
    TextView TXT_addtype_title;
    TextInputLayout TXTL_addtype_TenLoai;
    LoaimonDAO loaiMonDAO;
    int maloai = 0;
    Bitmap bitmapold;   //Bitmap dạng ảnh theo ma trận các pixel

    //dùng result launcher
    ActivityResultLauncher<Intent> resultLauncherOpenIMG = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK && result.getData() != null){
                        Uri uri = result.getData().getData();
                        try{
                            InputStream inputStream = getContentResolver().openInputStream(uri);
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            IMG_addtype_ThemHinh.setImageBitmap(bitmap);
                        }catch (FileNotFoundException e){
                            e.printStackTrace();
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addtype_layout);

        loaiMonDAO = new LoaimonDAO(this);  //khởi tạo đối tượng dao kết nối csdl

        //region Lấy đối tượng view
        BTN_addtype_TaoLoai = (Button)findViewById(R.id.btn_addtype_TaoLoai);
        TXTL_addtype_TenLoai = (TextInputLayout)findViewById(R.id.txtl_addtype_TenLoai);
        IMG_addtype_back = (ImageView)findViewById(R.id.img_addtype_back);
        IMG_addtype_ThemHinh = (ImageView)findViewById(R.id.img_addtype_ThemHinh);
        TXT_addtype_title = (TextView)findViewById(R.id.txt_addtype_title);
        //endregion

        BitmapDrawable olddrawable = (BitmapDrawable)IMG_addtype_ThemHinh.getDrawable();
        bitmapold = olddrawable.getBitmap();

        //region Hiển thị trang sửa nếu được chọn từ context menu sửa
        maloai = getIntent().getIntExtra("maloai",0);
        if(maloai != 0){
            TXT_addtype_title.setText(getResources().getString(R.string.edittype));
            LoaimonDTO loaiMonDTO = loaiMonDAO.LayLoaiMonTheoMa(maloai);

            //Hiển thị lại thông tin từ csdl
            TXTL_addtype_TenLoai.getEditText().setText(loaiMonDTO.getTENLOAI());

            byte[] typeimage = loaiMonDTO.getANH();
            Bitmap bitmap = BitmapFactory.decodeByteArray(typeimage,0,typeimage.length);
            IMG_addtype_ThemHinh.setImageBitmap(bitmap);

            BTN_addtype_TaoLoai.setText("Sửa loại");
        }
        //endregion

        IMG_addtype_back.setOnClickListener(this);
        IMG_addtype_ThemHinh.setOnClickListener(this);
        BTN_addtype_TaoLoai.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        int id = v.getId();
        boolean ktra;
        String chucnang;
        switch (id){
            case R.id.img_addtype_back:
                finish();
                break;

            case R.id.img_addtype_ThemHinh:
                Intent iGetIMG = new Intent();
                iGetIMG.setType("image/*"); //lấy những mục chứa hình ảnh
                iGetIMG.setAction(Intent.ACTION_GET_CONTENT);   //lấy mục hiện tại đang chứa hình
                resultLauncherOpenIMG.launch(Intent.createChooser(iGetIMG,"Chọn hình"));    //mở intent chọn hình ảnh
                break;

            case R.id.btn_addtype_TaoLoai:
                if(!validateImage() | !validateName()){
                    return;
                }

                String sTenLoai = TXTL_addtype_TenLoai.getEditText().getText().toString();
                LoaimonDTO loaiMonDTO = new LoaimonDTO();
                loaiMonDTO.setTENLOAI(sTenLoai);
                loaiMonDTO.setANH(imageViewtoByte(IMG_addtype_ThemHinh));
                if(maloai != 0){
                    ktra = loaiMonDAO.SuaLoaiMon(loaiMonDTO,maloai);
                    chucnang = "sualoai";
                }else {
                    ktra = loaiMonDAO.ThemLoaiMon(loaiMonDTO);
                    chucnang = "themloai";
                }

                //Thêm, sửa loại dựa theo obj loaimonDTO
                Intent intent = new Intent();
                intent.putExtra("ktra",ktra);
                intent.putExtra("chucnang",chucnang);
                setResult(RESULT_OK,intent);
                finish();
                break;

        }
    }

    //Chuyển ảnh bitmap về mảng byte lưu vào csdl
    private byte[] imageViewtoByte(ImageView imageView){
        Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    //kiểm tra nhập
    private boolean validateImage(){
        BitmapDrawable drawable = (BitmapDrawable)IMG_addtype_ThemHinh.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        if(bitmap == bitmapold){
            Toast.makeText(getApplicationContext(),"Xin chọn hình ảnh",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }

    private boolean validateName(){
        String val = TXTL_addtype_TenLoai.getEditText().getText().toString().trim();
        LoaimonDAO loaimonDAO = new LoaimonDAO(getApplicationContext());
        if(val.isEmpty()){
            TXTL_addtype_TenLoai.setError(getResources().getString(R.string.not_empty));
            return false;
        } else if (loaimonDAO.KiemTraTenLoai(val)) {
            TXTL_addtype_TenLoai.setError("Tên loại đã tồn tại!");
            return false;
        } else {
            TXTL_addtype_TenLoai.setError(null);
            TXTL_addtype_TenLoai.setErrorEnabled(false);
            return true;
        }
    }
}
