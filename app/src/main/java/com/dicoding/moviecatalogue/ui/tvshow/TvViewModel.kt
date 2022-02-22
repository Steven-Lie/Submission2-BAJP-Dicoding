package com.dicoding.moviecatalogue.ui.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.moviecatalogue.data.CatalogueEntity
import com.dicoding.moviecatalogue.data.repository.CatalogueRepository

class TvViewModel(private val catalogueRepository: CatalogueRepository) : ViewModel() {
    fun getTvs(): LiveData<List<CatalogueEntity>> = catalogueRepository.getTv()
}