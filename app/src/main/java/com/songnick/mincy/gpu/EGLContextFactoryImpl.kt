package com.songnick.mincy.gpu

import javax.microedition.khronos.egl.*

/*****
 * @author qfsong
 * Create Time: 2022/7/3
 **/
class EGLContextFactoryImpl:EGLContextFactory {
    companion object{
       const val EGL_CONTEXT_CLIENT_VERSION = 0x3098
    }

    var eglContextVersion:Int = 0

    override fun createEGLContext(egl: EGL10?, display: EGLDisplay?, eglConfig: EGLConfig?):EGLContext? {
        val attribList = intArrayOf(EGL_CONTEXT_CLIENT_VERSION, eglContextVersion, EGL10.EGL_NONE)

        return egl?.eglCreateContext(display, eglConfig,EGL10.EGL_NO_CONTEXT, attribList)
    }

    override fun destroyEGLContext(egl: EGL10?, display: EGLDisplay?, eglContext: EGLContext?) {
        if (egl?.eglDestroyContext(display, eglContext) == false){
            //destroy error
        }
    }
}