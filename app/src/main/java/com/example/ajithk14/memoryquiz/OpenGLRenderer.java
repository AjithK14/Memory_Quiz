package com.example.ajithk14.memoryquiz;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Ajithk14 on 2/12/2018.
 */

public class OpenGLRenderer implements GLSurfaceView.Renderer{

    private double greenVal = 1f;
    private static final double flashDur = 1000;
    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        GLES20.glClearColor(1f,0,0,1f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int i, int i1) {

    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        GLES20.glClearColor((float)greenVal,0,0,1f);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        greenVal = ((Math.sin(System.currentTimeMillis()*2*Math.PI/flashDur)*0.5)+0.5);
    }
}
