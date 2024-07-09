package com.example.vncoffee.DTO;

public class LoaimonDTO {
    int MALOAI;
    String TENLOAI,ANH;

    public String getANH() {
        return ANH;
    }

    public void setANH(String ANH) {
        this.ANH = ANH;
    }

    public String getTENLOAI() {
        return TENLOAI;
    }

    public void setTENLOAI(String TENLOAI) {
        this.TENLOAI = TENLOAI;
    }

    public int getMALOAI() {
        return MALOAI;
    }

    public void setMALOAI(int MALOAI) {
        this.MALOAI = MALOAI;
    }
}
