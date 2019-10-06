package com.example.appnews_sontit;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
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
import android.widget.Toast;

import com.example.appnews_sontit.callback.GirlCallback;
import com.example.appnews_sontit.fragment.Fragment_Idol;
import com.example.appnews_sontit.fragment.Fragment_girl;
import com.example.appnews_sontit.unity.Photo;
import com.squareup.picasso.Picasso;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

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
    public void onImageGirlClick(Photo photo, List<Photo> list) {
        Log.d("test","Mr cong nhận dc link ảnh girl:" + photo.getImage());
        Log.d("test","Mr cong nhận dc " + list.size() + "item");
        showDialog(photo,list);
    }
    private void showDialog(Photo photo,List<Photo> list){
        final Dialog dialog = new Dialog(this,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.dialog_detail_idol);
        final ImageView imgIdol = dialog.findViewById(R.id.iv_idol);
        ImageView iv_back = dialog.findViewById(R.id.iv_backpress);
        ImageView iv_save = dialog.findViewById(R.id.iv_save);




        // set click to dismiss dialog
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        // click save
        iv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImageFromImageview(imgIdol);
            }
        });

        Picasso.get().load(photo.getImage()).into(imgIdol);

        dialog.show();
        PhotoViewAttacher pAttacher;
        pAttacher = new PhotoViewAttacher(imgIdol);
        pAttacher.update();
    }
    public void saveImageFromImageview(ImageView imageView){
        // Get the image from drawable resource as drawable object
        Drawable drawable = imageView.getDrawable();
        // Get the bitmap from drawable object
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();

                /*
                    public static final String insertImage (ContentResolver cr, Bitmap source,
                    String title, String description)

                        Insert an image and create a thumbnail for it.

                    Parameters
                        cr : The content resolver to use
                        source : The stream to use for the image
                        title : The name of the image
                        description : The description of the image

                    Returns
                        The URL to the newly created image, or null if the image
                        failed to be stored for any reason.
                */

        // Save image to gallery
        String savedImageURL = MediaStore.Images.Media.insertImage(
                getContentResolver(),
                bitmap,
                "Bird",
                "Image of bird"
        );

        // Parse the gallery image url to uri
//        Uri savedImageURI = Uri.parse(savedImageURL);

        // Display saved image url to TextView
        Toast.makeText(this, "Image saved to gallery :" + savedImageURL, Toast.LENGTH_SHORT).show();
        Log.d("test","Image saved to gallery :" + savedImageURL);
    }

}
