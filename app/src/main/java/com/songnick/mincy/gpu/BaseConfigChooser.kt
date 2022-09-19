package com.songnick.mincy.gpu

import android.opengl.EGL14
import javax.microedition.khronos.egl.EGL10
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.egl.EGLDisplay

/*****
 * @author qfsong
 * Create Time: 2022/7/3
 **/
abstract class BaseConfigChooser(configSpec:IntArray):EGLConfigChooser {
    var mConfigSpec:IntArray? = null

    init {
        mConfigSpec = filterConfigSpec(configSpec = configSpec)
    }

    private fun filterConfigSpec(configSpec: IntArray):IntArray{
        val len = configSpec.size
        val newConfigSpec = IntArray(len+2)
        System.arraycopy(configSpec, 0, newConfigSpec, 0, len - 1)
        newConfigSpec[len-1] = EGL10.EGL_RENDERABLE_TYPE

        newConfigSpec[len] = EGL14.EGL_OPENGL_ES2_BIT

        newConfigSpec[len + 1] = EGL10.EGL_NONE

        return newConfigSpec
    }

    abstract fun chooseConfig(egl: EGL10, display:EGLDisplay, configs:Array<EGLConfig?>):EGLConfig?

    override fun chooseConfig(egl: EGL10, eglDisplay: EGLDisplay):EGLConfig? {
        val numConfig = IntArray(1)
        if (!egl.eglChooseConfig(eglDisplay, mConfigSpec, null, 0, numConfig)) {
            throw IllegalArgumentException("egl choose config failed")
        }

        val numConfigs = numConfig[0]

        if (numConfigs <= 0) {
            throw IllegalArgumentException("No configs match config spec")
        }

        val configs = arrayOfNulls<EGLConfig>(numConfigs)
        if (!egl.eglChooseConfig(eglDisplay, mConfigSpec, configs, numConfigs, numConfig)
        ) {
            throw IllegalArgumentException("eglChooseConfig#2 failed")
        }

        return chooseConfig(egl, display = eglDisplay, configs)
            ?: throw IllegalArgumentException("No config chose")
    }
}