package com.example.watcher.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.google.android.material.R.style;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.ShapeAppearancePathProvider;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import kotlin.jvm.JvmOverloads;

public final class MaskedCardView extends MaterialCardView {

    private final ShapeAppearancePathProvider pathProvider;
    private final Path path;
    private final ShapeAppearanceModel shapeAppearance;
    private final RectF rectF;

    protected void onDraw(@NotNull Canvas canvas) {
        canvas.clipPath(this.path);
        super.onDraw(canvas);
    }


    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        this.rectF.right = (float) w;
        this.rectF.bottom = (float) h;
        this.pathProvider.calculatePath(this.shapeAppearance, 1.0F, this.rectF, this.path);
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @JvmOverloads
    public MaskedCardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.pathProvider = new ShapeAppearancePathProvider();
        this.path = new Path();
        this.shapeAppearance = ShapeAppearanceModel.builder(context, attrs, defStyle, style.Widget_MaterialComponents_CardView).build();
        this.rectF = new RectF(0.0F, 0.0F, 0.0F, 0.0F);
    }


    @JvmOverloads
    public MaskedCardView(@NotNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @JvmOverloads
    public MaskedCardView(@NotNull Context context) {
        this(context, (AttributeSet) null, 0);
    }
}
