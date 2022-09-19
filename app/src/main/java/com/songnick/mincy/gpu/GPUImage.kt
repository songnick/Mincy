//package com.songnick.mincy.gpu
//
//import android.app.ActivityManager
//import android.content.Context
//import android.graphics.Bitmap
//
///*****
// * @author qfsong
// * Create Time: 2022/7/7
// **/
//class GPUImage(c: Context) {
//
//    private var context = c
//    private val renderer  = GPUImageRender()
//    var glTextureView:GLTextureView? = null
//    set(value) {
//        field = value
//        value?.apply {
//            eglContextClientVersion = 2
//            setEGLConfigChooser(8,8,8,8,16,0)
//            setRenderer(renderer)
//            renderMode = GLTextureView.RENDERMODE_WHEN_DIRTY
//            requestRender()
//        }
//
//    }
//    var curBitmap:Bitmap? = null
//
//
//    init {
//        if (!supportOpenGLES2()){
//            throw IllegalStateException("opengl es 2.0 is not support")
//        }
//    }
//
//    private fun supportOpenGLES2():Boolean{
//        val activityManager = (context.getSystemService(Context.ACTIVITY_SERVICE)) as ActivityManager
//        val configInfo = activityManager.deviceConfigurationInfo
//        return configInfo.reqGlEsVersion >= 0x20000
//    }
//
//    fun setBackgroundColor(red:Float, green:Float, blue:Float, alpha:Float){
////        renderer.s
//    }
//
//    fun requestRender(){
//        glTextureView?.requestRender()
//    }
//
//
//}