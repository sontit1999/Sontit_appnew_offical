package com.example.appnews_sontit.callback;

import com.example.appnews_sontit.unity.Photo;

import java.util.List;

public interface GirlCallback {
    void onGirlclick(String linkidol);
    void onImageGirlClick(Photo photo, List<Photo> list);
}
