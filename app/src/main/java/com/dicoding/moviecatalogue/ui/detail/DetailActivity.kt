package com.dicoding.moviecatalogue.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.moviecatalogue.R
import com.dicoding.moviecatalogue.databinding.ActivityDetailBinding
import com.dicoding.moviecatalogue.databinding.DetailContentBinding
import com.dicoding.moviecatalogue.viewmodel.ViewModelFactory

class DetailActivity : AppCompatActivity() {
    private lateinit var detailContentBinding: DetailContentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        detailContentBinding = activityDetailBinding.detailContent
        setContentView(activityDetailBinding.root)

        setSupportActionBar(activityDetailBinding.toolbar)
        activityDetailBinding.collapsingToolbar.setCollapsedTitleTextColor(
            ContextCompat.getColor(
                this,
                R.color.white
            )
        )
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = ViewModelFactory.getInstance()
        val viewModel = ViewModelProvider(
            this,
            factory
        )[DetailViewModel::class.java]

        activityDetailBinding.progressBar.visibility = View.VISIBLE
        val extras = intent.extras
        if (extras != null) {
            val id = extras.getInt(EXTRA_ID)
            val type = extras.getString(EXTRA_TYPE)
            if (type != null) {
                when (type) {
                    "movie" -> {
                        viewModel.getMovieDetail(id).observe(this) { movie ->
                            activityDetailBinding.progressBar.visibility = View.GONE
                            activityDetailBinding.collapsingToolbar.title = movie.title
                            Glide.with(this)
                                .load("https://image.tmdb.org/t/p/original/${movie.backdrop}")
                                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                                .into(activityDetailBinding.imgBackdrop)

                            with(detailContentBinding) {
                                tvTitle.text = movie.title
                                tvRelease.text = movie.releaseDate
                                ratingBar.stepSize = 0.1f
                                ratingBar.rating = (movie.rating?.toFloat() as Float) / 2
                                tvRating.text = movie.rating.toString()
                                tvGenre.text = movie.genres
                                tvDuration.text = getString(R.string.duration, movie.runtime)
                                tvTagline.text = getString(R.string.tagline, movie.tagline)
                                tvSeason.visibility = View.GONE
                                tvEpisode.visibility = View.GONE
                                tvOverview.text = movie.overview
                            }

                            Glide.with(this)
                                .load("https://image.tmdb.org/t/p/original/${movie.poster}")
                                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                                .into(detailContentBinding.imgPoster)
                        }
                    }
                    "tv" -> {
                        viewModel.getTvDetail(id).observe(this) { tv ->
                            activityDetailBinding.progressBar.visibility = View.GONE
                            activityDetailBinding.collapsingToolbar.title = tv.title
                            Glide.with(this)
                                .load("https://image.tmdb.org/t/p/original/${tv.backdrop}")
                                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                                .into(activityDetailBinding.imgBackdrop)

                            with(detailContentBinding) {
                                tvTitle.text = tv.title
                                tvRelease.text = tv.releaseDate
                                ratingBar.stepSize = 0.1f
                                ratingBar.rating = (tv.rating?.toFloat() as Float) / 2
                                tvRating.text = tv.rating.toString()
                                tvGenre.text = tv.genres
                                tvDuration.text = getString(R.string.duration, tv.runtime)
                                tvTagline.text = getString(R.string.tagline, tv.tagline)
                                tvSeason.text = getString(R.string.episodes, tv.episode)
                                tvEpisode.text = getString(R.string.seasons, tv.season)
                                tvOverview.text = tv.overview
                            }

                            Glide.with(this)
                                .load("https://image.tmdb.org/t/p/original/${tv.poster}")
                                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                                .into(detailContentBinding.imgPoster)
                        }
                    }
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    companion object {
        const val EXTRA_ID = "id"
        const val EXTRA_TYPE = "type"
    }
}