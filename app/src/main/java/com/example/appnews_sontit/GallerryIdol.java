package com.example.appnews_sontit;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import fr.rolandl.carousel.Carousel;
import fr.rolandl.carousel.CarouselAdapter;
import fr.rolandl.carousel.CarouselBaseAdapter;

public class GallerryIdol extends AppCompatActivity {
    String link = "https://mrcong.com/youwu-vol-147-yi-nuo-39-anh/";
    ImageView mainimage;
    List<Photo> photos;
    Carousel carousel;
    CarouselAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallerry_idol);
        Intent intent = getIntent();
        if(intent!=null){
            link = intent.getStringExtra("linkidol");
        }
        anhxa();
        getdata(link);

    }

    private void anhxa() {
        photos = new ArrayList<>();
        mainimage = (ImageView) findViewById(R.id.mainimage);
        mainimage.setVisibility(View.INVISIBLE);
        carousel = (Carousel) findViewById(R.id.carousel);
        /*
        photos.add(new Photo("https://1.bp.blogspot.com/-Y4IdjQSHIV8/XR85D9jObJI/AAAAAAACmj8/N29KU7DzxUUG8R7DR-euAPQ0NwAH1OOdQCLcBGAs/s1600/XIUREN-No.1444-Xiao-Reba-Angela-MrCong.com-001.jpg"));
        photos.add(new Photo("https://1.bp.blogspot.com/-Y4IdjQSHIV8/XR85D9jObJI/AAAAAAACmj8/N29KU7DzxUUG8R7DR-euAPQ0NwAH1OOdQCLcBGAs/s1600/XIUREN-No.1444-Xiao-Reba-Angela-MrCong.com-001.jpg"));
        photos.add(new Photo("https://1.bp.blogspot.com/-Y4IdjQSHIV8/XR85D9jObJI/AAAAAAACmj8/N29KU7DzxUUG8R7DR-euAPQ0NwAH1OOdQCLcBGAs/s1600/XIUREN-No.1444-Xiao-Reba-Angela-MrCong.com-001.jpg"));
        photos.add(new Photo("https://1.bp.blogspot.com/-Y4IdjQSHIV8/XR85D9jObJI/AAAAAAACmj8/N29KU7DzxUUG8R7DR-euAPQ0NwAH1OOdQCLcBGAs/s1600/XIUREN-No.1444-Xiao-Reba-Angela-MrCong.com-001.jpg"));
        photos.add(new Photo("https://1.bp.blogspot.com/-Y4IdjQSHIV8/XR85D9jObJI/AAAAAAACmj8/N29KU7DzxUUG8R7DR-euAPQ0NwAH1OOdQCLcBGAs/s1600/XIUREN-No.1444-Xiao-Reba-Angela-MrCong.com-001.jpg"));
        photos.add(new Photo("https://1.bp.blogspot.com/-Y4IdjQSHIV8/XR85D9jObJI/AAAAAAACmj8/N29KU7DzxUUG8R7DR-euAPQ0NwAH1OOdQCLcBGAs/s1600/XIUREN-No.1444-Xiao-Reba-Angela-MrCong.com-001.jpg"));
        photos.add(new Photo("https://1.bp.blogspot.com/-Y4IdjQSHIV8/XR85D9jObJI/AAAAAAACmj8/N29KU7DzxUUG8R7DR-euAPQ0NwAH1OOdQCLcBGAs/s1600/XIUREN-No.1444-Xiao-Reba-Angela-MrCong.com-001.jpg"));
        photos.add(new Photo("https://1.bp.blogspot.com/-Y4IdjQSHIV8/XR85D9jObJI/AAAAAAACmj8/N29KU7DzxUUG8R7DR-euAPQ0NwAH1OOdQCLcBGAs/s1600/XIUREN-No.1444-Xiao-Reba-Angela-MrCong.com-001.jpg"));
        photos.add(new Photo("https://1.bp.blogspot.com/-Y4IdjQSHIV8/XR85D9jObJI/AAAAAAACmj8/N29KU7DzxUUG8R7DR-euAPQ0NwAH1OOdQCLcBGAs/s1600/XIUREN-No.1444-Xiao-Reba-Angela-MrCong.com-001.jpg"));
        photos.add(new Photo("https://1.bp.blogspot.com/-Y4IdjQSHIV8/XR85D9jObJI/AAAAAAACmj8/N29KU7DzxUUG8R7DR-euAPQ0NwAH1OOdQCLcBGAs/s1600/XIUREN-No.1444-Xiao-Reba-Angela-MrCong.com-001.jpg"));
        adapter = new AdapterCarousel(GallerryIdol.this,photos);
        adapter.notifyDataSetChanged();
        carousel.setAdapter(adapter);
        */

        carousel.setOnItemClickListener(new CarouselBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CarouselBaseAdapter<?> parent, View view, int position, long id) {
                    Picasso.get().load(photos.get(position).getImage()).into(mainimage);
                    mainimage.setVisibility(View.VISIBLE);
            }
        });
        mainimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainimage.setVisibility(View.INVISIBLE);
            }
        });

    }

    // get data
   public void getdata(String link){
       photos.clear();
       RequestQueue requestQueue = Volley.newRequestQueue(this);
       StringRequest stringRequest = new StringRequest(Request.Method.GET,link, new Response.Listener<String>() {
           @Override
           public void onResponse(String response) {
               Log.d("doc",response.toString());
               String linkthumbail;
               Document document = Jsoup.parse(response);
               Elements items = document.select(".entry p img");
               for (Element i : items)
               {
                   Log.d("linkkk",i.attr("src"));
                   linkthumbail = i.attr("src");
                   photos.add(new Photo(linkthumbail));
               }

               adapter = new AdapterCarousel(GallerryIdol.this,photos);
              // adapter.notifyDataSetChanged();
               carousel.setAdapter(adapter);
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
