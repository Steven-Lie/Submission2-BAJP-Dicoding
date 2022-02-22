package com.dicoding.moviecatalogue.data.repository

import android.util.Log
import com.dicoding.moviecatalogue.BuildConfig
import com.dicoding.moviecatalogue.data.api.ApiConfig
import com.dicoding.moviecatalogue.data.api.response.*
import com.dicoding.moviecatalogue.utils.EspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource {
    fun getMovie(callback: LoadMovieCallback) {
        EspressoIdlingResource.increment()
        val client = ApiConfig.getApiService().loadMovie(BuildConfig.KEY)
        client.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(
                call: Call<MovieResponse>,
                response: Response<MovieResponse>
            ) {
                if (response.isSuccessful) {
                    callback.onMovieReceived(response.body()?.results as List<MovieResultsItem>)
                } else {
                    Log.e(TAG, "onResponse: ${response.message()}")
                }
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                EspressoIdlingResource.decrement()
            }
        })
    }

    fun getMovieDetail(movieId: Int, callback: LoadMovieDetailCallback) {
        EspressoIdlingResource.increment()
        val client = ApiConfig.getApiService().loadMovieDetail(movieId, BuildConfig.KEY)
        client.enqueue(object : Callback<MovieDetailResponse> {
            override fun onResponse(
                call: Call<MovieDetailResponse>,
                response: Response<MovieDetailResponse>
            ) {
                if (response.isSuccessful) {
                    callback.onMovieDetailReceived(response.body() as MovieDetailResponse)
                } else {
                    Log.e(TAG, "onResponse: ${response.message()}")
                }
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<MovieDetailResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                EspressoIdlingResource.decrement()
            }
        })
    }

    fun getTv(callback: LoadTvCallback) {
        EspressoIdlingResource.increment()
        val client = ApiConfig.getApiService().loadTv(BuildConfig.KEY)
        client.enqueue(object : Callback<TvResponse> {
            override fun onResponse(
                call: Call<TvResponse>,
                response: Response<TvResponse>
            ) {
                if (response.isSuccessful) {
                    callback.onTvReceived(response.body()?.results as List<TvResultsItem>)
                } else {
                    Log.e(TAG, "onResponse: ${response.message()}")
                }
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<TvResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                EspressoIdlingResource.decrement()
            }
        })
    }

    fun getTvDetail(tvId: Int, callback: LoadTvDetailCallback) {
        EspressoIdlingResource.increment()
        val client = ApiConfig.getApiService().loadTVDetail(tvId, BuildConfig.KEY)
        client.enqueue(object : Callback<TvDetailResponse> {
            override fun onResponse(
                call: Call<TvDetailResponse>,
                response: Response<TvDetailResponse>
            ) {
                if (response.isSuccessful) {
                    callback.onTvDetailReceived(response.body() as TvDetailResponse)
                } else {
                    Log.e(TAG, "onResponse: ${response.message()}")
                }
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<TvDetailResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                EspressoIdlingResource.decrement()
            }
        })
    }

    interface LoadMovieCallback {
        fun onMovieReceived(movieResponse: List<MovieResultsItem>)
    }

    interface LoadMovieDetailCallback {
        fun onMovieDetailReceived(movieDetailResponse: MovieDetailResponse)
    }

    interface LoadTvCallback {
        fun onTvReceived(tvResponse: List<TvResultsItem>)
    }

    interface LoadTvDetailCallback {
        fun onTvDetailReceived(tvDetailResponse: TvDetailResponse)
    }

    companion object {
        const val TAG = "RemoteDataSource"

        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource().apply { instance = this }
            }
    }
}