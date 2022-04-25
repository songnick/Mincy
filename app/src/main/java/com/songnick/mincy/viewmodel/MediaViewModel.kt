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
import javax.inject.Inject

@HiltViewModel
class MediaViewModel @Inject constructor(
    private val repository: MediaRepository
) :ViewModel() {

    companion object{
        const val TAG = "MediaListViewModel"
    }
    var permissionRequested by mutableStateOf(false)
    init {
        if (permissionRequested){
            loadMediaList()
        }
    }
    private fun loadMediaList(){
        viewModelScope.launch(Dispatchers.Main) {
            delay(1000L*3)
            Log.i(TAG, "current thread: ${Thread.currentThread()} main: ${Looper.getMainLooper().thread}")
            val pictureList =  repository.getPictureList()
            Log.i(TAG, "after current thread: ${Thread.currentThread()} main: ${Looper.getMainLooper().thread}")
            _uiState.value = UIState.ShowData(pictureList)
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