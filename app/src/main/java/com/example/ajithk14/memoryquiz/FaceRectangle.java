package com.example.ajithk14.memoryquiz;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created by ravidudhagra on 12/26/17.
 */

//Class designed to hold information about the faces selected by the picture...
    //Basically a Rect object, but has a special draw function that takes in a canvas.

class FaceRectangle {

    private RectF dimens, resize, move, delete;

    FaceRectangle(int x, int y) {
        dimens = new RectF(x-10,y-10,x+10,y+10);
        resize = new RectF();
        move = new RectF();
        delete = new RectF();

        resize.set(dimens.right-25-10,dimens.bottom-25-10,dimens.right+25-10,dimens.bottom+25-10);
        delete.set(dimens.left-25,dimens.top-25,dimens.left+25,dimens.top+25);
        move.set(dimens.centerX()-40,dimens.centerY()-40,dimens.centerX()+40,dimens.centerY()+40);
    }

    RectF getRect() {
        return dimens;
    }

    RectF getMoveRect() {
        return move;
    }

    RectF getDeleteRect() {
        return delete;
    }

    RectF getResizeRect() {
        return resize;
    }

    void setDimensFromBottomRightCorner(int x, int y) {
        if(x<dimens.centerX())
            x=(int)(2*dimens.centerX()-x);
        if(y<dimens.centerY())
            y=(int)(2*dimens.centerY()-y);

        if(x-dimens.centerX()<50)
            x=(int)dimens.centerX()+50;
        if(y-dimens.centerY()<50)
            y=(int)dimens.centerY()+50;

        dimens.set(2*dimens.centerX()-x,2*dimens.centerY()-y, x, y);
    }

    void setCoordinatesFromCenter(int x, int y) {
        dimens.offset(x-dimens.centerX(),y-dimens.centerY());
    }

    void draw(Canvas canvas, Bitmap moveIcon, Bitmap resizeIcon, Bitmap deleteIcon) {
        Paint p = new Paint();
        p.setColor(Color.argb(120,0,0,0));
        canvas.drawRoundRect(dimens,25,25,p);

        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(10);
        p.setColor(Color.BLACK);
        canvas.drawRoundRect(dimens,25,25,p);

        resize.set(dimens.right-25-10,dimens.bottom-25-10,dimens.right+25-10,dimens.bottom+25-10);
        delete.set(dimens.left-25,dimens.top-25,dimens.left+25,dimens.top+25);
        move.set(dimens.centerX()-40,dimens.centerY()-40,dimens.centerX()+40,dimens.centerY()+40);

        canvas.drawBitmap(moveIcon,null, move,p);
        canvas.drawBitmap(resizeIcon,null,resize,p);
        canvas.drawBitmap(deleteIcon,null,delete,p);

    }
}
