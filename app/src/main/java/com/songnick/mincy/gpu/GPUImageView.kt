//package com.songnick.mincy.gpu
//
//import android.content.Context
//import android.util.AttributeSet
//import android.widget.FrameLayout
//import javax.microedition.khronos.egl.EGL
//
///*****
// * @author qfsong
// * Create Time: 2022/7/2
// **/
//class GPUImageView @JvmOverloads constructor(
//    context: Context, attrs: AttributeSet? = null
//) : FrameLayout(context, attrs) {
//
//    private var glTextureView:GLTextureView = GLTextureView(context, attrs)
//    private var gpuImage:GPUImage? = null
//
//    init {
//        glTextureView.isOpaque = false
//        if (!isInEditMode){
//            addView(glTextureView)
//            gpuImage = GPUImage()
//
//        }
//    }
//
//
//}