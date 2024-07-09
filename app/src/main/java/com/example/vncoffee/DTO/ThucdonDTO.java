package com.example.vncoffee.DTO;

public class ThucdonDTO {
    int MAMON, MALOAI,SOLUONG;
    String TENMON, GIATIEN, ANH;

    public String getANH() {
        return ANH;
    }

    public void setANH(String ANH) {
        this.ANH = ANH;
    }

    public String getGIATIEN() {
        return GIATIEN;
    }

    public void setGIATIEN(String GIATIEN) {
        this.GIATIEN = GIATIEN;
    }

    public String getTENMON() {
        return TENMON;
    }

    public void setTENMON(String TENMON) {
        this.TENMON = TENMON;
    }

    public int getSOLUONG() {
        return SOLUONG;
    }

    public void setSOLUONG(int SOLUONG) {
        this.SOLUONG = SOLUONG;
    }

    public int getMALOAI() {
        return MALOAI;
    }

    public void setMALOAI(int MALOAI) {
        this.MALOAI = MALOAI;
    }

    public int getMAMON() {
        return MAMON;
    }

    public void setMAMON(int MAMON) {
        this.MAMON = MAMON;
    }
}
