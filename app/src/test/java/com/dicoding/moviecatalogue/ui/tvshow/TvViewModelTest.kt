package com.dicoding.moviecatalogue.ui.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.dicoding.moviecatalogue.data.CatalogueEntity
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
class TvViewModelTest {
    private lateinit var viewModel: TvViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var catalogueRepository: CatalogueRepository

    @Mock
    private lateinit var observer: Observer<List<CatalogueEntity>>

    @Before
    fun setup() {
        viewModel = TvViewModel(catalogueRepository)
    }

    @Test
    fun getTvs() {
        val dummyTv = FakeData.generateTv()
        val tv = MutableLiveData<List<CatalogueEntity>>()
        tv.value = dummyTv

        `when`(catalogueRepository.getTv()).thenReturn(tv)
        val catalogueEntities = viewModel.getTvs().value
        verify(catalogueRepository).getTv()
        assertNotNull(catalogueEntities)

        viewModel.getTvs().observeForever(observer)
        verify(observer).onChanged(dummyTv)
    }
}