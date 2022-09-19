package com.songnick.mincy.media_choose

import MediaRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.songnick.mincy.media_choose.model.Media
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

/*****
 * @author qfsong
 * Create Time: 2022/9/19
 **/
@HiltViewModel
class MediaChooseVM @Inject constructor(
    private val repository: MediaRepository
):ViewModel() {
    var mediaList:Flow<List<Media>>? = null
    init {
        viewModelScope.launch {
            mediaList = repository.getMediaList()
        }
    }
}