package com.dicoding.moviecatalogue.data

data class CatalogueEntity(
    val id: Int? = 0,
    val title: String? = null,
    val rating: Double? = 0.0,
    val overview: String? = null,
    val poster: String? = null,
)