package com.songnick.mincy.gpu

import java.lang.Exception
import javax.microedition.khronos.egl.*

/*****
 * @author qfsong
 * Create Time: 2022/7/3
 **/
class EGLWindowSurfaceFactoryImpl:EGLWindowSurfaceFactory {
    override fun createWindowSurface(
        egl: EGL10,
        display: EGLDisplay,
        config: EGLConfig,
        nativeWindow: Any
    ): EGLSurface? {
        var windowSurface:EGLSurface? = null
        try {
           windowSurface = egl.eglCreateWindowSurface(display,config,nativeWindow, null)
        }catch (e:Exception){
            e.printStackTrace()
        }
        return windowSurface
    }

    override fun destroyWindowSurface(egl: EGL10, display: EGLDisplay, eglSurface: EGLSurface) {
        egl.eglDestroySurface(display, eglSurface)
    }

}