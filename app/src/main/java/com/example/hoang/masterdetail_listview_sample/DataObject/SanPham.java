package com.example.hoang.masterdetail_listview_sample.DataObject;

import android.os.Parcel;
import android.os.Parcelable;

public class SanPham implements Parcelable {

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<SanPham> CREATOR = new Parcelable.Creator<SanPham>() {
        @Override
        public SanPham createFromParcel(Parcel in) {
            return new SanPham(in);
        }

        @Override
        public SanPham[] newArray(int size) {
            return new SanPham[size];
        }
    };


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;
    private int soluong;

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    private String tensp;
    private String gia;
    private String imgurl;
    private String MaLoai;

    public SanPham() {
    }

    public SanPham(int id, int soluong, String tensp, String gia, String imgurl, String maLoai) {
        this.id = id;
        this.soluong = soluong;
        this.tensp = tensp;
        this.gia = gia;
        this.imgurl = imgurl;
        MaLoai = maLoai;
    }
    protected SanPham(Parcel in) {
        id = in.readInt();
        soluong = in.readInt();
        tensp = in.readString();
        gia = in.readString();
        imgurl = in.readString();
        MaLoai = in.readString();
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public String getMaLoai() {
        return MaLoai;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void setMaLoai(String maLoai) {
        MaLoai = maLoai;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(soluong);
        dest.writeString(tensp);
        dest.writeString(gia);
        dest.writeString(imgurl);
        dest.writeString(MaLoai);
    }
}