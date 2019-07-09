package com.example.appnews_sontit.unity;

import java.io.Serializable;

public class Photo implements Serializable {
    private static final long serialVersionUID = 1L;

    public final String image;
    public Photo(String image)
    {
        this.image = image;
    }

    public String getImage() {
        return image;
    }
}
