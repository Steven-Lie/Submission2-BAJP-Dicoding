package com.dicoding.moviecatalogue.data.repository

import androidx.lifecycle.LiveData
import com.dicoding.moviecatalogue.data.CatalogueEntity
import com.dicoding.moviecatalogue.data.MovieEntity
import com.dicoding.moviecatalogue.data.TvEntity

interface CatalogueDataSource {
    fun getMovie(): LiveData<List<CatalogueEntity>>

    fun getMovieDetail(movieId: Int): LiveData<MovieEntity>

    fun getTv(): LiveData<List<CatalogueEntity>>

    fun getTvDetail(tvId: Int): LiveData<TvEntity>
}