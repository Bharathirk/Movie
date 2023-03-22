package com.movies.watchme.presentation.moviedetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.CompoundButton
import android.widget.CompoundButton.OnCheckedChangeListener
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.movies.watchme.data.helpers.getGenresText
import com.movies.watchme.data.models.Movie
import com.movies.watchme.databinding.ActivityMovieDetailBinding
import com.movies.watchme.presentation.base.BaseActivity
import kotlin.properties.Delegates

class MovieDetailActivity : BaseActivity(), OnCheckedChangeListener {

  companion object {
    private const val KEY_MOVIE = "movie"
    private const val KEY_IS_FROM_FAV_MOVIE = "is_from_fav_movie"

    fun getCallingIntent(context: Context, movie: Movie, isFromFavMovie: Boolean = false): Intent {
      return Intent(context, MovieDetailActivity::class.java).apply {
        putExtra(KEY_MOVIE, movie)
        putExtra(KEY_IS_FROM_FAV_MOVIE, isFromFavMovie)
      }
    }
  }

  private lateinit var binding: ActivityMovieDetailBinding
  private var movie by Delegates.notNull<Movie>()
  private val viewModel: MovieDetailViewModel by viewModels()
  private var isFromFavMovie = false

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMovieDetailBinding.inflate(layoutInflater)
    setContentView(binding.root)
    setSupportActionBar(binding.toolbar)
    showBackButton(true)

    if (intent != null) {
      intent.getParcelableExtra<Movie>(KEY_MOVIE)?.let {
        movie = it
      }
      isFromFavMovie = intent.getBooleanExtra(KEY_IS_FROM_FAV_MOVIE, false)
      binding.scrollView.isVisible = true
      binding.tvNoData.isVisible = false
      viewModel.setIsFromFavMovieScreen(isFromFavMovie)
      observeFavMovieValidation()
      observeSavedOrRemovedFavMovie()
      viewModel.isFavouriteMovie(movieId = movie.id)
    } else {
      binding.scrollView.isVisible = false
      binding.tvNoData.isVisible = true
    }
  }

  private fun observeFavMovieValidation() {
    viewModel.isFavMovie.observe(this) { isFavMovie ->
      updateMovieDetails(movie, isFavMovie)
    }
  }

  private fun observeSavedOrRemovedFavMovie() {
    viewModel.hasSavedToFavourite.observe(this) { isSavedToFav ->
      when (isSavedToFav) {
        true -> {
          showToast(this, "Movie added to favourite...")
        }
        false -> {
          showToast(this, "Unable to Favourite the movie...")
        }
      }
    }

    viewModel.hasRemovedFromFavourite.observe(this) { isRemovedFromFav ->
      when (isRemovedFromFav) {
        true -> {
          showToast(this, "Movie removed from favourite...")
          if (isFromFavMovie) {
            finish()
          }
        }
        false -> {
          showToast(this, "Unable to UnFavourite the movie...")
        }
      }
    }
  }

  private fun updateMovieDetails(movie: Movie, isFavMovie: Boolean) {
    Glide.with(this).load(movie.getPosterUrl()).into(binding.imgBanner)
    with(movie) {
      binding.tvTitle.text = title
      binding.tvReleaseDate.text = releaseDate
      binding.tvGenre.text = getGenresText(genreIds)
      binding.tvDescription.text = overView
    }
    binding.cbFavUnfav.isChecked = isFavMovie
    binding.cbFavUnfav.setOnCheckedChangeListener(this)
  }

  override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
    if (isChecked) {
      viewModel.saveMovieToFavourite(movie)
    } else {
      viewModel.removeMovieFromFavourite(movie)
    }
  }

  // Unregister Bus event
  override fun onDestroy() {
    super.onDestroy()
    viewModel.destroy()
  }
}