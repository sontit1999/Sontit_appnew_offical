package com.example.appnews_sontit;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.appnews_sontit.fragment.FragmnetContent;
import com.example.appnews_sontit.unity.Config;
import com.example.appnews_sontit.unity.Database;
import com.example.appnews_sontit.unity.Post;
import com.example.appnews_sontit.unity.Server;

public class MainActivity extends AppCompatActivity {
    static Database database;
    FragmentManager fragmentManager;
    LinearLayout layout;
    public static FrameLayout frameLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getorientionscreen();
        anhxa();
        Actionbar();
        setupdatabse();
        sukiendrawer();
        loadFragment(new FragmnetContent(),"content");
    }
    // ánh xạ
    private void anhxa() {
        database = new Database(this,"tintuc",null,1);
        fragmentManager = getSupportFragmentManager();
        layout =(LinearLayout) findViewById(R.id.activity) ;
        frameLayout = (FrameLayout) findViewById(R.id.framelayout) ;
        toolbar = (Toolbar) findViewById(R.id.toolbarmanhinhchinh);
        navigationView = (NavigationView) findViewById(R.id.navigationviewmanhinhchinh);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        // ghi đè icon option menu
        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(),
                R.drawable.ic_more_vert_black_24dp);
        toolbar.setOverflowIcon(drawable);
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
                startActivity(new Intent(MainActivity.this,Savepost.class));
                break;
            case R.id.menubright:
                Toast.makeText(this, "Chế độ ban đêm chưa phát triển hi ^^", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuinfor:
                showDialoginfor();
                break;
            case R.id.menurefresh:
                FragmnetContent fragmnetContent = (FragmnetContent) getSupportFragmentManager().findFragmentByTag("content");
                fragmnetContent.refresh();
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
                        Server.link= "https://baomoi.com/tin-moi/trang1.epi";
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
                    case R.id.menugirl:
                        final Dialog dialog = new Dialog(MainActivity.this);
                        dialog.setContentView(R.layout.dialog_login);
                        dialog.show();
                        Button btnlogin = dialog.findViewById(R.id.buttonlogin);
                        final EditText edtacc = dialog.findViewById(R.id.edittextacc);
                        final EditText edtpass = dialog.findViewById(R.id.edittextpass);
                        btnlogin.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(edtacc.getText().toString().equals("son") && edtpass.getText().toString().equals("123")){
                                    Intent intent = new Intent(MainActivity.this, Mrcong.class);
                                    startActivity(intent);
                                    dialog.dismiss();
                                }else{
                                    Toast.makeText(MainActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            }
                        });

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
        fragmentTransaction.addToBackStack(key);
        fragmentTransaction.replace(R.id.framelayout,fragment,key);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
            finish();
    }
    // show dialog
    public void showDialoginfor(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_inforapp);
        dialog.show();
    }
    // get hướng màn hình
    public void getorientionscreen(){
        Display display = ((WindowManager)getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        Config.orien = display.getOrientation();
    }
    /// thêm 1 tin vào bảng tinđaxem
    public static void addPostseen(Post post,String table){
       // Post post1 = new Post("https://photo-1-baomoi.zadn.vn/w700_r16x9_sm/2019_06_25_119_31223259/7e4041533d13d44d8d02.jpg","https://baomoi.com/","sontit dz ","dantri","06-10-1999");
        database.QueryData("INSERT INTO " + table + "  VALUES('" + post.getLinkthumbail()+ "','" + post.getLinkpost() + "','" + post.getTittle().replace("'","") + "','" + post.getFromnew() + "','" + post.getTimepost() + "')");
       // Log.d("query",("INSERT INTO tindaxem  VALUES(null,'" + post1.getLinkthumbail()+ "','" + post1.getLinkpost() + "','" + post1.getTittle().replace(",","") + "','" + post1.getFromnew() + "','" + post1.getTimepost() + "')").replace("'",""));
    }
    /// setupdatabase+
    public void setupdatabse(){
        database = new Database(this,"tintuc",null,1);
        database.QueryData("CREATE TABLE IF NOT EXISTS Seenpost(linkanh varchar(200), linkpost varchar(200), title varchar(200), fromnew varchar(200), time varchar(200))");
        database.QueryData("CREATE TABLE IF NOT EXISTS Savepost(linkanh varchar(200), linkpost varchar(200), title varchar(200), fromnew varchar(200), time varchar(200))");
    }
    // xóa all recorde in table
    public static void removeallPost(String table){
        database.QueryData("DELETE FROM "+table);
    }
    // hàm remove 1 post trong table
    public static void removePost(String linkpost,String table){
        database.QueryData("DELETE FROM " + table + " WHERE linkpost = '" + linkpost + "'");
    }
}
