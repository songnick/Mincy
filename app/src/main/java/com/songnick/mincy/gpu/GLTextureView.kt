//package com.songnick.mincy.gpu
//
//import android.content.Context
//import android.graphics.SurfaceTexture
//import android.util.AttributeSet
//import android.view.TextureView
//import java.lang.ref.WeakReference
//
///*****
// * @author qfsong
// * Create Time: 2022/7/2
// **/
//class GLTextureView @JvmOverloads constructor(
//    context: Context, attrs: AttributeSet? = null
//) : TextureView(context, attrs),TextureView.SurfaceTextureListener {
//
//    companion object{
//        const val RENDERMODE_WHEN_DIRTY = 0
//
//        /**
//         * The renderer is called
//         * continuously to re-render the scene.
//         *
//         * @see .getRenderMode
//         * @see .setRenderMode
//         */
//        const val RENDERMODE_CONTINUOUSLY = 1
//    }
//
//    var render:Renderer? = null
//    var eglContextFactory:EGLContextFactory? = null
//    var eglWindowSurfaceFactory:EGLWindowSurfaceFactory? = null
//    var eglConfigChooser:EGLConfigChooser? = null
//    var preserveEGLContextOnPause = false
//    var glThread:GLThread? = null
//    private var detached:Boolean = false
//
//    init {
//        super.setSurfaceTextureListener(this)
//    }
//
//    fun setRenderer(renderer:Renderer){
//        checkRenderThreadState()
//        this.render = renderer
//        if (eglConfigChooser == null){
//            eglConfigChooser = ComponentSizeChooser.SimpleEGLConfigChooser(true)
//        }
//        if (eglContextFactory == null){
//            eglContextFactory = EGLContextFactoryImpl()
//        }
//        if (eglWindowSurfaceFactory == null){
//            eglWindowSurfaceFactory = EGLWindowSurfaceFactoryImpl()
//        }
//        this.render = render
//        glThread = GLThread(WeakReference(this))
//        glThread?.start()
//    }
//
//    private fun checkRenderThreadState(){
//        glThread?.let {
//            throw IllegalStateException("set renderer has already been called for this instance")
//        }
//    }
//
//    fun setEGLConfigChooser(redSize:Int, greenSize:Int, blueSize:Int,
//                            alphaSize:Int, depthSize:Int, stencilSize:Int){
//        this.eglConfigChooser = ComponentSizeChooser(redSize, greenSize, blueSize, alphaSize, depthSize, stencilSize)
//    }
//
//    var eglContextClientVersion:Int? = null
//    set(value) {
//        checkRenderThreadState()
//        field = value
//    }
//
//    var renderMode:Int = 0
//
//    fun requestRender(){
//        glThread?.requestRender()
//    }
//
//    override fun onSurfaceTextureAvailable(p0: SurfaceTexture, width: Int, height: Int) {
//        glThread?.surfaceCreated()
//        onSurfaceTextureSizeChanged(p0, width,height)
//
//    }
//
//    override fun onSurfaceTextureSizeChanged(p0: SurfaceTexture, width: Int, height: Int) {
//        glThread?.onWindowResize(width, height)
//    }
//
//    override fun onSurfaceTextureDestroyed(p0: SurfaceTexture): Boolean {
//        glThread?.surfaceDestroyed()
//        render?.apply {
//            onSurfaceDestroyed()
//        }
//        return true
//    }
//
//    override fun onSurfaceTextureUpdated(p0: SurfaceTexture) {
//
//    }
//
//    fun onResume(){
//        glThread?.onResume()
//    }
//
//
//    override fun onAttachedToWindow() {
//        super.onAttachedToWindow()
//        if (detached && render != null){
//            var renderMode = RENDERMODE_CONTINUOUSLY
//            glThread?.let {
//                renderMode = it.getRenderMode()
//            }
//            glThread = GLThread(WeakReference(this))
//            if (renderMode != RENDERMODE_CONTINUOUSLY){
//                glThread?.setRenderMode(renderMode)
//            }
//            glThread?.start()
//        }
//        detached = false
//    }
//
//    override fun onDetachedFromWindow() {
//        glThread?.let {
//            it.requestExitAndWait()
//        }
//        detached = true
//        super.onDetachedFromWindow()
//    }
//}