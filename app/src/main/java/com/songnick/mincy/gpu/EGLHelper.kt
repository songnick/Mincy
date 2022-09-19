//package com.songnick.mincy.gpu
//
//import android.util.Log
//import java.lang.ref.WeakReference
//import javax.microedition.khronos.egl.*
//import javax.microedition.khronos.opengles.GL
//
///*****
// * @author qfsong
// * Create Time: 2022/7/3
// **/
//class EGLHelper(reference: WeakReference<GLTextureView>) {
//
//    companion object{
//        const val TAG = "EGLHelper"
//        fun eglGetErrorString(error: Int): String? {
//            return when (error) {
//                EGL11.EGL_SUCCESS -> "EGL_SUCCESS"
//                EGL11.EGL_NOT_INITIALIZED -> "EGL_NOT_INITIALIZED"
//                EGL11.EGL_BAD_ACCESS -> "EGL_BAD_ACCESS"
//                EGL11.EGL_BAD_ALLOC -> "EGL_BAD_ALLOC"
//                EGL11.EGL_BAD_ATTRIBUTE -> "EGL_BAD_ATTRIBUTE"
//                EGL11.EGL_BAD_CONFIG -> "EGL_BAD_CONFIG"
//                EGL11.EGL_BAD_CONTEXT -> "EGL_BAD_CONTEXT"
//                EGL11.EGL_BAD_CURRENT_SURFACE -> "EGL_BAD_CURRENT_SURFACE"
//                EGL11.EGL_BAD_DISPLAY -> "EGL_BAD_DISPLAY"
//                EGL11.EGL_BAD_MATCH -> "EGL_BAD_MATCH"
//                EGL11.EGL_BAD_NATIVE_PIXMAP -> "EGL_BAD_NATIVE_PIXMAP"
//                EGL11.EGL_BAD_NATIVE_WINDOW -> "EGL_BAD_NATIVE_WINDOW"
//                EGL11.EGL_BAD_PARAMETER -> "EGL_BAD_PARAMETER"
//                EGL11.EGL_BAD_SURFACE -> "EGL_BAD_SURFACE"
//                EGL11.EGL_CONTEXT_LOST -> "EGL_CONTEXT_LOST"
//                else -> "0x" + Integer.toHexString(error)
//            }
//        }
//    }
//
//    private val glTextureViewReference = reference
//
//    var eglContext:EGLContext? = null
//    var eglDisplay:EGLDisplay? = null
//    var eglSurface:EGLSurface? = null
//    var eglConfig:EGLConfig? = null
//    var egl:EGL10? = null
//
//    fun start(){
//        egl = EGLContext.getEGL() as EGL10
//
//        eglDisplay = egl!!.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY)
//        if (eglDisplay == EGL10.EGL_NO_DISPLAY){
//            throw RuntimeException("get display failed")
//        }
//        val version = IntArray(2)
//        val result = egl?.eglInitialize(eglDisplay, version)
//        if (result == false){
//            throw RuntimeException("egl initialized failed")
//        }
//        if (glTextureViewReference.get() != null){
//
//        }else{
//            eglConfig = null
//            eglContext = null
//        }
//
//        eglSurface = null
//    }
//
//
//    fun createSurface():Boolean{
//        if (egl == null){
//
//        }
//        if (eglDisplay == null){
//
//        }
//        if (eglConfig == null){
//
//        }
//        destroySurfaceImp()
//
//        val view = glTextureViewReference.get()
//        view?.let {
//            eglSurface = it.eglWindowSurfaceFactory?.createWindowSurface(egl,eglDisplay, eglConfig, it.surfaceTexture!!)
//        }
//        if (eglSurface == null || eglSurface == EGL10.EGL_NO_SURFACE){
//            val error = egl?.eglGetError()
//            Log.e(TAG, " create surface error: $error")
//            return false
//        }
//        if (egl?.eglMakeCurrent(eglDisplay, eglSurface, eglSurface,eglContext) == false){
//            Log.e(TAG, " egl make current error")
//            return false
//        }
//        return true
//    }
//
//    private fun destroySurfaceImp(){
//        if (eglSurface != null && eglSurface != EGL10.EGL_NO_SURFACE){
//            egl?.eglMakeCurrent(eglDisplay, EGL10.EGL_NO_SURFACE
//                                , EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT)
//            val view = glTextureViewReference.get()
//            view?.let {
//                it.eglWindowSurfaceFactory?.destroyWindowSurface(egl, eglDisplay,eglSurface)
//                eglSurface = null
//            }
//        }
//    }
//
//    fun createGL():GL?{
//        var gl = eglContext?.gl
//        val view = glTextureViewReference.get()
//        view?.let {
//            if (it == null){
//
//            }
//
//        }
//        return gl
//    }
//
//    fun swap():Int?{
//        if (egl?.eglSwapBuffers(eglDisplay, eglSurface) == false){
//
//            return egl?.eglGetError()
//        }
//        return EGL10.EGL_SUCCESS
//    }
//
//    fun destroySurface(){
//        destroySurfaceImp()
//    }
//
//    fun finish(){
//        if (eglContext != null){
//            val view = glTextureViewReference.get()
//            view?.let {
//                it.eglContextFactory?.destroyEGLContext(egl, eglDisplay, eglContext)
//
//            }
//            eglContext = null
//        }
//        if (eglDisplay != null){
//            egl?.eglTerminate(eglDisplay)
//            eglDisplay = null
//        }
//    }
//
//
//}