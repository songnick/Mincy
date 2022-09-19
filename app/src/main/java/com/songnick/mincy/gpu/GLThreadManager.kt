
//package com.songnick.mincy.gpu
//
//import android.content.pm.ConfigurationInfo
//import okhttp3.internal.notifyAll
//import javax.microedition.khronos.opengles.GL10
//
///*****
// * @author qfsong
// * Create Time: 2022/7/5
// **/
//class GLThreadManager {
//    companion object{
//        const val TAG = "GLThreadManager"
//        val GLES_20 = 0x20000
//        var kMSM7K_RENDERER_PREFIX = "Q3Dimension MSM7500 "
//    }
//    private var eglOwner:GLThread? = null
//
//    private var mGLESVersionCheckComplete = false
//
//    private var mGLESVersion = 0
//    private var mGLESDriverCheckComplete = false
//    private var multipleGLESContextAllowed =false
//    private var limitedGLESContexts = false
//
//    @Synchronized
//    fun threadExiting(glThread: GLThread){
//        glThread.exited = true
//        if (eglOwner == glThread){
//            eglOwner = null
//        }
//        notifyAll()
//    }
//
//    fun tryAcquireEGLContextLocked(glThread: GLThread):Boolean{
//        if (eglOwner == glThread || eglOwner == null){
//            eglOwner = glThread
//            notifyAll()
//            return true
//        }
//        checkGLESVersion()
//        if (multipleGLESContextAllowed){
//            return true
//        }
//        if (eglOwner != null){
//            eglOwner.hashCode()
//        }
//        return false
//    }
//
//    private fun checkGLESVersion(){
//        if (!mGLESVersionCheckComplete){
//            mGLESVersion = SystemProperties.getInt(
//                "ro.opengles.version",
//                ConfigurationInfo.GL_ES_VERSION_UNDEFINED
//            )
//            if (mGLESVersion >= GLES_20){
//                multipleGLESContextAllowed = true
//            }
//            mGLESVersionCheckComplete = true
//        }
//    }
//
//    fun releaseEGLContextLocked(glThread: GLThread){
//        if (eglOwner == glThread){
//            eglOwner = null
//        }
//        notifyAll()
//    }
//
//    @Synchronized
//    fun shouldReleaseEGLContextWhenPausing():Boolean{
//
//        return limitedGLESContexts
//    }
//
//    @Synchronized
//    fun shouldTerminateEGLWhenPausing():Boolean{
//
//        checkGLESVersion()
//        return !multipleGLESContextAllowed
//    }
//
//    @Synchronized
//    fun checkGLDriver(gl:GL10){
//        if (!mGLESDriverCheckComplete){
//            checkGLESVersion()
//            val renderer = gl.glGetString(GL10.GL_RENDERER)
//            if (mGLESVersion < GLES_20){
//                multipleGLESContextAllowed = !renderer.startsWith(kMSM7K_RENDERER_PREFIX)
//                notifyAll()
//            }
//            limitedGLESContexts = !multipleGLESContextAllowed
//            mGLESDriverCheckComplete = true
//        }
//    }
//}