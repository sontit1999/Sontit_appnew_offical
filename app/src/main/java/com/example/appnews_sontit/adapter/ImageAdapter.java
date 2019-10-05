package com.example.appnews_sontit.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.appnews_sontit.GallerryIdol;
import com.example.appnews_sontit.R;
import com.example.appnews_sontit.callback.GirlCallback;
import com.example.appnews_sontit.fragment.Fragment_Idol;
import com.example.appnews_sontit.unity.Photo;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends  RecyclerView.Adapter<ImageAdapter.Viewholder>{
    Context context;
    List<Photo> arraylist;
    GirlCallback listener;
    public ImageAdapter(Context context,List<Photo> arraylist) {
        this.arraylist = arraylist;
        this.context = context;
        listener = (GirlCallback) context;

    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.carouslel_item_idol,null);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder viewholder, int i) {
            viewholder.contain.setAnimation(AnimationUtils.loadAnimation(context, R.anim.animation_itempost));
            final Photo photo = arraylist.get(i);
            if(photo!=null){
                Picasso.get().load(photo.getImage()).into(viewholder.img);
            }
            viewholder.contain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onImageGirlClick(photo);
                }
            });

    }

    @Override
    public int getItemCount() {
        return arraylist.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ImageView img;
        LinearLayout contain ;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.imagecarousel);
            contain = (LinearLayout) itemView.findViewById(R.id.contain);
        }
    }

}
