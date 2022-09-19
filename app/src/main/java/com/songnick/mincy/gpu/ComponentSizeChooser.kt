package com.songnick.mincy.gpu

import javax.microedition.khronos.egl.EGL10
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.egl.EGLDisplay

/*****
 * @author qfsong
 * Create Time: 2022/7/4
 **/
open class ComponentSizeChooser( redSize:Int, greenSize:Int, blueSize:Int,
                           alphaSize:Int, depthSize:Int, stencilSize:Int): BaseConfigChooser(
    intArrayOf(
        EGL10.EGL_RED_SIZE, redSize,
        EGL10.EGL_GREEN_SIZE, greenSize,
        EGL10.EGL_BLUE_SIZE, blueSize,
        EGL10.EGL_ALPHA_SIZE, alphaSize,
        EGL10.EGL_DEPTH_SIZE, depthSize,
        EGL10.EGL_STENCIL_SIZE, stencilSize,
        EGL10.EGL_NONE
    ) ) {


    private var mValue: IntArray = IntArray(1)

    // Subclasses can adjust these values:
     private val mRedSize = redSize
     private val mGreenSize = greenSize
     private val mBlueSize = blueSize
     private val mAlphaSize = alphaSize
     private val mDepthSize = depthSize
     private val mStencilSize = stencilSize

    override fun chooseConfig(egl: EGL10, display: EGLDisplay, configs: Array<EGLConfig?>):EGLConfig? {
        for (config in configs) {
            val d: Int = findConfigAttrib(
                egl, display, config,
                EGL10.EGL_DEPTH_SIZE, 0
            )
            val s: Int = findConfigAttrib(
                egl, display, config,
                EGL10.EGL_STENCIL_SIZE, 0
            )
            if (d >= mDepthSize && s >= mStencilSize) {
                val r: Int = findConfigAttrib(
                    egl, display, config,
                    EGL10.EGL_RED_SIZE, 0
                )
                val g: Int = findConfigAttrib(
                    egl, display, config,
                    EGL10.EGL_GREEN_SIZE, 0
                )
                val b: Int = findConfigAttrib(
                    egl, display, config,
                    EGL10.EGL_BLUE_SIZE, 0
                )
                val a: Int = findConfigAttrib(
                    egl, display, config,
                    EGL10.EGL_ALPHA_SIZE, 0
                )
                if (r == mRedSize && g == mGreenSize
                    && b == mBlueSize && a == mAlphaSize
                ) {
                    return config
                }
            }
        }
        return null
    }
    private fun findConfigAttrib(
        egl: EGL10, display: EGLDisplay,
        config: EGLConfig?, attribute: Int, defaultValue: Int
    ): Int {
        return if (egl.eglGetConfigAttrib(display, config, attribute, mValue)) {
            mValue[0]
        } else defaultValue
    }

    class SimpleEGLConfigChooser(withDepthBuffer:Boolean): ComponentSizeChooser(8, 8, 8, 0,if (withDepthBuffer) 16 else 0, 0 )
}