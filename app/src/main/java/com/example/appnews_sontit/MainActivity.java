package com.example.appnews_sontit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appnews_sontit.fragment.FragmnetContent;
import com.example.appnews_sontit.unity.Server;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager;
    LinearLayout layout;
    FrameLayout frameLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhxa();
        Actionbar();
        sukiendrawer();
        loadFragment(new FragmnetContent(),"content");
    }
    // ánh xạ
    private void anhxa() {
        fragmentManager = getSupportFragmentManager();
        layout =(LinearLayout) findViewById(R.id.activity) ;
        frameLayout = (FrameLayout) findViewById(R.id.framelayout) ;
        toolbar = (Toolbar) findViewById(R.id.toolbarmanhinhchinh);
        navigationView = (NavigationView) findViewById(R.id.navigationviewmanhinhchinh);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
    }
    // set up toolbar
    private void Actionbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        toolbar.setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
    // tạo menu
    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (menu instanceof MenuBuilder) {
            ((MenuBuilder) menu).setOptionalIconsVisible(true);
        }
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    // sự kiện click menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menuseen:
                startActivity(new Intent(MainActivity.this,Seenpost.class));
                break;
            case R.id.menuheart:
                Toast.makeText(this, "Bạn chọn tin đã lưu", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuinfor:
                Toast.makeText(this, "Bạn chọn thông tin app", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menurefresh:
                Toast.makeText(this, "Đã refresh !", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
    // sự kiện click vào item drawler
    private void sukiendrawer(){
       // setBackground(MainActivity.this,navigationView,R.drawable.girl);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                drawerLayout.closeDrawers();
                // Add code here to update the UI based on the item selected
                // For example, swap UI fragments here
                Fragment fragment = new Fragment();
                switch (menuItem.getItemId())
                {
                    case R.id.menunew:
                        toolbar.setTitle("Tin mới");
                        Server.link= "https://baomoi.com/tin-moi.epi";
                        fragment = new FragmnetContent();
                        loadFragment(fragment,"content");
                        break;
                    case R.id.menusocial:
                        toolbar.setTitle("Xã hội");
                        Server.link= "https://baomoi.com/xa-hoi.epi";
                        fragment = new FragmnetContent();
                        loadFragment(fragment,"content");
                        break;
                    case R.id.menuearth:
                        toolbar.setTitle("Thế giới");
                        Server.link= "https://baomoi.com/the-gioi.epi";
                        fragment = new FragmnetContent();
                        loadFragment(fragment,"content");
                        break;
                    case R.id.menueconomic:
                        toolbar.setTitle("Kinh tế");
                        Server.link= "https://baomoi.com/kinh-te.epi";
                        fragment = new FragmnetContent();
                        loadFragment(fragment,"content");
                        break;
                    case R.id.menuscience:
                        toolbar.setTitle("Khoa học");
                        Server.link= "https://baomoi.com/khoa-hoc.epi";
                        fragment = new FragmnetContent();
                        loadFragment(fragment,"content");
                        break;
                    case R.id.menucultural:
                        toolbar.setTitle("Văn hóa");
                        Server.link= "https://baomoi.com/van-hoa.epi";
                        fragment = new FragmnetContent();
                        loadFragment(fragment,"content");
                        break;
                    case R.id.menusport:
                        toolbar.setTitle("Thể thao");
                        Server.link= "https://baomoi.com/the-thao.epi";
                        fragment = new FragmnetContent();
                        loadFragment(fragment,"content");
                        break;
                    case R.id.menuentertainment:
                        toolbar.setTitle("Giải trí");
                        Server.link= "https://baomoi.com/giai-tri.epi";
                        fragment = new FragmnetContent();
                        loadFragment(fragment,"content");
                        break;
                    case R.id.menurole:
                        toolbar.setTitle("Pháp luật");
                        Server.link= "https://baomoi.com/phap-luat.epi";
                        fragment = new FragmnetContent();
                        loadFragment(fragment,"content");
                        break;
                    case R.id.menueducatiom:
                        toolbar.setTitle("Giáo dục");
                        Server.link= "https://baomoi.com/giao-duc.epi";
                        fragment = new FragmnetContent();
                        loadFragment(fragment,"content");
                        break;
                    case R.id.menuhealthy:
                        toolbar.setTitle("Sức khỏe");
                        Server.link= "https://baomoi.com/doi-song.epi";
                        fragment = new FragmnetContent();
                        loadFragment(fragment,"content");
                        break;
                    case R.id.menuhouse:
                        toolbar.setTitle("Nhà đất");
                        Server.link= "https://baomoi.com/nha-dat.epi";
                        fragment = new FragmnetContent();
                        loadFragment(fragment,"content");
                        break;
                    case R.id.menucar:
                        toolbar.setTitle("Xe cộ");
                        Server.link= "https://baomoi.com/xe-co.epi";
                        fragment = new FragmnetContent();
                        loadFragment(fragment,"content");
                        break;
                }
                return true;
            }
        });
    }
    // hàm setbackround cho view
    public void setBackground(Context context, View view, int drawableId){
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawableId);
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(context.getResources(), bitmap);
        view.setBackground(bitmapDrawable);
    }
    // hàm load fragmnet
    public void loadFragment(Fragment fragment,String key){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout,fragment,key);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(fragmentManager.getBackStackEntryCount() == 0){
            finish();
        }
    }
}
