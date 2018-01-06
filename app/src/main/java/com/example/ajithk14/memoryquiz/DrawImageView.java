package com.example.ajithk14.memoryquiz;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.FaceDetector;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Iterator;
import java.util.LinkedList;

import static java.lang.Math.min;

public class DrawImageView extends View {

    private Paint paint;
    private Bitmap image;
    private RectF dimens;
    private int touchX=600, touchY=600;
    private int radius;
    private LinkedList<FaceRectangle> rects;
    private Bitmap resizeIcon, moveIcon, deleteIcon;

    private FaceRectangle selected = null;
    private boolean moving = false; //true = moving rectangle, false = resizing rectangle

    public DrawImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        image = BitmapFactory.decodeResource(getContext().getResources(),R.drawable.stock_image);
        resizeIcon = BitmapFactory.decodeResource(getContext().getResources(),R.drawable.resize);
        moveIcon = BitmapFactory.decodeResource(getContext().getResources(),R.drawable.move);
        deleteIcon = BitmapFactory.decodeResource(getContext().getResources(),R.drawable.delete);


        paint = new Paint();

        dimens = new RectF();

        rects = new LinkedList<FaceRectangle>();



        setOnClickListener(new OnClickListener() { //just to make onTouch work properly, not being used
            @Override
            public void onClick(View v) {}
        });

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                touchX = (int) event.getX();
                touchY = (int) event.getY();



                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        boolean makeNewRect = true;
                        Iterator<FaceRectangle> i = rects.iterator();
                        while (i.hasNext()) {
                            FaceRectangle f = i.next();
                            if(f.getMoveRect().contains(touchX,touchY)) {
                                selected = f;
                                moving = true;
                                makeNewRect = false;
                            } else if(f.getResizeRect().contains(touchX,touchY)) {
                                selected = f;
                                moving = false;
                                makeNewRect = false;
                            } else if(f.getDeleteRect().contains(touchX,touchY)) {
                                i.remove();
                                makeNewRect = false;
                            }
                        }
                        if(makeNewRect) {
                            rects.addFirst(new FaceRectangle(touchX,touchY));
                            selected = rects.getFirst();
                            moving=false;
                        }

                        break;

                    case MotionEvent.ACTION_MOVE:

                        if(selected!=null) {
                            if(moving) {
                                selected.setCoordinatesFromCenter(touchX, touchY);
                            } else {
                                selected.setDimensFromBottomRightCorner(touchX,touchY);
                            }
                        }
                        break;

                    case MotionEvent.ACTION_UP:

                        selected=null;
                        break;

                }



                invalidate((int)dimens.left,(int)dimens.top,(int)dimens.right,(int)dimens.bottom);
                return false;
            }
        });

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

        for(FaceRectangle e : rects) {
            e.draw(canvas, moveIcon, resizeIcon, deleteIcon);
        }


    }

}

