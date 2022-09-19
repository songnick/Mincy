//package com.songnick.mincy.gpu
//
//import javax.microedition.khronos.egl.EGL10
//import javax.microedition.khronos.egl.EGLConfig
//import javax.microedition.khronos.egl.EGLDisplay
//import javax.microedition.khronos.egl.EGLSurface
//
///*****
// * @author qfsong
// * Create Time: 2022/7/3
// **/
//interface EGLWindowSurfaceFactory {
//
//    fun createWindowSurface(egl:EGL10?, display:EGLDisplay?,config:EGLConfig?,nativeWindow:Any):EGLSurface?
//
//    fun destroyWindowSurface(egl:EGL10?, display: EGLDisplay?,eglSurface: EGLSurface?)
//}