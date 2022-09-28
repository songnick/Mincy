package com.songnick.mincy.core.data.repositorys
import android.app.Application
import android.content.ContentUris
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import javax.inject.Inject
import com.songnick.mincy.core.data.model.Media
import com.songnick.mincy.core.data.model.Image
import com.songnick.mincy.core.data.model.Video
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

    override suspend fun getImageList(): List<Image> = withContext(Dispatchers.IO){
        Log.i(TAG, " current thread picture: ${Thread.currentThread().name}")
        val selection = (MediaStore.MediaColumns.SIZE).plus(">0")
        val args = null
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val list:ArrayList<Image> = ArrayList()
        getMediaList(selection, args, uri).onEach {
            list.add(it as Image)
        }
        list
    }

    override suspend fun getVideoList(): List<Video> = withContext(Dispatchers.IO){
        Log.i(TAG, " current thread video: ${Thread.currentThread().name}")
        val selection = (MediaStore.MediaColumns.SIZE).plus(">0")
        val args = null
        val uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        val list:ArrayList<Video> = ArrayList()
        getMediaList(selection, args, uri).onEach {
            list.add(it as Video)
        }
        list
    }

    private fun getMediaList(selection:String, selectionArgs:Array<String>?,contentUri: Uri):Array<Media>{
        val projections =
            arrayOf(MediaStore.Files.FileColumns._ID, MediaStore.MediaColumns.DATA, MediaStore.MediaColumns.DISPLAY_NAME
                , MediaStore.MediaColumns.DATE_MODIFIED, MediaStore.MediaColumns.MIME_TYPE, MediaStore.MediaColumns.WIDTH,
                MediaStore.MediaColumns.HEIGHT, MediaStore.MediaColumns.SIZE, MediaStore.Video.Media.DURATION,
                MediaStore.Video.Thumbnails.DATA)

        val order = MediaStore.Files.FileColumns.DATE_MODIFIED.plus(" DESC")
        val cursor = context.contentResolver.query(contentUri, projections, selection, selectionArgs, order)
        var array = emptyArray<Media>()
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

                mediaData = if (type.startsWith("image")){
                    val uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                    Image(path = path, name = name, uri = uri, size = size.toInt(), date=date)
                }else{
                    val uri = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id)
                    Video( name = name, uri = uri,path = path, duration = duration, size = size.toInt(), date= date)
                }
                array = array.plus(mediaData!!)
            }while (cursor.moveToNext())
            cursor.close()
        }
        return array
    }

    override suspend fun getMediaList(): List<Media> {
        val pictureList = getImageList()
        val videoList = getVideoList()
        val mediaList = pictureList + videoList

        return mediaList.toMutableList()
    }
}