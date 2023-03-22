package com.movies.watchme.presentation.favourite

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.movies.watchme.R
import com.movies.watchme.data.eventbus.MainThreadBus
import com.movies.watchme.data.eventbus.events.UnFavouriteEvent
import com.movies.watchme.data.models.Movie
import com.movies.watchme.databinding.ActivityFavouriteBinding
import com.movies.watchme.presentation.base.BaseActivity
import com.movies.watchme.presentation.favourite.FavouriteMovieAdapter.MovieClickListener
import com.movies.watchme.presentation.moviedetail.MovieDetailActivity
import com.squareup.otto.Subscribe
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavouriteMovieActivity : BaseActivity(), MovieClickListener {
  companion object {
    fun getCallingIntent(context: Context): Intent {
      return Intent(context, FavouriteMovieActivity::class.java)
    }
  }

  @Inject lateinit var bus: MainThreadBus
  private val viewModel: FavouriteViewModel by viewModels()
  private lateinit var binding: ActivityFavouriteBinding
  private lateinit var favAdapter: FavouriteMovieAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityFavouriteBinding.inflate(layoutInflater)
    setContentView(binding.root)
    setSupportActionBar(binding.toolbar)
    setHeaderTitle(getString(R.string.fav_movies_title))
    showBackButton(true)
    // Register with Bus event
    bus.register(this)
    setUpViews()
    observeFavouriteMovies()
    viewModel.fetchFavouriteMovies()
  }

  private fun setUpViews() {
    favAdapter = FavouriteMovieAdapter(this, this)
    binding.rvFavourite.apply {
      layoutManager = GridLayoutManager(this@FavouriteMovieActivity, 2)
      adapter = favAdapter
    }
  }

  private fun observeFavouriteMovies() {
    viewModel.favMovies.observe(this) { movies ->
      if (!movies.isNullOrEmpty()) {
        binding.rvFavourite.isVisible = true
        favAdapter.setFavMovies(movies)
      } else {
        binding.rvFavourite.isVisible = false
      }
    }
  }

  override fun onMovieClicked(movie: Movie) {
    startActivity(MovieDetailActivity.getCallingIntent(this, movie, true))
  }

  // Unregister Bus event
  override fun onDestroy() {
    super.onDestroy()
    bus.unregister(this)
  }

  // Subscribe the with Bus event and refresh the Fav movies.
  @Subscribe fun onUnFavouriteEvent(event: UnFavouriteEvent) {
    viewModel.fetchFavouriteMovies()
  }
}