package com.songnick.mincy.core_data.repositorys
import android.app.Application
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton
import com.songnick.mincy.core_data.model.Media
import com.songnick.mincy.core_data.model.Picture
import com.songnick.mincy.core_data.model.Video
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/*****
 * @author qfsong
 * Create Time: 2022/9/19
 **/
class LocalMediaRepository @Inject constructor(private val context: Application): MediaRepository {
    companion object{
        const val TAG = "LocalMediaRepository"
    }

    suspend fun getPictureList():List<Media> = withContext(Dispatchers.IO){
        Log.i(TAG, " current thread picture: ${Thread.currentThread().name}")
        val selection = (MediaStore.MediaColumns.SIZE).plus(">0")
        val args = null
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        getMediaList(selection, args, uri)
    }

    private fun getMediaList(selection:String, selectionArgs:Array<String>?,contentUri: Uri):List<Media>{
        val projections =
            arrayOf(MediaStore.Files.FileColumns._ID, MediaStore.MediaColumns.DATA, MediaStore.MediaColumns.DISPLAY_NAME
                , MediaStore.MediaColumns.DATE_MODIFIED, MediaStore.MediaColumns.MIME_TYPE, MediaStore.MediaColumns.WIDTH,
                MediaStore.MediaColumns.HEIGHT, MediaStore.MediaColumns.SIZE, MediaStore.Video.Media.DURATION,
                MediaStore.Video.Thumbnails.DATA)
        val array = ArrayList<Media>()
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
                var mediaData:Media? = null
                if (type.startsWith("image*")){
                    mediaData = Picture(path = path, name, size.toInt())
                }else{
                    mediaData = Video( name,path = path, duration, size.toInt())
                }
                array.add(mediaData!!)
            }while (cursor.moveToNext())
            cursor.close()
        }
        return array
    }

    override suspend fun getMediaList(): List<Media> {

        return getPictureList()
    }
}