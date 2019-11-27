package com.example.appnews_sontit.fragment;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appnews_sontit.unity.Config;
import com.example.appnews_sontit.R;
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
import java.util.Random;

import me.yuqirong.cardswipelayout.CardItemTouchHelperCallback;
import me.yuqirong.cardswipelayout.CardLayoutManager;
import me.yuqirong.cardswipelayout.OnSwipeListener;

public class FragmnetContent extends Fragment {
    ImageView imgtop;
    ProgressBar progressBar;
    SwipeRefreshLayout swl;
    RecyclerView recyclerView;
    PostAdapter adapter;
    ArrayList<Post> arrayListPost;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content,container,false);
        Log.d("vongdoi","oncreatview - Fragment");
        // ánh xạ
        anhxa(view);
        setuprecyclerview();
        mess();
        sukienswipe();
        // sự kiện khi ko vuốt dc nữa
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(!recyclerView.canScrollVertically(1))
                {
                    Toast.makeText(getActivity(), "Hết rồi còn đâu mà vuốt cái gì haha", Toast.LENGTH_SHORT).show();
                    imgtop.setVisibility(View.VISIBLE);
                }
            }
        });
        imgtop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.scrollToPosition(0);
                imgtop.setVisibility(View.INVISIBLE);
            }
        });
        return view;
    }
    public void anhxa(View view){
        imgtop = (ImageView) view.findViewById(R.id.imagetop);
        imgtop.setVisibility(View.INVISIBLE);
        progressBar = view.findViewById(R.id.progress);
        swl = (SwipeRefreshLayout) view.findViewById(R.id.swiperefreshlayout);
        // random màu swipe to refresh
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        swl.setColorSchemeColors(color);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerviewcontent);
        arrayListPost = new ArrayList<>();
    }
    // setup recyclerview
    public void setuprecyclerview(){

        recyclerView.setHasFixedSize(true);

        // add divider item
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider));
        recyclerView.addItemDecoration(itemDecorator);

        adapter = new PostAdapter(getActivity(),R.layout.item_post,1,arrayListPost);
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);



    }
    // lấy data
    public void mess()
    {

        arrayListPost.clear();
        adapter.notifyDataSetChanged();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET,Server.link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("go","yess");
                Log.d("html",response.toString());
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
                        String[] time = timeago.split("T");
                        String daypost = time[0].replace('-', '/');
                        String timepost = time[1].substring(0,5);
                        String[] mangtime = timepost.split(":");
                        hourpost = Integer.parseInt(mangtime[0]);
                        minutepost = Integer.parseInt(mangtime[1]);
                        timeStamp = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
                        arrtimenow = timeStamp.split(":");
                        hournow = Integer.parseInt(arrtimenow[0]);
                        minutenow = Integer.parseInt(arrtimenow[1]);
                        if(hournow > hourpost) {
                            timeago = (hournow - hourpost) + " giờ trước";
                        }else{
                            timeago = (minutenow - minutepost) + " phút trước";
                        }
                        Log.d("time",hournow + ":" + minutenow);
                        arrayListPost.add(new Post(linkthumbail,linkpost,tittle,from,timeago));
                        progressBar.setVisibility(View.INVISIBLE);
                    }

                }
                adapter.notifyDataSetChanged();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(stringRequest);
    }

    // bắt sự kiện swipe
    public void sukienswipe()
    {
        swl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                MediaPlayer mediaPlayer = MediaPlayer.create(getActivity(),R.raw.cuoi);
                mediaPlayer.start();
                mess();
                swl.setRefreshing(false);
                Toast.makeText(getActivity(), "Đã cập nhật ! ahihi ^^", Toast.LENGTH_SHORT).show();
            }
        });
    }
    // sự kiện refresh
    public void refresh(){
        MediaPlayer mediaPlayer = MediaPlayer.create(getActivity(),R.raw.cuoi);
        mess();
        mediaPlayer.start();
        swl.setRefreshing(false);
        Toast.makeText(getActivity(), "Đã cập nhật ! ahihi ^^", Toast.LENGTH_SHORT).show();
    }
}
