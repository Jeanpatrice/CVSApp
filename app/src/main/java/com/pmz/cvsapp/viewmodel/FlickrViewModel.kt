package com.pmz.cvsapp.viewmodel

import androidx.lifecycle.*
import com.pmz.cvsapp.model.remote.response.FlickrResponse
import com.pmz.cvsapp.model.remote.response.Item
import com.pmz.cvsapp.model.repository.FlickrRepository
import com.pmz.cvsapp.utils.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlickrViewModel @Inject constructor(
    private val flickrRepository: FlickrRepository
): ViewModel() {

    private var _images: MutableLiveData<ApiState<FlickrResponse>> = MutableLiveData()
    val images: LiveData<ApiState<FlickrResponse>> = _images

    private var _queries: MutableLiveData<List<String>> = MutableLiveData()
    val queries: LiveData<List<String>> = _queries

    var query: String = ""
    set(value) {
        fetchImages(value)
        field = value
    }

    var selectedImage: Item? = null

    fun getSearchQueries() {
        viewModelScope.launch {
            flickrRepository.getAllSearchQueries().collect { queries ->
                _queries.postValue(queries.map { it.query })
            }
        }
    }

    private fun fetchImages(query: String) {
        viewModelScope.launch {
            flickrRepository.fetchImages(query).collect {
                _images.postValue(it)
            }
        }
    }


}