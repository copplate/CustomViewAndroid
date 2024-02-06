package com.example.expandviewgroupprac.mview_

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import com.example.expandviewgroupprac.R

class LHC_SelectedImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatImageView(context, attrs) {

    var default_img: Drawable? = null
    var seleted_img: Drawable? = null
    //用来切换图片的标记
    var flag = false
    //记录一次完整的点击
    var down = false
    init {
        val array: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.LHC_SelectedImageView)
        default_img = array.getDrawable(R.styleable.LHC_SelectedImageView_defaultImag)
        seleted_img = array.getDrawable(R.styleable.LHC_SelectedImageView_selectedImg)
    }

    //初始化时候设置默认图片
    private fun setDefaultImage() {
        if (default_img!=null)
            this.background = default_img
    }

    //在onTouchEvent中点击一次完成进行背景的修改。
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        Log.e("tiktok", "onTouchEvent=" + event.action.toString())
        if (event.action == MotionEvent.ACTION_DOWN) {
            down = true
            setBackgroundImag()
        }
        if (event.action == MotionEvent.ACTION_UP && down) {
            //按下的时候设置图片
            setBackgroundImag()
            down = false
        }
        return super.onTouchEvent(event)

    }

    //修改背景
    private fun setBackgroundImag() {
        if (!flag) {
            this.background =seleted_img
        } else {
//            this.background =default_img
            this.background =null
        }
        flag = !flag
    }






}