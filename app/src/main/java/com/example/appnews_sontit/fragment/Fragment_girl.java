package com.example.appnews_sontit.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
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
import com.example.appnews_sontit.unity.Config;
import com.example.appnews_sontit.unity.Idol;
import com.example.appnews_sontit.unity.Server;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import me.yuqirong.cardswipelayout.CardItemTouchHelperCallback;
import me.yuqirong.cardswipelayout.CardLayoutManager;
import me.yuqirong.cardswipelayout.OnSwipeListener;

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

        adapter = new IdolAdapter(getContext(),arrayList);
        recyclerView.setAdapter(adapter);

        final CardItemTouchHelperCallback cardCallback = new CardItemTouchHelperCallback(recyclerView.getAdapter(),arrayList);
        ItemTouchHelper itemTouchHelper =  new ItemTouchHelper(cardCallback);
        CardLayoutManager cardLayoutManager = new CardLayoutManager(recyclerView,itemTouchHelper);
        recyclerView.setLayoutManager(cardLayoutManager);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        cardCallback.setOnSwipedListener(new OnSwipeListener() {
            @Override
            public void onSwiping(RecyclerView.ViewHolder viewHolder, float v, int i) {
                Log.d("test","on swiping");
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, Object o, int i) {
                Log.d("test","on swiped");
            }

            @Override
            public void onSwipedClear() {
                Log.d("test","on swipe clear");
            }
        });

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
                        Toast.makeText(getActivity(), "Trang hợp lệ từ 1 đến 650 hihi ^^", Toast.LENGTH_SHORT).show();
                        edtpage.setText(PAGE+"");
                }else{
                    PAGE = pageuserenter;
                    getdata();
                }

            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("test","Destroy frag girl");
    }
}
