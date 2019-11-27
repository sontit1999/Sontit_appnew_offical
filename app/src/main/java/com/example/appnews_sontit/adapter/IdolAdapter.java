package com.example.appnews_sontit.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.appnews_sontit.GallerryIdol;
import com.example.appnews_sontit.R;
import com.example.appnews_sontit.callback.GirlCallback;
import com.example.appnews_sontit.unity.Idol;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class IdolAdapter extends RecyclerView.Adapter<IdolAdapter.Holderview>{
    Context context;
    ArrayList<Idol> arrayList;
    GirlCallback listener;

    public IdolAdapter(Context context, ArrayList<Idol> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        listener = (GirlCallback) context;
    }

    @NonNull
    @Override
    public Holderview onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_item_idol,null);
        return new Holderview(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holderview holderview, final int i) {
        final Idol idol = arrayList.get(i);
        if(!idol.getLinkidol().trim().isEmpty() && !idol.getLinkthumbail().trim().isEmpty() && !idol.getNameidol().trim().isEmpty()){
            Picasso.get().load(idol.getLinkthumbail()).into(holderview.imgidol);
            holderview.nameidol.setText(idol.getNameidol());
        }
        holderview.containeridol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GallerryIdol.class);
//                intent.putExtra("linkidol",idol.getLinkidol());
//                context.startActivity(intent);
                  listener.onGirlclick(idol.getLinkidol());
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class Holderview extends RecyclerView.ViewHolder {
        LinearLayout containeridol;
        ImageView imgidol;
        TextView nameidol;
        public Holderview(@NonNull View itemView) {
            super(itemView);
            containeridol = (LinearLayout) itemView.findViewById(R.id.containerIdol);
            imgidol = (ImageView) itemView.findViewById(R.id.imageviewidol);
            nameidol = (TextView) itemView.findViewById(R.id.textviewNameidol);
        }
    }
}
