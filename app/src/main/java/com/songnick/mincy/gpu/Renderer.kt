package com.songnick.mincy.gpu

import javax.microedition.khronos.egl.EGL10
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/*****
 * @author qfsong
 * Create Time: 2022/7/5
 **/
interface Renderer {

    fun onSurfaceCreated(gl: GL10?, config:EGLConfig?)

    fun onSurfaceChanged(gl:GL10?, width:Int?, height:Int?)

    fun onSurfaceDestroyed()

    fun onDrawFrame(gl: GL10?):Boolean
}