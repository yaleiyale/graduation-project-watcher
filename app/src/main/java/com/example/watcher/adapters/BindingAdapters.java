package com.example.watcher.adapters;


import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import kotlin.jvm.internal.Intrinsics;

public class BindingAdapters {
    @BindingAdapter({"imageFromUrl"})
    public static void bindImageFromUrl(@NotNull ImageView view, @Nullable String imageUrl) {
        Intrinsics.checkNotNullParameter(view, "view");
        if (imageUrl != null && ((CharSequence) imageUrl).length() != 0) {
            Glide.with(view.getContext()).load("http://10.241.125.154:8080/" + imageUrl).transition(DrawableTransitionOptions.withCrossFade()).into(view);
        }

    }
}
