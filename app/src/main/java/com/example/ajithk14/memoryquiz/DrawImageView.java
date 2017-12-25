package com.example.ajithk14.memoryquiz;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import static java.lang.Math.min;

public class DrawImageView extends View {

    private Paint paint;
    private Bitmap image;
    private RectF dimens;
    private int touchX=600, touchY=600;
    private int radius;

    public DrawImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        image = BitmapFactory.decodeResource(getContext().getResources(),R.drawable.stock_image);

        paint = new Paint();

        dimens = new RectF();
    }

    @Override
    public void onDraw(Canvas canvas) {
        radius = min(getWidth(), getHeight()) / 10;


        // rect contains the bounds of the shape
        // radius is the radius in pixels of the rounded corners
        // paint contains the shader that will texture the shape
        int width = image.getWidth();
        int height = image.getHeight();
        float ratioBitmap = (float) width / (float) height;
        float ratioMax = (float) getWidth() / (float) getHeight();

        int finalWidth = getWidth();
        int finalHeight = getHeight();
        if (ratioMax > ratioBitmap) {
            finalWidth = (int) ((float) getHeight() * ratioBitmap);
        } else {
            finalHeight = (int) ((float) getWidth() / ratioBitmap);
        }
        dimens.set((getWidth() - finalWidth) / 2, (getHeight() - finalHeight) / 2, finalWidth + (getWidth() - finalWidth) / 2, finalHeight + (getHeight() - finalHeight) / 2);
        canvas.drawBitmap(image, null, dimens, paint);

        paint.setColor((Color.RED));
        canvas.drawCircle(touchX, touchY, radius / 5, paint);
    }

    public void setTouch(int x, int y) {
        touchX=x;
        touchY=y;
        invalidate(touchX- radius / 5,touchY- radius / 5,touchX+ radius / 5,touchY+ radius / 5);
    }

}

