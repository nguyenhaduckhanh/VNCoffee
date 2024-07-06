package com.example.vncoffee.DTO;

public class DonhangDTO {
    int MADON, MATK, MABAN;
    String NGAYTAO, TINHTRANG, TONGTIEN;

    public String getTONGTIEN() {
        return TONGTIEN;
    }

    public void setTONGTIEN(String TONGTIEN) {
        this.TONGTIEN = TONGTIEN;
    }

    public String getTINHTRANG() {
        return TINHTRANG;
    }

    public void setTINHTRANG(String TINHTRANG) {
        this.TINHTRANG = TINHTRANG;
    }

    public String getNGAYTAO() {
        return NGAYTAO;
    }

    public void setNGAYTAO(String NGAYTAO) {
        this.NGAYTAO = NGAYTAO;
    }

    public int getMABAN() {
        return MABAN;
    }

    public void setMABAN(int MABAN) {
        this.MABAN = MABAN;
    }

    public int getMATK() {
        return MATK;
    }

    public void setMATK(int MATK) {
        this.MATK = MATK;
    }

    public int getMADON() {
        return MADON;
    }

    public void setMADON(int MADON) {
        this.MADON = MADON;
    }
}
