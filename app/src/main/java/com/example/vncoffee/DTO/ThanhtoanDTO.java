package com.example.vncoffee.DTO;

public class ThanhtoanDTO {
    String TENMON;
    int SOLUONGDAT, GIATIEN, MAMON, MADON;
    byte[] ANH;

    public int getMAMON() {
        return MAMON;
    }

    public void setMAMON(int MAMON) {
        this.MAMON = MAMON;
    }

    public int getMADON() {
        return MADON;
    }

    public void setMADON(int MADON) {
        this.MADON = MADON;
    }

    public byte[] getANH() {
        return ANH;
    }

    public void setANH(byte[] ANH) {
        this.ANH = ANH;
    }

    public int getGIATIEN() {
        return GIATIEN;
    }

    public void setGIATIEN(int GIATIEN) {
        this.GIATIEN = GIATIEN;
    }

    public int getSOLUONGDAT() {
        return SOLUONGDAT;
    }

    public void setSOLUONGDAT(int SOLUONGDAT) {
        this.SOLUONGDAT = SOLUONGDAT;
    }

    public String getTENMON() {
        return TENMON;
    }

    public void setTENMON(String TENMON) {
        this.TENMON = TENMON;
    }
}
