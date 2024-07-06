package com.example.vncoffee.DTO;

public class BananDTO {
    int MABAN;
    String TENBAN;
    boolean DUOCCHON;

    public boolean isDUOCCHON() {
        return DUOCCHON;
    }

    public void setDUOCCHON(boolean DUOCCHON) {
        this.DUOCCHON = DUOCCHON;
    }

    public String getTENBAN() {
        return TENBAN;
    }

    public void setTENBAN(String TENBAN) {
        this.TENBAN = TENBAN;
    }

    public int getMABAN() {
        return MABAN;
    }

    public void setMABAN(int MABAN) {
        this.MABAN = MABAN;
    }
}
