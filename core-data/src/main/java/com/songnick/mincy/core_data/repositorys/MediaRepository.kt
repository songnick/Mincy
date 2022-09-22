
package com.songnick.mincy.core_data.repositorys
import com.songnick.mincy.core_data.model.Media
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
}