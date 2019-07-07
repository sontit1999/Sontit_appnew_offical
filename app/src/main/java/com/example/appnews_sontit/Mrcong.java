package com.example.appnews_sontit;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.example.appnews_sontit.fragment.Fragment_girl;

public class Mrcong extends AppCompatActivity {
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
}
