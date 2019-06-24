package com.example.appnews_sontit;

import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appnews_sontit.adapter.PostAdapter;
import com.example.appnews_sontit.unity.Post;
import com.example.appnews_sontit.unity.Server;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Seenpost extends AppCompatActivity {
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
        mess();
        setuprecyclerview();
    }
   // ánh xạ
    private void anhxa() {
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

    }
    // lấy data
    public void mess()
    {
        String link = "https://baomoi.com/am-nhac.epi";
        arrayList.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Document document = Jsoup.parse(response);
                Elements items = document.select(".story");
                String linkthumbail,tittle,timeago,from,linkpost,timeStamp;
                String[] arr,arrtimenow;
                int hourpost,hournow,minutepost,minutenow;
                for(Element i:items)
                {
                    linkthumbail = i.select(".story__thumb a img").attr("src");
                    tittle = i.select(".story__heading a").text();
                    timeago = i.select(".story__meta .time").attr("datetime");
                    from = i.select(".story__meta .source").text();
                    linkpost = "https://baomoi.com" + i.select(".story__heading a").attr("href");
                    if(!(linkthumbail.length() == 0 || tittle.length() == 0 || timeago.length() == 0 || linkpost.length() ==0))
                    {
                        timeago = timeago.substring(11,19);
                        arr = timeago.split(":");
                        hourpost = Integer.parseInt(arr[0]);
                        minutepost = Integer.parseInt(arr[1]);
                        timeStamp = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
                        arrtimenow = timeStamp.split(":");
                        hournow = Integer.parseInt(arrtimenow[0]);
                        minutenow = Integer.parseInt(arrtimenow[1]);
                        if(hournow == hourpost){
                            timeago = (minutenow - minutepost) + " phút trước" ;
                        }else if(hourpost < hournow){
                            timeago = (hournow - hourpost) + " giờ trước";
                        }
                        Log.d("time","Giờ post = " + hourpost+ ", phút post = " + minutepost + ",Giờ now =  " + hournow + ",minutenow = " + minutenow);
                        arrayList.add(new Post(linkthumbail,linkpost,tittle,from,timeago));
                    }

                }
                adapter.notifyDataSetChanged();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Seenpost.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(stringRequest);
    }
}
