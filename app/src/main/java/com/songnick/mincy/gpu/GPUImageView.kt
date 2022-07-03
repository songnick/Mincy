package com.songnick.mincy.gpu

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import javax.microedition.khronos.egl.EGL

/*****
 * @author qfsong
 * Create Time: 2022/7/2
 **/
class GPUImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {
    init {
        addTextureView()
    }

    private fun addTextureView(){

    }



}