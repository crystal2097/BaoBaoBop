package com.example.hoang.masterdetail_listview_sample.DataObject;

public class Order {
    private int MaOrder;
    private String ThoiGian;
    private String Username;

    public Order(int maOrder, String thoiGian, String username) {
        MaOrder = maOrder;
        ThoiGian = thoiGian;
        Username = username;
    }

    public Order() {
    }

    public int getMaOrder() {
        return MaOrder;
    }

    public void setMaOrder(int maOrder) {
        MaOrder = maOrder;
    }

    public String getThoiGian() {
        return ThoiGian;
    }

    public void setThoiGian(String thoiGian) {
        ThoiGian = thoiGian;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }
}
