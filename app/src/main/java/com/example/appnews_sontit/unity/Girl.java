package com.example.appnews_sontit.unity;

public class Girl {
    String linkgirl;
    String linkanh;
    String gia;
    String diachi;
    String tittle;
    String ngaydang;

    public String getNgaydang() {
        return ngaydang;
    }

    public void setNgaydang(String ngaydang) {
        this.ngaydang = ngaydang;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public Girl(){

    };

    public Girl(String linkgirl, String linkanh, String gia, String diachi, String tittle,String ngaydang) {
        this.linkgirl = linkgirl;
        this.linkanh = linkanh;
        this.gia = gia;
        this.diachi = diachi;
        this.tittle = tittle;
        this.ngaydang = ngaydang;
    }

    public String getLinkgirl() {
        return linkgirl;
    }

    public void setLinkgirl(String linkgirl) {
        this.linkgirl = linkgirl;
    }

    public String getLinkanh() {
        return linkanh;
    }

    public void setLinkanh(String linkanh) {
        this.linkanh = linkanh;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }
}
