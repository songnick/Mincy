//package com.songnick.mincy.gpu
//
//import android.opengl.GLES10
//import com.songnick.mincy.gpu.GLTextureView.Companion.RENDERMODE_CONTINUOUSLY
//import okhttp3.internal.notifyAll
//import okhttp3.internal.wait
//import java.lang.ref.WeakReference
//import javax.microedition.khronos.egl.EGL10
//import javax.microedition.khronos.egl.EGL11
//import javax.microedition.khronos.opengles.GL10
//
///*****
// * @author qfsong
// * Create Time: 2022/7/5
// **/
//class GLThread(glTextureViewRef: WeakReference<GLTextureView>): Thread() {
//
//    companion object{
//        val glThreadManager = GLThreadManager()
//    }
//
//    private val glTextureViewRef = glTextureViewRef
//
//    private var width= 0
//    private var height = 0
//    private var shouldExit = false
//     var exited = false
//    private var requestPaused = false
//    private var paused = false
//    private var hasSurface = false
//    private var surfaceIsBad = false
//    private var haveEGLContext = false
//    private var haveEGLSurface = false
//    private var waitingForSurface = false
//    private var finishedCreatingEGLSurface = false
//    private var shouldReleaseEGLContext = false
//    private var renderMode = RENDERMODE_CONTINUOUSLY
//    private var requestRender = true
//    private var renderComplete = false
//    private var eventQueue = mutableListOf<Runnable>()
//
//    private var sizeChanged = true
//    var eglHelper:EGLHelper? = null
//    override fun run() {
//        name = "GLThread:$id"
//
//    }
//
//    private fun guardedRun(){
//        try {
//            eglHelper = EGLHelper(glTextureViewRef)
//            haveEGLContext = false
//            haveEGLSurface = false
//
//            var gl: GL10? = null
//            var createEglContext = false
//            var createEGLSurface = false
//            var createGLInterface = false
//            var lostEGLContext = false
//            var sizeChanged = false
//            var wantRenderNotification = false
//            var doRenderNotification = false
//            var askedToReleaseEGLContext = false
//            var w = 0
//            var h = 0
//            var event:Runnable? = null
//
//            while (true){
//                synchronized(glThreadManager){
//                    while (true){
//                        if (shouldExit){
//                            return
//                        }
//                        if (eventQueue.isNotEmpty()){
//                            event = eventQueue.removeAt(0)
//                            break
//                        }
//
//                        var pausing = false
//                        if (paused != requestPaused){
//                            pausing = requestPaused
//                            paused = requestPaused
//                            glThreadManager.notifyAll()
//                        }
//
//                        if (shouldReleaseEGLContext){
//                            stopEGLSurfaceLocked()
//                            stopEglContextLocked()
//                            shouldReleaseEGLContext = false
//                            askedToReleaseEGLContext = true
//                        }
//
//                        if (lostEGLContext){
//                            stopEGLSurfaceLocked()
//                            stopEglContextLocked()
//                            lostEGLContext = false
//                        }
//                        if (pausing && haveEGLSurface){
//                            stopEGLSurfaceLocked()
//                        }
//
//                        if (pausing && haveEGLContext){
//                            val view = glTextureViewRef.get()
//                            val preserveEGLContextOnPause = view?.preserveEGLContextOnPause ?: false
//                            if (!preserveEGLContextOnPause || glThreadManager.shouldReleaseEGLContextWhenPausing()){
//                                stopEglContextLocked()
//                            }
//                        }
//
//                        if (pausing){
//                            if (glThreadManager.shouldTerminateEGLWhenPausing()){
//                                eglHelper?.finish()
//                            }
//                        }
//
//                        if (!hasSurface && !waitingForSurface){
//                            if (haveEGLSurface){
//                                stopEGLSurfaceLocked()
//                            }
//                            waitingForSurface = true
//                            surfaceIsBad = false
//                            glThreadManager.notifyAll()
//                        }
//
//                        if (hasSurface && waitingForSurface){
//                            waitingForSurface =false
//                            glThreadManager.notifyAll()
//                        }
//                        if (doRenderNotification){
//                            wantRenderNotification = false
//                            doRenderNotification = false
//                            renderComplete = true
//                            glThreadManager.notifyAll()
//                        }
//                        if (readyToDraw()){
//                            if (!haveEGLContext){
//                                if (askedToReleaseEGLContext){
//                                    askedToReleaseEGLContext = false
//                                }else if (glThreadManager.tryAcquireEGLContextLocked(this)){
//                                    try {
//                                        eglHelper?.start()
//                                    }catch (e:RuntimeException){
//                                        glThreadManager.releaseEGLContextLocked(this)
//                                        throw e
//                                    }
//                                    haveEGLContext = true
//                                    createEglContext = true
//                                    glThreadManager.notifyAll()
//                                }
//                            }
//                            if (haveEGLContext && !haveEGLSurface){
//                                haveEGLSurface = true
//                                createEGLSurface = true
//                                createGLInterface = true
//                                sizeChanged = true
//                            }
//                            if (haveEGLSurface){
//                                if (this.sizeChanged){
//                                    sizeChanged = true
//                                    w = width
//                                    h = height
//                                    wantRenderNotification = true
//                                    createEGLSurface = true
//                                    this.sizeChanged = false
//                                }
//                                requestRender = false
//                                glThreadManager.notifyAll()
//                                break
//                            }
//                        }
//
//                        glThreadManager.notifyAll()
//                    }
//                }
//                if (event != null){
//                    event?.run()
//                    event = null
//                    continue
//                }
//
//                if (createEGLSurface){
//                    if (eglHelper?.createSurface() == true){
//                        synchronized(glThreadManager){
//                            finishedCreatingEGLSurface = true
//                            glThreadManager.notifyAll()
//                        }
//                    }else{
//                        synchronized(glThreadManager){
//                            finishedCreatingEGLSurface = true
//                            surfaceIsBad = true
//                            glThreadManager.notifyAll()
//                        }
//                        continue
//                    }
//                    createEGLSurface = false
//                }
//
//                if (createGLInterface){
//                    gl = eglHelper?.createGL() as GL10
//                    glThreadManager.checkGLDriver(gl)
//                    createGLInterface = false
//                }
//
//                if (createEglContext){
//                    val view = glTextureViewRef.get()
//                    view?.let {
//                        it.render?.onSurfaceCreated(gl, eglHelper?.eglConfig)
//                    }
//                    createEglContext = false
//                }
//                if (sizeChanged){
//                    val view = glTextureViewRef.get()
//                    view?.let {
//                        it.render?.onSurfaceChanged(gl, w, h)
//                    }
//                    sizeChanged = false
//                }
//
//                var needSwap = false
//
//                val view = glTextureViewRef.get()
//                view?.let {
//                    needSwap = it.render?.onDrawFrame(gl) == true
//                }
//                if (needSwap){
//                    when(eglHelper?.swap()){
//                        EGL11.EGL_CONTEXT_LOST->
//                            lostEGLContext = true
//                        else ->
//                            synchronized(glThreadManager){
//                                surfaceIsBad = true
//                                glThreadManager.notifyAll()
//                            }
//                    }
//                }
//                if (wantRenderNotification){
//                    doRenderNotification = true
//                }
//            }
//        }finally {
//            synchronized(glThreadManager){
//                stopEGLSurfaceLocked()
//                stopEglContextLocked()
//            }
//        }
//    }
//
//    private fun stopEGLSurfaceLocked(){
//        if (haveEGLSurface){
//            haveEGLSurface = false
//            eglHelper?.destroySurface()
//        }
//    }
//
//    private fun stopEglContextLocked() {
//        if (haveEGLContext) {
//            eglHelper?.finish()
//            haveEGLContext = false
////            glThreadManager.releaseEglContextLocked(this)
//        }
//    }
//
//    fun ableToDraw():Boolean{
//
//        return haveEGLContext && haveEGLSurface && readyToDraw()
//    }
//
//    private fun readyToDraw():Boolean{
//
//        return !paused && hasSurface && !surfaceIsBad
//                && width>0 && height>0
//                && (requestRender || (renderMode == RENDERMODE_CONTINUOUSLY))
//    }
//
//    fun setRenderMode(renderMode:Int){
//        synchronized(glThreadManager){
//            this.renderMode = renderMode
//            glThreadManager.notifyAll()
//        }
//    }
//
//    fun getRenderMode():Int{
//
//        return this.renderMode
//    }
//
//    fun requestRender(){
//        synchronized(glThreadManager){
//            requestRender = true
//            glThreadManager.notifyAll()
//        }
//    }
//
//    fun surfaceCreated(){
//        synchronized(glThreadManager){
//            hasSurface = true
//            finishedCreatingEGLSurface = false
//            glThreadManager.notifyAll()
//            while (waitingForSurface && !finishedCreatingEGLSurface && !exited){
//                try {
//                    glThreadManager.wait()
//                }catch (e:Exception){
//                    e.printStackTrace()
//                }
//            }
//
//        }
//    }
//
//    fun surfaceDestroyed(){
//        synchronized(glThreadManager){
//            hasSurface = false
//            glThreadManager.notifyAll()
//            while ((!waitingForSurface) && (!exited)){
//                try {
//                    glThreadManager.wait()
//                }catch (e:InterruptedException){}
//                currentThread().interrupt()
//            }
//        }
//    }
//
//    fun onPause(){
//        synchronized(glThreadManager){
//            requestPaused = true
//            glThreadManager.notifyAll()
//            while ((!exited) && !paused){
//                try {
//                    glThreadManager.wait()
//                }catch (e:InterruptedException){
//                    currentThread().interrupt()
//                }
//            }
//
//        }
//    }
//
//    fun onResume(){
//        synchronized(glThreadManager){
//            requestPaused = false
//            requestRender = true
//            renderComplete = false
//            glThreadManager.notifyAll()
//            while (!exited && paused && !renderComplete){
//                try {
//                    glThreadManager.wait()
//                }catch (e:InterruptedException){
//                    currentThread().interrupt()
//                }
//            }
//
//        }
//    }
//
//    fun onWindowResize(w:Int, h:Int){
//        synchronized(glThreadManager){
//            width = w
//            height = h
//            sizeChanged = true
//            requestRender = true
//            renderComplete = false
//            glThreadManager.notifyAll()
//            while (!exited && !paused && !renderComplete && ableToDraw()){
//                try {
//                    glThreadManager.wait()
//                }catch (e:InterruptedException){
//                    currentThread().interrupt()
//                }
//            }
//        }
//    }
//
//    fun requestExitAndWait(){
//        synchronized(glThreadManager){
//            shouldExit = true
//            glThreadManager.notifyAll()
//            while (!exited){
//                try {
//                    glThreadManager.wait()
//                }catch (e:InterruptedException){
//                    currentThread().interrupt()
//                }
//            }
//
//        }
//    }
//
//    fun requestReleaseEglContextLocked(){
//        shouldReleaseEGLContext = true
//        glThreadManager.notifyAll()
//    }
//
//    fun queueEvent(r:Runnable){
//        synchronized(glThreadManager){
//            eventQueue.add(r)
//            glThreadManager.notifyAll()
//        }
//    }
//}