package com.songnick.mincy.gpu

import javax.microedition.khronos.egl.EGL10
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.egl.EGLContext
import javax.microedition.khronos.egl.EGLDisplay

/*****
 * @author qfsong
 * Create Time: 2022/7/3
 **/
interface EGLContextFactory {
    fun createEGLContext(egl:EGL10?, display:EGLDisplay?,eglConfig:EGLConfig?):EGLContext?

    fun destroyEGLContext(egl:EGL10?, display:EGLDisplay?,eglContext:EGLContext?)
}