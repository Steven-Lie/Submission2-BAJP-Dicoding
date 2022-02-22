package com.dicoding.moviecatalogue.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.moviecatalogue.data.MovieEntity
import com.dicoding.moviecatalogue.data.TvEntity
import com.dicoding.moviecatalogue.data.repository.CatalogueRepository

class DetailViewModel(private val catalogueRepository: CatalogueRepository) : ViewModel() {
    fun getMovieDetail(movieId: Int): LiveData<MovieEntity> =
        catalogueRepository.getMovieDetail(movieId)

    fun getTvDetail(tvId: Int): LiveData<TvEntity> = catalogueRepository.getTvDetail(tvId)
}