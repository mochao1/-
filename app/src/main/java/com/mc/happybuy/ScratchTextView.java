package com.mc.happybuy;

import android.content.Context;
import android.widget.TextView;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import constant.AllConstant;

/**
 * 刮刮卡类
 * @author reyo
 *
 */
public class ScratchTextView extends TextView{

  private float TOUCH_TOLERANCE;
  private Bitmap mBitmap;
  private Canvas mCanvas;
  private Paint mPaint;
  private Path mPath;
  private float mX,mY;

  private boolean isDraw = false;

  public ScratchTextView(Context context) {
    super(context);

  }
  public ScratchTextView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  public ScratchTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    if (isDraw) {
      mCanvas.drawPath(mPath, mPaint);
      //  mCanvas.drawPoint(mX, mY, mPaint);
      canvas.drawBitmap(mBitmap, 0, 0, null);
    }
  }

  /**
   * 初始化刮刮卡
   * @param bgColor 刮刮卡背景色
   * @param paintStrokeWidth 擦除线宽
   * @param touchTolerance 画线容差
   */
  public void initScratchCard(final int bgColor,final int paintStrokeWidth,float touchTolerance) {
    TOUCH_TOLERANCE = touchTolerance;
    mPaint = new Paint();
    mPaint.setAlpha(0);
    mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
    mPaint.setAntiAlias(true);
    mPaint.setDither(true);
    mPaint.setStyle(Paint.Style.STROKE);
    mPaint.setStrokeJoin(Paint.Join.ROUND);
    mPaint.setStrokeCap(Paint.Cap.ROUND);
    mPaint.setStrokeWidth(paintStrokeWidth);

    mPath = new Path();

    mBitmap = Bitmap.createBitmap(getLayoutParams().width, getLayoutParams().height, Config.ARGB_8888);
    mCanvas = new Canvas(mBitmap);

    mCanvas.drawColor(bgColor);
    isDraw = true;
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    if (!isDraw) {
      return true;
    }
    switch (event.getAction()) {
      case MotionEvent.ACTION_DOWN:
        touchDown(event.getX(), event.getY());
        invalidate();
        break;
      case MotionEvent.ACTION_MOVE:
        AllConstant.sign++;
        touchMove(event.getX(), event.getY());
        invalidate();
        break;
      case MotionEvent.ACTION_UP:
        touchUp(event.getX(), event.getY());
        invalidate();
        break;
      default:
        break;
    }
    return true;
  }


  private void touchDown(float x,float y){
    mPath.reset();
    mPath.moveTo(x, y);
    mX = x;
    mY = y;
  }

  private void touchMove(float x,float y){
    float dx = Math.abs(x - mX);
    float dy = Math.abs(y - mY);
    if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
      mPath.quadTo(mX, mY, (x + mX)/2, (y + mY)/2);
      mX = x;
      mY = y;
    }

  }

  private void touchUp(float x,float y){
    mPath.lineTo(x, y);
    mCanvas.drawPath(mPath, mPaint);
    mPath.reset();
  }

}
