package com.example.appnews_sontit;

import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.appnews_sontit.callback.GirlCallback;
import com.example.appnews_sontit.fragment.Fragment_Idol;
import com.example.appnews_sontit.fragment.Fragment_girl;
import com.example.appnews_sontit.unity.Photo;
import com.squareup.picasso.Picasso;

import uk.co.senab.photoview.PhotoViewAttacher;

public class Mrcong extends AppCompatActivity implements GirlCallback {
    FragmentManager fragmentManager;
    Toolbar toolbar;
    FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mrcong);
        anhxa();
        Actionbar();
        loadFragment(new Fragment_girl(),"girl");
    }

    private void anhxa() {
        fragmentManager = getSupportFragmentManager();
        frameLayout = (FrameLayout) findViewById(R.id.framelayoutMrcong);
        toolbar = (Toolbar) findViewById(R.id.toolbarMrcong);
    }

    // set up toolbar
    private void Actionbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    // hàm load fragmnet
    public void loadFragment(Fragment fragment,String key){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(key);
        fragmentTransaction.replace(R.id.framelayoutMrcong,fragment,key);
        fragmentTransaction.commit();
    }

    @Override
    public void onGirlclick(String linkidol) {
        Log.d("test","Mr cong nhận dc : " + linkidol);
        Fragment_Idol fragment_idol = new Fragment_Idol(linkidol);
        loadFragment(fragment_idol,"fragment Idol");
    }

    @Override
    public void onImageGirlClick(Photo photo) {
        Log.d("test","Mr cong nhận dc link ảnh girl:" + photo.getImage());
        showDialog(photo);
    }
    private void showDialog(Photo photo){
        Dialog dialog = new Dialog(this,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.dialog_detail_idol);
        ImageView imgIdol = dialog.findViewById(R.id.iv_idol);
        Picasso.get().load(photo.getImage()).into(imgIdol);
        PhotoViewAttacher pAttacher;
        pAttacher = new PhotoViewAttacher(imgIdol);
        pAttacher.update();
        dialog.show();
    }
}
