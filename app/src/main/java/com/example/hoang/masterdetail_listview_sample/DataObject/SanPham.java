package com.example.hoang.masterdetail_listview_sample.DataObject;

import android.os.Parcelable;
import android.os.Parcel;

public class SanPham implements Parcelable {
    private int id;
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
    private int soluong;
    private String tensp;
    private String gia;
    private String imgurl;

    public SanPham(int id, int soluong, String tensp, String gia, String imgurl, boolean addedTocart) {
        this.id = id;
        this.soluong = soluong;
        this.tensp = tensp;
        this.gia = gia;
        this.imgurl = imgurl;
        this.addedTocart = addedTocart;
    }


    public boolean isAddedTocart() {
        return addedTocart;
    }

    public void setAddedTocart(boolean addedTocart) {
        this.addedTocart = addedTocart;
    }

    private boolean addedTocart = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public SanPham() {
        this.soluong = 0;
    }

    protected SanPham(Parcel in) {
        id = in.readInt();
        soluong = in.readInt();
        tensp = in.readString();
        gia = in.readString();
        imgurl = in.readString();
        addedTocart = in.readByte() != 0x00;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(soluong);
        dest.writeString(tensp);
        dest.writeString(gia);
        dest.writeString(imgurl);
        dest.writeByte((byte) (addedTocart ? 0x01 : 0x00));
    }
}