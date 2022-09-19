package com.songnick.mincy.gpu

import javax.microedition.khronos.egl.EGL10
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.egl.EGLDisplay

/*****
 * @author qfsong
 * Create Time: 2022/7/3
 **/
interface EGLConfigChooser {
    fun chooseConfig(egl:EGL10, eglDisplay: EGLDisplay):EGLConfig?
}