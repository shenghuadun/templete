package com.greenidea.templete.utils;

import android.widget.ImageView;
import com.greenidea.templete.R;

/**
 * Created by Green on 2015/1/3.
 */
public class ImageFetcher
{
    private static int defaultId = R.drawable.default_image;
    public static void loadImage(ImageView imageView, String url, int resId)
    {
        imageView.setImageResource(defaultId);

    }
}
