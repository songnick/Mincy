package com.songnick.mincy.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.songnick.mincy.data.MediaData
import com.songnick.mincy.data.MediaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MediaListViewModel @Inject constructor(
    private val repository: MediaRepository
) :ViewModel() {

   val media = MutableLiveData<List<MediaData>>()

    init {
        viewModelScope.launch {
            media.value = repository.getAllMediaList()
        }
    }
}