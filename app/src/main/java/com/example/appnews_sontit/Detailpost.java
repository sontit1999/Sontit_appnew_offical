package com.example.appnews_sontit;

import android.annotation.SuppressLint;
import android.content.Intent;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class Detailpost extends AppCompatActivity {
    Toolbar toolbar;
    WebView webView;
    String link;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailpost);
        anhxa();
        Intent intent = getIntent();
        link = intent.getStringExtra("link");
        webView.post(new Runnable() {
            @Override
            public void run() {
                setupwwebview();
            }
        });
        Actionbar();
        //setupwwebview();
    }
    private void anhxa()
    {
        progressBar = findViewById(R.id.progressdetail);
        webView = (WebView) findViewById(R.id.webview);
        toolbar = (Toolbar) findViewById(R.id.toolbardetailpost);
    }
    // setup webview
    private  void setupwwebview()
    {

        webView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setAppCacheEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webSettings.setDomStorageEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setDisplayZoomControls(true);
        webSettings.setSaveFormData(true);
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(link);
        webView.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
    // set up toolbar
    private void Actionbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(webView.canGoBack()){
                    webView.goBack();
                }else {
                    finish();
                }

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
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }
    // sự kiện click menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menu_goto:
                if(webView.canGoForward()){
                    webView.goForward();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
