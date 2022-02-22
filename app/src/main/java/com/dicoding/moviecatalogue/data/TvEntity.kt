package com.dicoding.moviecatalogue.data

data class TvEntity(
    val id: Int? = 0,
    val title: String? = null,
    val releaseDate: String? = null,
    val genres: String? = null,
    val runtime: String? = null,
    val tagline: String? = null,
    val episode: Int? = 0,
    val season: Int? = 0,
    val overview: String? = null,
    val rating: Double? = 0.0,
    val poster: String? = null,
    val backdrop: String? = null,
)