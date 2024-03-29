package com.example.appnews_sontit.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appnews_sontit.R;
import com.example.appnews_sontit.adapter.ImageAdapter;
import com.example.appnews_sontit.unity.Photo;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import me.yuqirong.cardswipelayout.CardItemTouchHelperCallback;
import me.yuqirong.cardswipelayout.CardLayoutManager;
import me.yuqirong.cardswipelayout.OnSwipeListener;
import uk.co.senab.photoview.PhotoViewAttacher;

@SuppressLint("ValidFragment")
public class Fragment_Idol extends Fragment {
    RecyclerView recyclerView;
    int PAGE = 0;
    ImageAdapter adapter;
    String link ;
    List<Photo> photos;
    @SuppressLint("ValidFragment")
    public Fragment_Idol(String link) {
        this.link = link;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("test","link idol nhận từ contructor" + link);
        View view = inflater.inflate(R.layout.fragment_idol,container,false);
        // anh xa
        anhxa(view);
        // set up recyclerview
        setuprecyclerview();
        getdata(link);
        sukienclick();
        return view;

    }

    private void anhxa(View view) {
        recyclerView = view.findViewById(R.id.recyclerviewgalleryidol);
        photos = new ArrayList<>();
    }
    public void setuprecyclerview(){
        recyclerView.setHasFixedSize(true);
        adapter = new ImageAdapter(getContext(),photos);
        // tao layoutmanager
        //RecyclerView.LayoutManager linearLayoutManager = new GridLayoutManager(getContext(),2,LinearLayoutManager.VERTICAL,false);
       // recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        final CardItemTouchHelperCallback cardCallback = new CardItemTouchHelperCallback(recyclerView.getAdapter(),photos);
        ItemTouchHelper itemTouchHelper =  new ItemTouchHelper(cardCallback);
        CardLayoutManager cardLayoutManager = new CardLayoutManager(recyclerView,itemTouchHelper);
        recyclerView.setLayoutManager(cardLayoutManager);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        cardCallback.setOnSwipedListener(new OnSwipeListener() {
            @Override
            public void onSwiping(RecyclerView.ViewHolder viewHolder, float v, int i) {

            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, Object o, int i) {

            }

            @Override
            public void onSwipedClear() {

                getdata(link);
            }
        });

    }
    public void getdata(String link){
        if(PAGE<7){
            PAGE++;
            Log.d("test","lấy page" + PAGE);
            String duongan = link + PAGE;
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
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
                    int curSize = adapter.getItemCount();
                    adapter.notifyItemRangeInserted(curSize, photos.size() - 1);
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
        }else{
            Toast.makeText(getActivity(), "Hết ảnh rùi :v", Toast.LENGTH_SHORT).show();
        }
    }
    public void sukienclick(){

    }
    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("test","Destroy frag Idol");
    }

    private void showDialog(Photo photo){
        Dialog dialog = new Dialog(getContext(),android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.dialog_detail_idol);
        ImageView imgIdol = dialog.findViewById(R.id.iv_idol);
        Picasso.get().load(photo.getImage()).into(imgIdol);
        PhotoViewAttacher pAttacher;
        pAttacher = new PhotoViewAttacher(imgIdol);
        pAttacher.update();

        dialog.show();
    }


}
