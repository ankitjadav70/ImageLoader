package com.example.myapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.DataResponseItem
import com.example.myapplication.data.remote.ApiManager
import com.example.myapplication.data.remote.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val apiManager: ApiManager
): ViewModel() {

    private val _getMediaList = MutableLiveData<ApiResponse<ArrayList<DataResponseItem>>>()
    val getMediaList: MutableLiveData<ApiResponse<ArrayList<DataResponseItem>>> get() = _getMediaList

    fun fetchMedia() {
        viewModelScope.launch {
            _getMediaList.value = apiManager.fetchMedia()
        }
    }

}