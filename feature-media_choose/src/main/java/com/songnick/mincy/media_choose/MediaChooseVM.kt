package com.songnick.mincy.media_choose

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.songnick.mincy.core_data.model.Media
import com.songnick.mincy.core_data.repositorys.MediaRepository
import com.songnick.mincy.core_data.result.Result
import com.songnick.mincy.core_data.result.asResult
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

    val _uiState:MutableStateFlow<MediaChooseUiState> = chooseUiState

    init {
        viewModelScope.launch {
            mediaList = repository.getMediaList()
            if (mediaList != null){
                _uiState.value = MediaChooseUiState.Success(mediaList!!)
            }

        }
    }
}

sealed interface MediaChooseUiState{
    object Loading:MediaChooseUiState
    data class Success(val mediaList:List<Media>):MediaChooseUiState
    object Error:MediaChooseUiState
}