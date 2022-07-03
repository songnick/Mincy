package com.songnick.mincy.gpu

import android.content.Context
import android.graphics.SurfaceTexture
import android.util.AttributeSet
import android.view.TextureView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext

/*****
 * @author qfsong
 * Create Time: 2022/7/2
 **/
class GLTextureView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : TextureView(context, attrs),TextureView.SurfaceTextureListener {

    init {
        super.setSurfaceTextureListener(this)
    }


    @Synchronized
    private fun request(){

    }


    override fun onSurfaceTextureAvailable(p0: SurfaceTexture, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    override fun onSurfaceTextureSizeChanged(p0: SurfaceTexture, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    override fun onSurfaceTextureDestroyed(p0: SurfaceTexture): Boolean {
        TODO("Not yet implemented")
    }

    override fun onSurfaceTextureUpdated(p0: SurfaceTexture) {
        TODO("Not yet implemented")
    }

}