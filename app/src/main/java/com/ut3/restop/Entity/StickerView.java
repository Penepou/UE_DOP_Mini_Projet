package com.ut3.restop.Entity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class StickerView extends View {

    private Bitmap stickerBitmap;
    private Matrix matrix;

    private float lastTouchX;
    private float lastTouchY;

    public StickerView(Context context) {
        super(context);
        init();
    }

    public StickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        matrix = new Matrix();
    }

    public void setStickerBitmap(Bitmap bitmap) {
        this.stickerBitmap = bitmap;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (stickerBitmap != null) {
            canvas.drawBitmap(stickerBitmap, matrix, null);
        }
    }

    public void setStickerResource(int resourceId) {
        BitmapDrawable drawable = (BitmapDrawable) getResources().getDrawable(resourceId);
        if (drawable != null) {
            Bitmap bitmap = drawable.getBitmap();
            setStickerBitmap(bitmap);
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastTouchX = touchX;
                lastTouchY = touchY;
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = touchX - lastTouchX;
                float dy = touchY - lastTouchY;
                matrix.postTranslate(dx, dy);
                invalidate();
                lastTouchX = touchX;
                lastTouchY = touchY;
                break;
        }

        return true;
    }
}

