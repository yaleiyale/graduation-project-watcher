package com.example.watcher.adapters;


import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import kotlin.jvm.internal.Intrinsics;

public class BindingAdapters {
    public static final String SERVER_IP = "192.168.137.1";

    @BindingAdapter({"imageFromUrl"})
    public static void bindImageFromUrl(@NotNull ImageView view, @Nullable String imageUrl) {
        Intrinsics.checkNotNullParameter(view, "view");
        if (imageUrl != null && ((CharSequence) imageUrl).length() != 0) {
            Glide.with(view.getContext()).load("http://" + SERVER_IP + ":8080/" + imageUrl).transition(DrawableTransitionOptions.withCrossFade()).into(view);
        }

    }
}
