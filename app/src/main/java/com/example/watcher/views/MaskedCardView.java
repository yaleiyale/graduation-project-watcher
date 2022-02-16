package com.example.watcher.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.ShapeAppearancePathProvider;

public class MaskedCardView extends MaterialCardView {

    private final ShapeAppearancePathProvider pathProvider = new ShapeAppearancePathProvider();
    private final Path path = new Path();
    private final ShapeAppearanceModel shapeAppearance = new ShapeAppearanceModel();
    private final RectF rectF = new RectF();

    public MaskedCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.clipPath(path);
        super.onDraw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        rectF.right = (float) w;
        rectF.bottom = (float) h;
        pathProvider.calculatePath(shapeAppearance, 1f, rectF, path);
        super.onSizeChanged(w, h, oldw, oldh);
    }


}
