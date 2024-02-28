package com.example.photoviewtest.myview;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.photoviewtest.OffSet;
import com.example.photoviewtest.R;

public class PhotoView2 extends View {
    // 需要操作的图片
    private Bitmap mBitMap;
    // 画笔
    Paint mPaint = new Paint();
    // 将图片移动到View中心
    float offsetWidth = 0f;
    float offsetHeight = 0f;

    // 缩放前图片比例
    float smallScale = 0f;

    // 缩放后图片
    float bigScale = 0f;

    // 当前比例
    float currentScale = 0f;

    // 缩放倍数
    private static final float ZOOM_SCALE = 1.5f;

    private final GestureDetector mPhotoGestureListener;
    // 放大后手指移动位置
    private OffSet moveOffset = new OffSet(0f, 0f);
    //todo:放大后图片滑动

    public PhotoView2(Context context) {
        this(context, null);
    }

    public PhotoView2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("CustomViewStyleable")
    public PhotoView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PhotoView);

        Drawable drawable = typedArray.getDrawable(R.styleable.PhotoView_android_src);
        if (drawable == null)
            mBitMap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_baseline_close);
        else
//            mBitMap = toBitMap(drawable, 800, 800);
            mBitMap = toBitMap(drawable, 800, 800);
        mPhotoGestureListener = new GestureDetector(context, new PhotoGestureListener());
        // 回收 避免内存泄漏
        typedArray.recycle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // view比例
        float viewScale = (float) getWidth() / (float) getHeight();

        // 图片比例
        float bitScale = (float) mBitMap.getWidth() / (float) mBitMap.getHeight();

        // 如果图片比例大于view比例(比比谁更细)
        /*Log.d("tiktok", "onSizeChanged: ----viewScale----" + viewScale +
                "--bitScale--" + bitScale);*/
        if (bitScale > viewScale) {
            // 横向图片
            smallScale = (float) getWidth() / (float) mBitMap.getWidth();
            bigScale = (float) getHeight() / (float) mBitMap.getHeight() * ZOOM_SCALE;
        } else {
            // 纵向图片
            smallScale = (float) getHeight() / (float) mBitMap.getHeight();
            bigScale = (float) getWidth() / (float) mBitMap.getWidth() * ZOOM_SCALE;
        }
        /*Log.d("tiktok", "onSizeChanged: ----smallScale----" + smallScale +
                "--bigScale--" + bigScale);*/
        // 当前缩放比例 = 缩放前的比例
        currentScale = smallScale;

        //图片居中
        offsetWidth = getWidth() / 2f - mBitMap.getWidth() / 2f;
        offsetHeight = getHeight() / 2f - mBitMap.getHeight() / 2f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /*
         *  参数一: x 缩放比例
         *  参数二: y 缩放比例
         *  参数三: x 轴位置
         *  参数四: y 轴位置
         *
         *  这里为了简单起见,所以x,y缩放比例使用的同一个值[currentScale]
         */
        canvas.scale(currentScale, currentScale, getWidth() / 2f, getHeight() / 2f);

        canvas.drawBitmap(mBitMap, offsetWidth, offsetHeight, mPaint);

        /*//图片居中
        // 参数一:图片
        // 参数二:图片x位置
        // 参数三:图片y的位置
        // 参数四:画笔
        canvas.drawBitmap(mBitMap, offsetWidth, offsetHeight, mPaint);*/

        /*// 绘制一张图片 其实位置为0,0
        canvas.drawBitmap(mBitMap, 0, 0, mPaint);*/
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        return super.onTouchEvent(event);
        return mPhotoGestureListener.onTouchEvent(event);

    }

    // drawable -> bitmap
    private Bitmap toBitMap(Drawable drawable, int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    // 双击手势监听
    /*static */class PhotoGestureListener extends GestureDetector.SimpleOnGestureListener {
        // 是否双击 [默认第一次点击是缩小]
        boolean isDoubleClick = false;

        // 双击 -- 第二次按下时候触发 (40ms - 300ms) [小于40ms是为了防止抖动]
        // 单击情况 : 抬起[ACTION_UP]时候触发
        // 双击情况 : 第二次抬起[ACTION_POINTER_UP]时候触发
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.i("szjPhotoGestureListener", "抬起了 onSingleTapUp");
            return super.onSingleTapUp(e);
        }

        // 长按时触发 [300ms]
        @Override
        public void onLongPress(MotionEvent e) {
            Log.i("szjPhotoGestureListener", "长按了 onLongPress");
            super.onLongPress(e);
        }

        // 滑动时候触发 类似 ACTION_MOVE 事件
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.i("szjPhotoGestureListener", "滑动了  onScroll");
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        // 滑翔/飞翔 [惯性滑动]
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.i("szjPhotoGestureListener", "惯性滑动 onFling");
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        // 延时触发 [100ms] -- 常用与水波纹等效果
        @Override
        public void onShowPress(MotionEvent e) {
            super.onShowPress(e);
            Log.i("szjPhotoGestureListener", "延时触发 onShowPress");
        }

        // 按下 这里必须返回true 因为所有事件都是由按下出发的
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        // 双击 -- 第二次按下时候触发 (40ms - 300ms) [小于40ms是为了防止抖动]
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Log.i("tiktok", "双击了 onDoubleTap");
            //return super.onDoubleTap(e);
            // 先改为放大,第一次点击是放大效果
            isDoubleClick = !isDoubleClick;
            if (isDoubleClick) {
                // 放大 放大到最大比例
//                currentScale = bigScale;
                scaleAnimation(currentScale, bigScale).start();
            } else {
                // 缩小 缩小为左右留白的比例
//                currentScale = smallScale;
                scaleAnimation(bigScale, smallScale).start();
            }
            // 刷新 onDraw
            // 不需要刷新了,在属性动画调用setCurrentScale() 的时候已经刷新了
            invalidate();

            return super.onDoubleTap(e);
        }

        // 双击 第二次的事件处理 DOWN MOVE UP 都会执行到这里
        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            Log.i("szjPhotoGestureListener", "双击执行了 onDoubleTapEvent");
            return super.onDoubleTapEvent(e);
        }

        // 单击时触发 双击时不触发
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Log.i("szjPhotoGestureListener", "单击了 onSingleTapConfirmed");
            return super.onSingleTapConfirmed(e);
        }

        // 缩放动画
        public ObjectAnimator scaleAnimation(float start, float end) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(this, "currentScale", start, end);
            // 动画时间
            animator.setDuration(500);
            return animator;
        }

        // 属性动画的关键!!  内部通过反射调用set方法来赋值
        public void setCurrentScale(float currentScale) {
            PhotoView2.this.currentScale = currentScale;
            invalidate();
        }

    }

}

