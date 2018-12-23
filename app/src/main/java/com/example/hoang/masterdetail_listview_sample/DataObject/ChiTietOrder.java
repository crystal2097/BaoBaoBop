package com.example.hoang.masterdetail_listview_sample.DataObject;

public class ChiTietOrder {


    private String TenMon;
    private String IMGMon;
    private int SoLuong;
    private int MaSP;

    public ChiTietOrder() {
    }

    public ChiTietOrder(String tenMon, String IMGMon, int soLuong, int maSP) {
        TenMon = tenMon;
        this.IMGMon = IMGMon;
        SoLuong = soLuong;
        MaSP = maSP;
    }

    public String getTenMon() {
        return TenMon;
    }

    public void setTenMon(String tenMon) {
        TenMon = tenMon;
    }

    public String getIMGMon() {
        return IMGMon;
    }

    public void setIMGMon(String IMGMon) {
        this.IMGMon = IMGMon;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
    }

    public int getMaSP() {
        return MaSP;
    }

    public void setMaSP(int maSP) {
        MaSP = maSP;
    }

}
