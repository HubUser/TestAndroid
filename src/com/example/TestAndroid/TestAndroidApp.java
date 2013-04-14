package com.example.TestAndroid;

import android.app.Application;
import android.util.DisplayMetrics;
import com.nostra13.universalimageloader.imageloader.ImageLoader;
import com.nostra13.universalimageloader.imageloader.ImageLoaderConfiguration;

public class TestAndroidApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .maxImageWidthForMemoryCache(displayMetrics.widthPixels)
                .maxImageHeightForMemoryCache(displayMetrics.heightPixels)
                .threadPoolSize(3)
                .discCacheDir("UniversalImageLoaderApp/Cache")
                .memoryCacheSize(1500000)
                .build();
        // Initialize ImageLoader with configuration.
        //versions 26 - 38 fix and fix server int->long
        //     updating
        ImageLoader.getInstance().init(config);

    }

}
