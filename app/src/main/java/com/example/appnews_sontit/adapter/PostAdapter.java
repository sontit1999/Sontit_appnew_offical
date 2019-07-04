package com.example.appnews_sontit.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appnews_sontit.Detailpost;
import com.example.appnews_sontit.MainActivity;
import com.example.appnews_sontit.R;
import com.example.appnews_sontit.unity.Database;
import com.example.appnews_sontit.unity.Post;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.Myviewholder> {
    Context context;
    int layout;
    int animation;
    ArrayList<Post> arrayList;

    public PostAdapter(Context context,int layout,int animation, ArrayList<Post> arrayList) {
        this.layout = layout;
        this.context = context;
        this.arrayList = arrayList;
        this.animation = animation;
    }

    public PostAdapter() {
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(layout,null);
        return new Myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder myviewholder, int i) {
        final Post post =  arrayList.get(i);
        switch (animation){
            case 1: myviewholder.containerpost.setAnimation(AnimationUtils.loadAnimation(context, R.anim.animation_itempost));
                break;
            case 2:
                myviewholder.containerpost.setAnimation(AnimationUtils.loadAnimation(context,R.anim.animation_itemsavepost));
                break;
            case 3:
                TranslateAnimation translateAnimation = new TranslateAnimation(500,0,0,0);
                translateAnimation.setDuration(1000);
                myviewholder.containerpost.setAnimation(translateAnimation);
                break;
                default:
                    break;
        }
        Picasso.get().load(post.getLinkthumbail()).into(myviewholder.img);
        myviewholder.txttittle.setText(post.getTittle());
        myviewholder.txttimeandfrom.setText(post.getFromnew() + " - " + post.getTimepost());
        // bắt sự kiện click vào item thì add post vào database
        myviewholder.containerpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post.setTimepost("");
                MainActivity.addPostseen(post,"Seenpost");
                Intent intent = new Intent(context, Detailpost.class);
                intent.putExtra("link",post.getLinkpost());
                context.startActivity(intent);
            }
        });
        // bắt sự kiện longclick vào item thì save post vào database
        myviewholder.containerpost.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(context, "Đã lưu tin", Toast.LENGTH_SHORT).show();
                post.setTimepost("");
                MainActivity.addPostseen(post,"Savepost");
                return true;
            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder {
        LinearLayout containerpost;
        ImageView img;
        TextView txttittle,txttimeandfrom;
        public Myviewholder(@NonNull final View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.imageviewcontent);
            txttittle = (TextView) itemView.findViewById(R.id.textviewtittlecontent);
            txttimeandfrom  = (TextView) itemView.findViewById(R.id.textviewtimeandfrom);
            containerpost = (LinearLayout) itemView.findViewById(R.id.containerpost);
        }
    }
}
