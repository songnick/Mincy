
import com.songnick.mincy.media_choose.model.Media
import com.songnick.mincy.media_choose.repository.MediaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

/*****
 * @author qfsong
 * Create Time: 2022/9/19
 **/
class LocalMediaRepository @Inject constructor(): MediaRepository {
    override fun getMediaList(): Flow<List<Media>> {

        return flowOf()
    }
}