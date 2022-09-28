package com.songnick.mincy.feature.media_choose

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.songnick.mincy.core.data.model.Image
import com.songnick.mincy.core.data.model.Media
import com.songnick.mincy.core.data.repositorys.MediaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/*****
 * @author qfsong
 * Create Time: 2022/9/19
 **/

@HiltViewModel
class MediaChooseVM @Inject constructor(
    private val repository: MediaRepository,
    private val savedStateHandle: SavedStateHandle
):ViewModel() {

    var mediaList:List<Media>? = null

    var imageList:List<Image>? = null

    private var chooseUiState = MutableStateFlow<MediaChooseUiState>(MediaChooseUiState.Loading)

    val uiState:MutableStateFlow<MediaChooseUiState> = chooseUiState

    val selectedList:MutableStateFlow<List<Media>> = MutableStateFlow(emptyList())

    var curIndex:Int = 0

    init {
        viewModelScope.launch {
            mediaList = repository.getMediaList()
            imageList = repository.getImageList()
            if (mediaList != null){
                uiState.value = MediaChooseUiState.Success(mediaList!!)
            }
        }

    }
}

sealed interface SelectIndexEvent{
    object Increase:SelectIndexEvent
    object Decrease:SelectIndexEvent
    object Valid:SelectIndexEvent
}

sealed interface MediaChooseUiState{
    object Loading:MediaChooseUiState
    data class Success(val mediaList:List<Media>):MediaChooseUiState
    object Error:MediaChooseUiState
}