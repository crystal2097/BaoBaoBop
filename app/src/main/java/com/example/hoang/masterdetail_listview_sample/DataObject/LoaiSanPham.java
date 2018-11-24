package com.example.hoang.masterdetail_listview_sample.DataObject;

public class LoaiSanPham {

    public int getId() {
        return id;
    }

    public String getTenloai() {
        return Tenloai;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTenloai(String tenloai) {
       this.Tenloai = tenloai;
    }

    private int id;

    private String Tenloai;


}
