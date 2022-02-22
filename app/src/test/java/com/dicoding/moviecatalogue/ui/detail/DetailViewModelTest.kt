package com.dicoding.moviecatalogue.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.dicoding.moviecatalogue.data.MovieEntity
import com.dicoding.moviecatalogue.data.TvEntity
import com.dicoding.moviecatalogue.data.repository.CatalogueRepository
import com.dicoding.moviecatalogue.utils.FakeData
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {
    private lateinit var viewModel: DetailViewModel
    private val dummyMovies = FakeData.generateMovieDetail()
    private val dummyTv = FakeData.generateTvDetail()
    private val movieId = dummyMovies.id as Int
    private val tvId = dummyTv.id as Int
    private val wrongMovieId = 1

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var catalogueRepository: CatalogueRepository

    @Mock
    private lateinit var movieObserver: Observer<MovieEntity>

    @Mock
    private lateinit var tvObserver: Observer<TvEntity>

    @Before
    fun setup() {
        viewModel = DetailViewModel(catalogueRepository)
    }

    @Test
    fun getDetailMovie() {
        val movieData = MutableLiveData<MovieEntity>()
        movieData.value = dummyMovies

        `when`(catalogueRepository.getMovieDetail(movieId)).thenReturn(movieData)
        val movieEntity = viewModel.getMovieDetail(movieId).value as MovieEntity
        verify(catalogueRepository).getMovieDetail(movieId)
        assertNotNull(movieEntity)
        assertEquals(dummyMovies.id, movieEntity.id)
        assertEquals(dummyMovies.genres, movieEntity.genres)
        assertEquals(dummyMovies.releaseDate, movieEntity.releaseDate)
        assertEquals(dummyMovies.backdrop, movieEntity.backdrop)
        assertEquals(dummyMovies.poster, movieEntity.poster)
        assertEquals(dummyMovies.tagline, movieEntity.tagline)
        assertEquals(dummyMovies.runtime, movieEntity.runtime)
        assertEquals(dummyMovies.overview, movieEntity.overview)
        assertEquals(dummyMovies.rating, movieEntity.rating)

        viewModel.getMovieDetail(movieId).observeForever(movieObserver)
        verify(movieObserver).onChanged(dummyMovies)
    }

    @Test
    fun getDetailTv() {
        val tvData = MutableLiveData<TvEntity>()
        tvData.value = dummyTv

        `when`(catalogueRepository.getTvDetail(tvId)).thenReturn(tvData)
        val tvEntity = viewModel.getTvDetail(tvId).value as TvEntity
        verify(catalogueRepository).getTvDetail(tvId)
        assertNotNull(tvEntity)
        assertEquals(dummyTv.id, tvEntity.id)
        assertEquals(dummyTv.season, tvEntity.season)
        assertEquals(dummyTv.episode, tvEntity.episode)
        assertEquals(dummyTv.backdrop, tvEntity.backdrop)
        assertEquals(dummyTv.genres, tvEntity.genres)
        assertEquals(dummyTv.overview, tvEntity.overview)
        assertEquals(dummyTv.poster, tvEntity.poster)
        assertEquals(dummyTv.rating, tvEntity.rating)
        assertEquals(dummyTv.releaseDate, tvEntity.releaseDate)
        assertEquals(dummyTv.runtime, tvEntity.runtime)
        assertEquals(dummyTv.tagline, tvEntity.tagline)
        assertEquals(dummyTv.title, tvEntity.title)

        viewModel.getTvDetail(tvId).observeForever(tvObserver)
        verify(tvObserver).onChanged(dummyTv)
    }

    @Test
    fun emptyDetail() {
        val movieData = MutableLiveData<MovieEntity>()

        `when`(catalogueRepository.getMovieDetail(wrongMovieId)).thenReturn(movieData)
        val movieEntity = viewModel.getMovieDetail(wrongMovieId).value
        verify(catalogueRepository).getMovieDetail(wrongMovieId)
        assertNull(movieEntity)
    }
}