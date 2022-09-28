
package com.songnick.mincy.core.data.repositorys
import com.songnick.mincy.core.data.model.Image
import com.songnick.mincy.core.data.model.Media
import com.songnick.mincy.core.data.model.Video
import kotlinx.coroutines.flow.Flow

/*****
 * @author qfsong
 * Create Time: 2022/9/19
 **/
interface MediaRepository {

    /***
     * get media list
     * */
    suspend fun getMediaList():List<Media>

    suspend fun getImageList():List<Image>

    suspend fun getVideoList():List<Video>
}