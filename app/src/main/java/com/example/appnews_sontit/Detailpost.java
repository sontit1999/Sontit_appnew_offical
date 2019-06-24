package com.example.appnews_sontit;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.text.TextPaint;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.appnews_sontit.adapter.PostAdapter;

public class Detailpost extends AppCompatActivity {
    Toolbar toolbar;
    WebView webView;
    String link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailpost);
        anhxa();
        Actionbar();
        Intent intent = getIntent();
        link = intent.getStringExtra("link");
        setupwwebview();

    }
    private void anhxa()
    {
        webView = (WebView) findViewById(R.id.webview);
        toolbar = (Toolbar) findViewById(R.id.toolbardetailpost);
    }
    // setup webview
    private  void setupwwebview()
    {
        webView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(link);
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
    /*
    //tạo menu
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
                startActivity(new Intent(Detailpost.this,Seenpost.class));
                break;
            case R.id.menuheart:
                Toast.makeText(this, "Bạn chọn tin đã lưu", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuinfor:
                Toast.makeText(this, "Bạn chọn thông tin app", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menurefresh:
                Toast.makeText(this, "Đã refresh", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
    */

}
