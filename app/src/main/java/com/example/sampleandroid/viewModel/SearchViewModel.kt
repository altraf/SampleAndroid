package com.example.sampleandroid.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sampleandroid.model.Album

class SearchViewModel : ViewModel() {
    private val _albums = MutableLiveData<ArrayList<Album>>()
    private val _selectedAlbum = MutableLiveData<Album>()

    val albums: LiveData<ArrayList<Album>>
        get() = _albums

    val selectedAlbum: LiveData<Album>
        get() = _selectedAlbum

    fun updateAlbums(albums: ArrayList<Album>) {
        _albums.postValue(albums)
    }

    fun updateSelectedAlbum(album: Album) {
        _selectedAlbum.postValue(album)
    }
}
