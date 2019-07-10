package com.example.appnews_sontit;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appnews_sontit.adapter.AdapterCarousel;
import com.example.appnews_sontit.adapter.ImageAdapter;
import com.example.appnews_sontit.adapter.PostAdapter;
import com.example.appnews_sontit.unity.Config;
import com.example.appnews_sontit.unity.EndlessRecyclerViewScrollListener;
import com.example.appnews_sontit.unity.Idol;
import com.example.appnews_sontit.unity.Photo;
import com.example.appnews_sontit.unity.Server;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fr.rolandl.carousel.Carousel;
import fr.rolandl.carousel.CarouselAdapter;
import fr.rolandl.carousel.CarouselBaseAdapter;
import uk.co.senab.photoview.PhotoViewAttacher;

public class GallerryIdol extends AppCompatActivity {
    int PAGE = 0;
    ImageAdapter adapter;
    RecyclerView recyclerView;
    String link ;
    public ImageView mainimage,ivbackidol;
    List<Photo> photos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallerry_idol);
        Intent intent = getIntent();
        if(intent!=null){
            link = intent.getStringExtra("linkidol");
        }
        anhxa();
        setuprecyclerview();
        getdata(link);

    }

    private void anhxa() {
        ivbackidol = (ImageView) findViewById(R.id.imageviewback);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerviewgalleryidol) ;
        photos = new ArrayList<>();
        mainimage = (ImageView) findViewById(R.id.mainimage);
        mainimage.setVisibility(View.INVISIBLE);
        // zoom
        PhotoViewAttacher pAttacher;
        pAttacher = new PhotoViewAttacher(mainimage);
        pAttacher.update();

        mainimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainimage.setVisibility(View.INVISIBLE);
            }
        });
        ivbackidol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    // get data
   public void getdata(String link){
        if(PAGE<=7){
            PAGE++;
            String duongan = link + PAGE;
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            StringRequest stringRequest = new StringRequest(Request.Method.GET,duongan, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("doc",response.toString());
                    String linkthumbail;
                    Document document = Jsoup.parse(response);
                    Elements items = document.select(".entry p img");
                    for (Element i : items)
                    {
                        linkthumbail = i.attr("src");
                        Photo temp = new Photo(linkthumbail);
                        if(!photos.contains(temp)){
                            photos.add(temp);
                        }
                    }
                    Picasso.get().load(photos.get(0).getImage()).into(mainimage);
                    int curSize = adapter.getItemCount();
                    adapter.notifyItemRangeInserted(curSize, photos.size() - 1);
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(GallerryIdol.this, error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
            );
            requestQueue.add(stringRequest);
        }

   }
    // setup recyclerview
    public void setuprecyclerview(){

        recyclerView.setHasFixedSize(true);
        adapter = new ImageAdapter(GallerryIdol.this,photos);
        // tao layoutmanager
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager layoutManager=LinearLayoutManager.class.cast(recyclerView.getLayoutManager());
                int totalItemCount = layoutManager.getItemCount();
                int lastVisible = layoutManager.findLastVisibleItemPosition();

                boolean endHasBeenReached = lastVisible + 5 >= totalItemCount;
                if (totalItemCount > 0 && endHasBeenReached) {
                             getdata(link);
                }
            }
        });
    }

}
