package com.songnick.mincy.viewmodel

import android.os.Looper
import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.songnick.mincy.data.MediaData
import com.songnick.mincy.data.MediaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class MediaViewModel @Inject constructor(
    private val repository: MediaRepository
) :ViewModel() {

    companion object{
        const val TAG = "MediaListViewModel"
    }
    var permissionRequested by mutableStateOf(false)
    var pictureList = MutableLiveData<List<MediaData>>()
    var videoList = MutableLiveData<List<MediaData>>()

    private fun loadMediaList(){
        viewModelScope.launch(Dispatchers.Main) {
            Log.i(TAG, "current thread: ${Thread.currentThread()} main: ${Looper.getMainLooper().thread}")
            val pictureList =  repository.getPictureList()
            val videoList = repository.getVideoList()
            val mediaList = ArrayList<MediaData>()
            if (pictureList.isNotEmpty() && videoList.isNotEmpty()){
                mediaList.addAll(pictureList)
                mediaList.addAll(videoList)
                mediaList.sortByDescending {
                    it.date
                }
            }
            async {  }
            Log.i(TAG, "after current thread: ${Thread.currentThread()} main: ${videoList.size}")
            _uiState.value = UIState.ShowData(mediaList)
        }
    }

    sealed class UIState(){
        object Loading:UIState()
        class ShowData(mediaList: List<MediaData>):UIState(){
            val mediaList = mediaList
        }
    }

    private val _uiState:MutableStateFlow<UIState> = MutableStateFlow(UIState.Loading)

    val uiState = _uiState.asStateFlow()

    sealed class Action{
        object LoadData:Action()
    }

    fun handleAction(action:Action){
        when(action){
            is Action.LoadData -> {
                val state = uiState.value
                if (state is UIState.Loading){
                    loadMediaList()
                }
            }
        }
    }
}