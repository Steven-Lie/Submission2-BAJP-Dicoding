package com.dicoding.moviecatalogue.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.moviecatalogue.data.CatalogueEntity
import com.dicoding.moviecatalogue.data.MovieEntity
import com.dicoding.moviecatalogue.data.TvEntity
import com.dicoding.moviecatalogue.data.api.response.MovieDetailResponse
import com.dicoding.moviecatalogue.data.api.response.MovieResultsItem
import com.dicoding.moviecatalogue.data.api.response.TvDetailResponse
import com.dicoding.moviecatalogue.data.api.response.TvResultsItem

class FakeCatalogueRepository(private val remoteDataSource: RemoteDataSource) :
    CatalogueDataSource {

    override fun getMovie(): LiveData<List<CatalogueEntity>> {
        val movieResults = MutableLiveData<List<CatalogueEntity>>()
        remoteDataSource.getMovie(object : RemoteDataSource.LoadMovieCallback {
            override fun onMovieReceived(movieResponse: List<MovieResultsItem>) {
                val movieList = ArrayList<CatalogueEntity>()
                for (response in movieResponse) {
                    val movie = CatalogueEntity(
                        response.id,
                        response.title,
                        response.voteAverage,
                        response.overview,
                        response.posterPath
                    )
                    movieList.add(movie)
                }
                movieResults.postValue(movieList)
            }
        })
        return movieResults
    }

    override fun getMovieDetail(movieId: Int): LiveData<MovieEntity> {
        val movieDetailResult = MutableLiveData<MovieEntity>()
        remoteDataSource.getMovieDetail(movieId, object : RemoteDataSource.LoadMovieDetailCallback {
            override fun onMovieDetailReceived(movieDetailResponse: MovieDetailResponse) {
                val movie = MovieEntity(
                    movieDetailResponse.id,
                    movieDetailResponse.title,
                    movieDetailResponse.releaseDate,
                    movieDetailResponse.genres.joinToString(
                        separator = ", ",
                        transform = { it.name }),
                    "${movieDetailResponse.runtime} minutes",
                    movieDetailResponse.tagline,
                    movieDetailResponse.overview,
                    movieDetailResponse.voteAverage,
                    movieDetailResponse.posterPath,
                    movieDetailResponse.backdropPath
                )
                movieDetailResult.postValue(movie)
            }
        })
        return movieDetailResult
    }

    override fun getTv(): LiveData<List<CatalogueEntity>> {
        val tvResults = MutableLiveData<List<CatalogueEntity>>()
        remoteDataSource.getTv(object : RemoteDataSource.LoadTvCallback {
            override fun onTvReceived(tvResponse: List<TvResultsItem>) {
                val tvList = ArrayList<CatalogueEntity>()
                for (response in tvResponse) {
                    val tv = CatalogueEntity(
                        response.id,
                        response.title,
                        response.voteAverage,
                        response.overview,
                        response.posterPath
                    )
                    tvList.add(tv)
                }
                tvResults.postValue(tvList)
            }
        })
        return tvResults
    }

    override fun getTvDetail(tvId: Int): LiveData<TvEntity> {
        val tvDetailResult = MutableLiveData<TvEntity>()
        remoteDataSource.getTvDetail(tvId, object : RemoteDataSource.LoadTvDetailCallback {
            override fun onTvDetailReceived(tvDetailResponse: TvDetailResponse) {
                val tv = TvEntity(
                    tvDetailResponse.id,
                    tvDetailResponse.name,
                    "${tvDetailResponse.firstAirDate} - ${tvDetailResponse.lastAirDate}",
                    tvDetailResponse.genres.joinToString(
                        separator = ", ",
                        transform = { it.name }),
                    tvDetailResponse.episodeRunTime.joinToString(
                        separator = ", ",
                        transform = { "$it minutes" }),
                    tvDetailResponse.tagline,
                    tvDetailResponse.numberOfEpisodes,
                    tvDetailResponse.numberOfSeasons,
                    tvDetailResponse.overview,
                    tvDetailResponse.voteAverage,
                    tvDetailResponse.posterPath,
                    tvDetailResponse.backdropPath
                )
                tvDetailResult.postValue(tv)
            }
        })
        return tvDetailResult
    }
}