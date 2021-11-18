package com.songnick.mincy.viewmodel

import android.os.Looper
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.songnick.mincy.data.MediaData
import com.songnick.mincy.data.MediaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MediaListViewModel @Inject constructor(
    private val repository: MediaRepository
) :ViewModel() {

   val media = MutableLiveData<List<MediaData>>()

    init {
        viewModelScope.launch {
            Log.i("TAG", "current thread: ${Thread.currentThread()} main: ${Looper.getMainLooper().thread}")
            val mediaList = withContext(Dispatchers.IO){
                repository.getAllMediaList()
            }
            media.value = mediaList
        }
    }
}