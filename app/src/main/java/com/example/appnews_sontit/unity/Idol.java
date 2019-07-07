package com.example.appnews_sontit.unity;

public class Idol {
    String linkidol;
    String linkthumbail;
    String nameidol;

    public Idol() {
    }

    public Idol(String linkidol, String linkthumbail, String nameidol) {
        this.linkidol = linkidol;
        this.linkthumbail = linkthumbail;
        this.nameidol = nameidol;
    }

    public String getLinkidol() {
        return linkidol;
    }

    public void setLinkidol(String linkidol) {
        this.linkidol = linkidol;
    }

    public String getLinkthumbail() {
        return linkthumbail;
    }

    public void setLinkthumbail(String linkthumbail) {
        this.linkthumbail = linkthumbail;
    }

    public String getNameidol() {
        return nameidol;
    }

    public void setNameidol(String nameidol) {
        this.nameidol = nameidol;
    }
}
