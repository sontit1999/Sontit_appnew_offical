package com.example.appnews_sontit.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appnews_sontit.R;
import com.example.appnews_sontit.adapter.IdolAdapter;
import com.example.appnews_sontit.adapter.PostAdapter;
import com.example.appnews_sontit.unity.Config;
import com.example.appnews_sontit.unity.Idol;
import com.example.appnews_sontit.unity.Post;
import com.example.appnews_sontit.unity.Server;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Fragment_girl extends Fragment {
    public int PAGE = 1;
    ImageView imgback,imgnext;
    EditText edtpage;
    TextView txtgo;
    RecyclerView recyclerView;
    ArrayList<Idol> arrayList;
    IdolAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_girl,container,false);
        // anh xa
        anhxa(view);
        // set up recyclerview
        setuprecyclerview();
        getdata();
        sukienclick();
        return view;
    }
   // anh xa
    private void anhxa(View view) {
        imgback = (ImageView) view.findViewById(R.id.imageback);
        imgnext = (ImageView) view.findViewById(R.id.imagenext);
        txtgo = (TextView) view.findViewById(R.id.textviewgo);
        edtpage = (EditText) view.findViewById(R.id.textviewpage);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        arrayList = new ArrayList<>();
    }

    // setup recyclerview
    public void setuprecyclerview(){

        recyclerView.setHasFixedSize(true);

        // add divider item
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider));
        recyclerView.addItemDecoration(itemDecorator);

        adapter = new IdolAdapter(getContext(),arrayList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        if(Config.orien == 0){
            recyclerView.setLayoutManager(linearLayoutManager);
        }else{
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,1));
        }
        recyclerView.setAdapter(adapter);

    }

    // get data
    public void getdata()
    {
        String link = Server.linkxxx + PAGE;
        arrayList.clear();
        adapter.notifyDataSetChanged();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET,link , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String linkidol,linkthumbail,nameidol;
                Document document = Jsoup.parse(response);
                Elements items = document.select(".item-list");
                for (Element i : items)
                {
                    linkidol = i.select(".post-thumbnail a").attr("href");
                    linkthumbail = i.select(".post-thumbnail a img").attr("src");
                    nameidol = i.select("h2 a").text();
                    arrayList.add(new Idol(linkidol,linkthumbail,nameidol));
                    Log.d("Idol",nameidol + "-" + linkthumbail + "-" + linkidol);
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
    // sự kiện click
    public void sukienclick()
    {
        imgnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PAGE<650){
                    edtpage.setText((PAGE+1) + "");
                    PAGE++;
                    getdata();
                }

            }
        });
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PAGE>=2){
                    edtpage.setText((PAGE-1) + "");
                    PAGE--;
                    getdata();
                }
            }
        });
        txtgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pageuserenter = Integer.parseInt(edtpage.getText().toString());
                if(pageuserenter>650 || pageuserenter <=0){
                        Toast.makeText(getActivity(), "Trang hợp lệ từ 0 đến 650 hihi ^^", Toast.LENGTH_SHORT).show();
                        edtpage.setText(PAGE+"");
                }else{
                    PAGE = pageuserenter;
                    getdata();
                }

            }
        });
    }
}
