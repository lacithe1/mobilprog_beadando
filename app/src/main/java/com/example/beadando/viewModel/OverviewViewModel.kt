package com.example.beadando.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beadando.network.Photos
import com.example.beadando.network.PictureApi
import kotlinx.coroutines.launch

enum class ApiStatus { LOADING, ERROR, DONE }

class OverviewViewModel : ViewModel() {

    private val _status = MutableLiveData<ApiStatus>()

    val status: LiveData<ApiStatus> = _status

    private val _photos = MutableLiveData<List<Photos>>()

    val photos: LiveData<List<Photos>> = _photos

    init {
        getMarsPhotos()
    }

    private fun getMarsPhotos() {

        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                _photos.value = PictureApi.retrofitService.getPhotos()
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                _photos.value = listOf()
            }
        }
    }
}