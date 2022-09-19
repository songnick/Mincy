
import com.songnick.mincy.media_choose.model.Media
import kotlinx.coroutines.flow.Flow

/*****
 * @author qfsong
 * Create Time: 2022/9/19
 **/
interface MediaRepository {

    /***
     * get media list
     * */
    fun getMediaList():Flow<List<Media>>
}