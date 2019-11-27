package com.example.appnews_sontit;

import android.annotation.SuppressLint;
import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.appnews_sontit.adapter.PostAdapter;
import com.example.appnews_sontit.unity.Database;
import com.example.appnews_sontit.unity.Post;

import java.util.ArrayList;
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
