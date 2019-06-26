package com.example.appnews_sontit;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appnews_sontit.adapter.PostAdapter;
import com.example.appnews_sontit.fragment.FragmnetContent;
import com.example.appnews_sontit.unity.Database;
import com.example.appnews_sontit.unity.Post;
import com.example.appnews_sontit.unity.Server;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class Seenpost extends AppCompatActivity {
    Database database;
    Toolbar toolbar;
    RecyclerView recyclerView;
    ArrayList<Post> arrayList;
    PostAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seenpost);
        anhxa();
        Actionbar();
        setuprecyclerview();
        database.QueryData("CREATE TABLE IF NOT EXISTS tindaxem(id INTEGER PRIMARY KEY AUTOINCREMENT, linkanh varchar(200), linkpost varchar(200), title varchar(200), fromnew varchar(200), time varchar(200))");
        mess();
    }
   // ánh xạ
    private void anhxa() {
        database = new Database(this,"tintuc",null,1);
        arrayList = new ArrayList<>();
        toolbar = (Toolbar) findViewById(R.id.toolbarseenpost);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerviewseenpost);
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
    // setup recyclerview
    public void setuprecyclerview(){
        recyclerView.setHasFixedSize(true);

        adapter = new PostAdapter(Seenpost.this,R.layout.item_post_seen,1,arrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(Seenpost.this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);
        // sự kiện swipe 1 item trong recyclerview
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                Post post = arrayList.get(viewHolder.getAdapterPosition());
                String linkpost = post.getLinkpost();
                MainActivity.removePost(linkpost,"Seenpost");
                arrayList.remove(viewHolder.getAdapterPosition());
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(recyclerView);
    }
    // lấy data
    public void mess() {
        arrayList.clear();
        String linkthumbail, tittle, timeago, from, linkpost;
        Cursor cursor = database.Getdata("SELECT DISTINCT * FROM Seenpost");
        while (cursor.moveToNext()) {
            linkthumbail = cursor.getString(0);
            linkpost = cursor.getString(1);
            tittle = cursor.getString(2);
            from = cursor.getString(3);
            timeago = "" ;//cursor.getString(4);
            Log.d("thanhcong", linkthumbail + linkpost + tittle + from + timeago + "");
            arrayList.add(new Post(linkthumbail,linkpost,tittle,from,timeago));
        }
        Collections.reverse(arrayList);
        adapter.notifyDataSetChanged();
        if (arrayList.size()==0){
            Toast.makeText(this, "Xóa hết lịch sử tin đã xem rồi còn đâu!", Toast.LENGTH_SHORT).show();
        }
    }
    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (menu instanceof MenuBuilder) {
            ((MenuBuilder) menu).setOptionalIconsVisible(true);
        }
        getMenuInflater().inflate(R.menu.menu_seenpost, menu);
        return true;
    }
    // sự kiện click menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menudeleteall:
                MainActivity.removeallPost("Seenpost");
                arrayList.clear();
                adapter.notifyDataSetChanged();
        }
        return super.onOptionsItemSelected(item);
    }
}
