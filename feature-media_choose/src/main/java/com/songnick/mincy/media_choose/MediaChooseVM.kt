package com.songnick.mincy.media_choose

import androidx.compose.runtime.MutableState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.songnick.mincy.core_data.model.Media
import com.songnick.mincy.core_data.repositorys.MediaRepository
import com.songnick.mincy.media_choose.component.ImageCardData
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

    private var mediaList:List<Media>? = null

    private var chooseUiState = MutableStateFlow<MediaChooseUiState>(MediaChooseUiState.Loading)

    val uiState:MutableStateFlow<MediaChooseUiState> = chooseUiState

    val selectedList:MutableStateFlow<List<ImageCardData>> = MutableStateFlow(emptyList())

    val selectIndexEvent:MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)

    var curIndex:Int = 0

    init {
        viewModelScope.launch {
            mediaList = repository.getMediaList()
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