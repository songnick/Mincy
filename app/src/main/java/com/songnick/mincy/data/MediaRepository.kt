package com.songnick.mincy.data

import android.app.Application
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Singleton
class MediaRepository @Inject constructor(context:Application) {
    companion object{
        const val TAG = "MediaRepository"
    }

   private var context:Application = context

    suspend fun getPictureList():List<MediaData> = withContext(Dispatchers.IO){
        Log.i(TAG, " current thread picture: ${Thread.currentThread().name}")
        val selection = (MediaStore.MediaColumns.SIZE).plus(">0")
        val args = null
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        getMediaList(selection, args, uri)
    }

    suspend fun getVideoList():List<MediaData> = withContext(Dispatchers.IO){
        Log.i(TAG, " current thread video: ${Thread.currentThread().name}")
        val selection = (MediaStore.MediaColumns.SIZE).plus(">0")
        val args = null
        val uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        getMediaList(selection, args, uri)
    }

    private fun getMediaList(selection:String, selectionArgs:Array<String>):List<MediaData>{
        val projections =
            arrayOf(MediaStore.Files.FileColumns._ID, MediaStore.MediaColumns.DATA, MediaStore.MediaColumns.DISPLAY_NAME
                , MediaStore.MediaColumns.DATE_MODIFIED, MediaStore.MediaColumns.MIME_TYPE, MediaStore.MediaColumns.WIDTH,
                MediaStore.MediaColumns.HEIGHT, MediaStore.MediaColumns.SIZE, MediaStore.Video.Media.DURATION,
                MediaStore.Video.Thumbnails.DATA)
        val contentUri = MediaStore.Files.getContentUri("external")
        val array = ArrayList<MediaData>()
        val order = MediaStore.Files.FileColumns.DATE_MODIFIED.plus(" DESC")

        val cursor = context.contentResolver.query(contentUri, projections, selection, selectionArgs, order)
        cursor?.let {
            it.moveToFirst()
            val idC = it.getColumnIndex(MediaStore.Files.FileColumns._ID)
            val pathC = it.getColumnIndex(MediaStore.MediaColumns.DATA)
            val nameC = it.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME)
            val dateC = it.getColumnIndex(MediaStore.MediaColumns.DATE_MODIFIED)
            val mimeTypeC = it.getColumnIndex(MediaStore.MediaColumns.MIME_TYPE)
            val sizeC = it.getColumnIndex(MediaStore.MediaColumns.SIZE)
            val durationC = it.getColumnIndex(MediaStore.Video.VideoColumns.DURATION)
            val widthC = it.getColumnIndex(MediaStore.MediaColumns.WIDTH)
            val heightC = it.getColumnIndex(MediaStore.MediaColumns.HEIGHT)
            val thumbC = it.getColumnIndex(MediaStore.Video.Thumbnails.DATA)
            var index = 0
            do{
                val id = cursor.getLong(idC)
                val path = cursor.getString(pathC)
                val name = cursor.getString(nameC)
                val date = cursor.getLong(dateC)
                val type = cursor.getString(mimeTypeC)
                val size = cursor.getLong(sizeC)
                val duration = cursor.getLong(durationC)
                val width = cursor.getInt(widthC)
                val height = cursor.getInt(heightC)
                val thumbnail = cursor.getString(thumbC)
                val mediaData = MediaData(path, name, date, type)
                mediaData.thumbnail = thumbnail
                mediaData.duration = duration
                Log.i(TAG, " path: $mediaData thumbnail $thumbnail")
                array.add(mediaData)
            }while (cursor.moveToNext())
            cursor.close()
        }
        return array
    }


    private fun getMediaList(selection:String, selectionArgs:Array<String>?,contentUri:Uri):List<MediaData>{
        val projections =
            arrayOf(MediaStore.Files.FileColumns._ID, MediaStore.MediaColumns.DATA, MediaStore.MediaColumns.DISPLAY_NAME
            , MediaStore.MediaColumns.DATE_MODIFIED, MediaStore.MediaColumns.MIME_TYPE, MediaStore.MediaColumns.WIDTH,
                        MediaStore.MediaColumns.HEIGHT, MediaStore.MediaColumns.SIZE, MediaStore.Video.Media.DURATION,
                MediaStore.Video.Thumbnails.DATA)
        val array = ArrayList<MediaData>()
        val order = MediaStore.Files.FileColumns.DATE_MODIFIED.plus(" DESC")
        val cursor = context.contentResolver.query(contentUri, projections, selection, selectionArgs, order)
        cursor?.let {
            it.moveToFirst()
            val idC = it.getColumnIndex(MediaStore.Files.FileColumns._ID)
            val pathC = it.getColumnIndex(MediaStore.MediaColumns.DATA)
            val nameC = it.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME)
            val dateC = it.getColumnIndex(MediaStore.MediaColumns.DATE_MODIFIED)
            val mimeTypeC = it.getColumnIndex(MediaStore.MediaColumns.MIME_TYPE)
            val sizeC = it.getColumnIndex(MediaStore.MediaColumns.SIZE)
            val durationC = it.getColumnIndex(MediaStore.Video.VideoColumns.DURATION)
            val widthC = it.getColumnIndex(MediaStore.MediaColumns.WIDTH)
            val heightC = it.getColumnIndex(MediaStore.MediaColumns.HEIGHT)
            val thumbC = it.getColumnIndex(MediaStore.Video.Thumbnails.DATA)
            do{
                val id = cursor.getLong(idC)
                val path = cursor.getString(pathC)
                val name = cursor.getString(nameC)
                val date = cursor.getLong(dateC)
                val type = cursor.getString(mimeTypeC)
                val size = cursor.getLong(sizeC)
                val duration = cursor.getLong(durationC)
                val width = cursor.getInt(widthC)
                val height = cursor.getInt(heightC)
                val thumbnail = cursor.getString(thumbC)
                val mediaData = MediaData(path, name, date, type)
                mediaData.thumbnail = thumbnail
                mediaData.duration = duration
                array.add(mediaData)
            }while (cursor.moveToNext())
            cursor.close()
        }
        return array
    }

}