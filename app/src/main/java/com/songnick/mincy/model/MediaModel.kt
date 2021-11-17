package com.songnick.mincy.model

import android.content.ContentResolver
import android.content.Context
import android.provider.MediaStore

class MediaModel(context:Context) {

    companion object{
        private const val TAG = "MediaModel"
        private val URI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    }

    private val  contentResolver:ContentResolver

    init {
        contentResolver = context.contentResolver
    }


    private fun readMedia(){
        contentResolver.query(URI, arrayOf(

        ), null, null, null)?.let {
            it.moveToFirst()

        }
    }

}