package com.example.appnews_sontit.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appnews_sontit.R;
import com.example.appnews_sontit.unity.Photo;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import fr.rolandl.carousel.CarouselAdapter;
import fr.rolandl.carousel.CarouselItem;

public class AdapterCarousel extends CarouselAdapter<Photo> {
    public static final class PhotoItem
            extends CarouselItem<Photo>
    {
        private ImageView image;
        private Context context;

        public PhotoItem(Context context)
        {
            super(context, R.layout.carouslel_item_idol);
            this.context = context;
        }

        @Override
        public void extractView(View view) {
            image = (ImageView) view.findViewById(R.id.imagecarousel);
        }

        @Override
        public void update(Photo photo)
        {
            Picasso.get().load(photo.image).into(image);
        }

    }

    public AdapterCarousel(Context context, List<Photo> photos)
    {
        super(context, photos);
    }

    @Override
    public CarouselItem<Photo> getCarouselItem(Context context)
    {
        return new PhotoItem(context);
    }
    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
