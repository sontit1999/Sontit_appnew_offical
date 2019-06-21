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
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
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
    }
    // ánh xạ
    private void anhxa() {
        frameLayout = (FrameLayout) findViewById(R.id.framelayout) ;
        toolbar = (Toolbar) findViewById(R.id.toolbarmanhinhchinh);
        navigationView = (NavigationView) findViewById(R.id.navigationviewmanhinhchinh);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
    }
    // set up toolbar
    private void Actionbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.menu);
        toolbar.setTitle("Tin nóng");
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
                Toast.makeText(this, "Bạn chọn tin đã xem", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuheart:
                Toast.makeText(this, "Bạn chọn tin đã lưu", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuinfor:
                Toast.makeText(this, "Bạn chọn thông tin app", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menurefresh:
                Toast.makeText(this, "bạn chọn refresh", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
    // sự kiện click vào item drawler
    private void sukiendrawer(){
        setBackground(MainActivity.this,navigationView,R.drawable.girl);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                drawerLayout.closeDrawers();
                // Add code here to update the UI based on the item selected
                // For example, swap UI fragments here
                switch (menuItem.getItemId())
                {
                    case R.id.menunew:
                        Toast.makeText(MainActivity.this, "bạn chọn Tin nóng", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menusocial:
                        Toast.makeText(MainActivity.this, "bạn chọn xã hội", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menuearth:
                        Toast.makeText(MainActivity.this, "bạn chọn thế giới", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menueconomic:
                        Toast.makeText(MainActivity.this, "bạn chọn kinh tế", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menuscience:
                        Toast.makeText(MainActivity.this, "bạn chọn khoa học", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menucultural:
                        Toast.makeText(MainActivity.this, "bạn chọn văn hóa", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menusport:
                        Toast.makeText(MainActivity.this, "bạn chọn thể thao", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menuentertainment:
                        Toast.makeText(MainActivity.this, "bạn chọn giải trí", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menurole:
                        Toast.makeText(MainActivity.this, "bạn chọn pháp luật", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menueducatiom:
                        Toast.makeText(MainActivity.this, "bạn chọn giáo dục", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menuhealthy:
                        Toast.makeText(MainActivity.this, "bạn chọn sức khỏe", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menuhouse:
                        Toast.makeText(MainActivity.this, "bạn chọn nhà đất", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menucar:
                        Toast.makeText(MainActivity.this, "bạn chọn oto xe máy", Toast.LENGTH_SHORT).show();
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
}
