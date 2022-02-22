package com.dicoding.moviecatalogue.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dicoding.moviecatalogue.utils.FakeData
import com.dicoding.moviecatalogue.utils.LiveDataTestUtils
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import org.mockito.Mockito

class CatalogueRepositoryTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = Mockito.mock(RemoteDataSource::class.java)
    private val catalogueRepository = FakeCatalogueRepository(remote)

    private val movieResponse = FakeData.generateRemoteMovie()
    private val movieDetailResponse = FakeData.generateRemoteMovieDetail()
    private val movieId = movieDetailResponse.id
    private val tvResponse = FakeData.generateRemoteTv()
    private val tvDetailResponse = FakeData.generateRemoteTvDetail()
    private val tvId = tvDetailResponse.id

    @Test
    fun getMovie() {
        doAnswer { invocation ->
            (invocation.arguments[0] as RemoteDataSource.LoadMovieCallback)
                .onMovieReceived(movieResponse)
            null
        }.`when`(remote).getMovie(any())
        val movieEntities = LiveDataTestUtils.getValue(catalogueRepository.getMovie())
        verify(remote).getMovie(any())
        assertNotNull(movieEntities)
        assertEquals(movieResponse.size.toLong(), movieEntities.size.toLong())
    }

    @Test
    fun getMovieDetail() {
        doAnswer { invocation ->
            (invocation.arguments[1] as RemoteDataSource.LoadMovieDetailCallback)
                .onMovieDetailReceived(movieDetailResponse)
            null
        }.`when`(remote).getMovieDetail(eq(movieId), any())
        val movieDetailEntity =
            LiveDataTestUtils.getValue(catalogueRepository.getMovieDetail(movieId))
        verify(remote).getMovieDetail(eq(movieId), any())
        assertNotNull(movieDetailEntity)
        assertEquals(movieDetailEntity.title, movieDetailResponse.title)
        assertEquals(movieDetailEntity.overview, movieDetailResponse.overview)
    }

    @Test
    fun getTv() {
        doAnswer { invocation ->
            (invocation.arguments[0] as RemoteDataSource.LoadTvCallback)
                .onTvReceived(tvResponse)
            null
        }.`when`(remote).getTv(any())
        val tvEntities = LiveDataTestUtils.getValue(catalogueRepository.getTv())
        verify(remote).getTv(any())
        assertNotNull(tvEntities)
        assertEquals(tvResponse.size.toLong(), tvEntities.size.toLong())
    }

    @Test
    fun getTvDetail() {
        doAnswer { invocation ->
            (invocation.arguments[1] as RemoteDataSource.LoadTvDetailCallback)
                .onTvDetailReceived(tvDetailResponse)
            null
        }.`when`(remote).getTvDetail(eq(tvId), any())
        val tvDetailEntity = LiveDataTestUtils.getValue(catalogueRepository.getTvDetail(tvId))
        verify(remote).getTvDetail(eq(tvId), any())
        assertNotNull(tvDetailEntity)
        assertEquals(tvDetailEntity.title, tvDetailResponse.name)
        assertEquals(tvDetailEntity.overview, tvDetailResponse.overview)
    }
}