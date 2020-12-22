package com.example.bigproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.TypedValue;
import android.content.Context;
import android.util.AttributeSet;

import androidx.lifecycle.Lifecycle;

public class DrawingView extends View {
    private Path drawPath;
    private boolean erase=false;
    private Paint drawPaint,canvasPaint;
    private Canvas drawCanvas;
    private int paintColor=0xFF660000;
    private Bitmap canvasBitmap;
    private float BrushSize,lastBrushSize;
    private boolean Load;
    private Bitmap loadBitmap;

    public DrawingView(Context context,AttributeSet attributeSet){
        super(context,attributeSet);
        setupDrawing();
    }
    public void startNew(){
        drawCanvas.drawColor(0,PorterDuff.Mode.CLEAR);
        invalidate();
    }
    public void setErase(boolean isErase){
        erase=isErase;
        if(erase) drawPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        else drawPaint.setXfermode(null);
    }
    public void setBrushSize(float newSize){
        float pixelAmount=TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,newSize,getResources().getDisplayMetrics());
        BrushSize=pixelAmount;
        drawPaint.setStrokeWidth(BrushSize);
    }
    public void setLastBrushSize(float lastSize){
        lastBrushSize=lastSize;
    }
    public float getBrushSize(){
        return lastBrushSize;
    }
    public void setColor(String newColor){
        invalidate();
        paintColor=Color.parseColor(newColor);
        drawPaint.setColor(paintColor);
    }
    @Override
    public void onSizeChanged(int w,int h,int oldw,int oldh){
        super.onSizeChanged(w,h,oldw,oldh);
        canvasBitmap=Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
        drawCanvas=new Canvas(canvasBitmap);
        if(Load){
            if(loadBitmap!=null){
                drawCanvas.drawBitmap(loadBitmap,0,0,null);
                Load=false;
            }
        }
    }
    @Override
    public void onDraw(Canvas canvas){
        canvas.drawBitmap(canvasBitmap,0,0,canvasPaint);
        canvas.drawPath(drawPath,drawPaint);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        float TouchX=event.getX();
        float TouchY=event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                drawPath.rMoveTo(TouchX,TouchY);
                break;
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(TouchX,TouchY);
                break;
            case MotionEvent.ACTION_UP:
                drawCanvas.drawPath(drawPath,drawPaint);
                drawPath.reset();
                break;
            default:
                return false;
        }
        invalidate();
        return true;
    }
    public void setupDrawing(){
        drawPath=new Path();
        drawPaint=new Paint();
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(5);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        canvasPaint=new Paint(Paint.DITHER_FLAG);
        BrushSize=10;
        lastBrushSize=BrushSize;
        drawPaint.setStrokeWidth(BrushSize);
    }
    public void LoadDraw(Bitmap bm){
        Load=true;
        loadBitmap=bm;
    }
}
