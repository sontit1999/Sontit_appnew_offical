package com.example.appnews_sontit;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Layout;
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
import com.example.appnews_sontit.unity.Post;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class Savepost extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    ArrayList<Post> arrayList;
    PostAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savepost);
        anhxa();
        Actionbar();
        setuprecyclerview();
        mess();
    }
   // ánh xạ
    private void anhxa() {
        toolbar = (Toolbar) findViewById(R.id.toolbarsavepost);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerviewsavepost);
        arrayList = new ArrayList<>();
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
    // lấy data
    public void mess()
    {
        arrayList.clear();
        String linkthumbail, tittle, timeago, from, linkpost;
        Cursor cursor = MainActivity.database.Getdata("SELECT DISTINCT * FROM Savepost");
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
        if(arrayList.size()==0){
            Toast.makeText(this, "Không có tin nào được lưu. Giữ vào tin để lưu hí hí", Toast.LENGTH_SHORT).show();
        }
    }
    // setup recyclerview
    public void setuprecyclerview(){
        recyclerView.setHasFixedSize(true);

        adapter = new PostAdapter(Savepost.this,R.layout.item_post,2,arrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(Savepost.this,LinearLayoutManager.VERTICAL,false));
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
                 MainActivity.removePost(linkpost,"Savepost");
                 arrayList.remove(viewHolder.getAdapterPosition());
                 adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(recyclerView);
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
                MainActivity.removeallPost("Savepost");
                arrayList.clear();
                adapter.notifyDataSetChanged();
        }
        return super.onOptionsItemSelected(item);
    }
}
