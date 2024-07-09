package com.example.vncoffee.Activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.example.vncoffee.DAO.ThucdonDAO;
import com.example.vncoffee.DTO.ThucdonDTO;
import com.example.vncoffee.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

public class AddMenuActivity extends AppCompatActivity implements View.OnClickListener{
    Button BTN_addmenu_ThemMon;
    ImageView IMG_addmenu_ThemHinh, IMG_addmenu_back;
    TextView TXT_addmenu_title;
    TextInputLayout TXTL_addmenu_TenMon,TXTL_addmenu_GiaTien,TXTL_addmenu_LoaiMon,TXTL_addmenu_SoLuong;
    ThucdonDAO thucdonDAO;
    String tenloai, sTenMon,sGiaTien;
    Bitmap bitmapold;
    int maloai,sSoLuong;
    int mamon = 0;

    ActivityResultLauncher<Intent> resultLauncherOpenIMG = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK && result.getData() != null){
                        Uri uri = result.getData().getData();
                        try{
                            InputStream inputStream = getContentResolver().openInputStream(uri);
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            IMG_addmenu_ThemHinh.setImageBitmap(bitmap);
                        }catch (FileNotFoundException e){
                            e.printStackTrace();
                        }
                    }
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addmenu_layout);

        //region Lấy đối tượng view
        IMG_addmenu_ThemHinh = (ImageView)findViewById(R.id.img_addmenu_ThemHinh);
        IMG_addmenu_ThemHinh.setTag(R.drawable.addattachicon);
        IMG_addmenu_back = (ImageView)findViewById(R.id.img_addmenu_back);
        TXTL_addmenu_TenMon = (TextInputLayout)findViewById(R.id.txtl_addmenu_TenMon);
        TXTL_addmenu_GiaTien = (TextInputLayout)findViewById(R.id.txtl_addmenu_GiaTien);
        TXTL_addmenu_LoaiMon = (TextInputLayout)findViewById(R.id.txtl_addmenu_LoaiMon);
        TXTL_addmenu_SoLuong= (TextInputLayout) findViewById((R.id.txtl_addmenu_SoLuong));
        BTN_addmenu_ThemMon = (Button)findViewById(R.id.btn_addmenu_ThemMon);
        TXT_addmenu_title = (TextView)findViewById(R.id.txt_addmenu_title);

        //endregion

        Intent intent = getIntent();
        maloai = intent.getIntExtra("maLoai",-1);
        tenloai = intent.getStringExtra("tenLoai");
        thucdonDAO = new ThucdonDAO(this);  //khởi tạo đối tượng dao kết nối csdl
        TXTL_addmenu_LoaiMon.getEditText().setText(tenloai);

        BitmapDrawable olddrawable = (BitmapDrawable)IMG_addmenu_ThemHinh.getDrawable();
        bitmapold = olddrawable.getBitmap();

        //region Hiển thị trang sửa nếu được chọn từ context menu sửa
        mamon = getIntent().getIntExtra("mamon",0);
        if(mamon != 0){
            TXT_addmenu_title.setText("Sửa thực đơn");
            ThucdonDTO thucdonDTO = thucdonDAO.LayMonTheoMa(mamon);

            TXTL_addmenu_TenMon.getEditText().setText(thucdonDTO.getTENMON());
            TXTL_addmenu_GiaTien.getEditText().setText(thucdonDTO.getGIATIEN());
            TXTL_addmenu_SoLuong.getEditText().setText(String.valueOf(thucdonDTO.getSOLUONG()));

            String menuimage = thucdonDTO.getANH();
            Bitmap bitmap = BitmapFactory.decodeFile(menuimage);
            IMG_addmenu_ThemHinh.setImageBitmap(bitmap);


            BTN_addmenu_ThemMon.setText("Sửa món");
        }

        //endregion

        IMG_addmenu_ThemHinh.setOnClickListener(this);
        BTN_addmenu_ThemMon.setOnClickListener(this);
        IMG_addmenu_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        boolean ktra;
        String chucnang;
        switch (id){
            case R.id.img_addmenu_ThemHinh:
                Intent iGetIMG = new Intent();
                iGetIMG.setType("image/*");
                iGetIMG.setAction(Intent.ACTION_GET_CONTENT);
                resultLauncherOpenIMG.launch(Intent.createChooser(iGetIMG,getResources().getString(R.string.choseimg)));
                break;

            case R.id.img_addmenu_back:
                finish();
                break;

            case R.id.btn_addmenu_ThemMon:
                //ktra validation
                if(!validateImage() | !validateName() | !validatePrice() | !validateAmount()){
                    return;
                }

                sTenMon = TXTL_addmenu_TenMon.getEditText().getText().toString();
                sGiaTien = TXTL_addmenu_GiaTien.getEditText().getText().toString();
                sSoLuong = Integer.parseInt(TXTL_addmenu_SoLuong.getEditText().getText().toString());

                ThucdonDTO thucdonDTO = new ThucdonDTO();
                thucdonDTO.setMALOAI(maloai);
                thucdonDTO.setTENMON(sTenMon);
                thucdonDTO.setGIATIEN(sGiaTien);
                thucdonDTO.setSOLUONG(sSoLuong);
                thucdonDTO.setANH(saveImageToInternalStorage(((BitmapDrawable) IMG_addmenu_ThemHinh.getDrawable()).getBitmap()));
                if(mamon!= 0){
                    ktra = thucdonDAO.SuaMon(thucdonDTO,mamon);
                    chucnang = "suamon";
                }else {
                    ktra = thucdonDAO.ThemMon(thucdonDTO);
                    chucnang = "themmon";
                }

                //Thêm, sửa món dựa theo obj loaimonDTO
                Intent intent = new Intent();
                intent.putExtra("ktra",ktra);
                intent.putExtra("chucnang",chucnang);
                setResult(RESULT_OK,intent);
                finish();

                break;
        }
    }

    //Chuyển ảnh bitmap, đưa vào thư mục và gọi đường dẫn
    private String saveImageToInternalStorage(Bitmap bitmap) {
        File directory = getFilesDir();
        String fileName = "IMG_" + System.currentTimeMillis() + ".png";
        File file = new File(directory, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }

    //kiểm tra nhập
    private boolean validateImage(){
        BitmapDrawable drawable = (BitmapDrawable)IMG_addmenu_ThemHinh.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        if(bitmap == bitmapold){
            Toast.makeText(getApplicationContext(),"Xin chọn hình ảnh",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }

    private boolean validateName(){
        String val = TXTL_addmenu_TenMon.getEditText().getText().toString().trim();
        ThucdonDAO thucdonDAO = new ThucdonDAO(getApplicationContext());
        ThucdonDTO thucdonDTO = thucdonDAO.LayMonTheoMa(mamon);
        if(val.isEmpty()){
            TXTL_addmenu_TenMon.setError(getResources().getString(R.string.not_empty));
            return false;
        } else if (thucdonDAO != null && !val.equals(thucdonDTO.getTENMON()) && thucdonDAO.KiemTraTenMon(val)) {
            TXTL_addmenu_TenMon.setError("Tên món đã tồn tại!");
            return false;
        } else {
            TXTL_addmenu_TenMon.setError(null);
            TXTL_addmenu_TenMon.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePrice(){
        String val = TXTL_addmenu_GiaTien.getEditText().getText().toString().trim();
        if(val.isEmpty()){
            TXTL_addmenu_GiaTien.setError(getResources().getString(R.string.not_empty));
            return false;
        }else if(!val.matches(("\\d+(?:\\.\\d+)?"))){
            TXTL_addmenu_GiaTien.setError("Giá tiền không hợp lệ");
            return false;
        }else {
            TXTL_addmenu_GiaTien.setError(null);
            TXTL_addmenu_GiaTien.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateAmount(){
        String val = TXTL_addmenu_SoLuong.getEditText().getText().toString().trim();
        if(val.isEmpty()){
            TXTL_addmenu_SoLuong.setError(getResources().getString(R.string.not_empty));
            return false;
        }else if(!val.matches(("\\d+"))){
            TXTL_addmenu_SoLuong.setError("Số lượng không hợp lệ");
            return false;
        }else {
            TXTL_addmenu_SoLuong.setError(null);
            TXTL_addmenu_SoLuong.setErrorEnabled(false);
            return true;
        }
    }
}
