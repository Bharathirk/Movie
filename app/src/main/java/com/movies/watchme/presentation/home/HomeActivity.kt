package com.movies.watchme.presentation.home

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle.State
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.movies.watchme.R
import com.movies.watchme.R.layout
import com.movies.watchme.data.helpers.getGenresText
import com.movies.watchme.data.models.Movie
import com.movies.watchme.databinding.ActivityHomeBinding
import com.movies.watchme.presentation.base.BaseActivity
import com.movies.watchme.presentation.favourite.FavouriteMovieActivity
import com.movies.watchme.presentation.home.adapters.NowPlayingMovieAdapter
import com.movies.watchme.presentation.home.adapters.PopularMovieAdapter
import com.movies.watchme.presentation.home.adapters.TopRatedMovieAdapter
import com.movies.watchme.presentation.home.adapters.TopRatedMovieAdapter.MovieClickListener
import com.movies.watchme.presentation.moviedetail.MovieDetailActivity
import com.movies.watchme.presentation.search.SearchActivity

class HomeActivity : BaseActivity(), MovieClickListener, PopularMovieAdapter.MovieClickListener,
  NowPlayingMovieAdapter.MovieClickListener {

  companion object {
    fun getCallingIntent(context: Context): Intent = Intent(context, HomeActivity::class.java)
  }

  private lateinit var binding: ActivityHomeBinding
  private val viewModel: HomeViewModel by viewModels()
  private lateinit var topRatedAdapter: TopRatedMovieAdapter
  private lateinit var popularAdapter: PopularMovieAdapter
  private lateinit var nowPlayingAdapter: NowPlayingMovieAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityHomeBinding.inflate(layoutInflater)
    setContentView(binding.root)
    setSupportActionBar(binding.toolbar)
    setupMenuProvider()
    setViews()
    observeMovies()
    viewModel.fetchMovies()
  }

  private fun setupMenuProvider() {
    addMenuProvider(object : MenuProvider {
      override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_home, menu)
      }

      override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
          R.id.action_search -> {
            startActivity(SearchActivity.getCallingIntent(this@HomeActivity))
          }
          R.id.action_favourite -> {
            startActivity(FavouriteMovieActivity.getCallingIntent(this@HomeActivity))
          }
        }
        return true
      }
    })
  }

  private fun setViews() {
    topRatedAdapter = TopRatedMovieAdapter(this, this)
    popularAdapter = PopularMovieAdapter(this, this)
    nowPlayingAdapter = NowPlayingMovieAdapter(this, this)
    binding.rvTopRated.apply {
      layoutManager = LinearLayoutManager(this@HomeActivity, LinearLayoutManager.HORIZONTAL, false)
      adapter = topRatedAdapter
    }
    binding.rvPopular.apply {
      layoutManager = LinearLayoutManager(this@HomeActivity, LinearLayoutManager.HORIZONTAL, false)
      adapter = popularAdapter
    }
    binding.rvNowPlaying.apply {
      layoutManager = LinearLayoutManager(this@HomeActivity, LinearLayoutManager.HORIZONTAL, false)
      adapter = nowPlayingAdapter
    }
  }

  private fun observeMovies() {
    updateTrendingMovie()
    updateTopRatedMovies()
    updatePopularMovies()
    updateNowPlayingMovies()
  }

  private fun updateTrendingMovie() {
    viewModel.trendingFirst.observe(this) { movie ->
      updateTrendingView(movie)
    }
  }

  private fun updateTopRatedMovies() {
    viewModel.topRatedPlayList.observe(this) { movies ->
      if (!movies.isNullOrEmpty()) {
        binding.groupTopRated.isVisible = true
        topRatedAdapter.setTopRatedMovies(movies)
      } else {
        binding.groupTopRated.isVisible = false
      }
    }
  }

  private fun updatePopularMovies() {
    viewModel.popularPlayList.observe(this) { movies ->
      if (!movies.isNullOrEmpty()) {
        binding.groupPopular.isVisible = true
        popularAdapter.setTopRatedMovies(movies)
      } else {
        binding.groupPopular.isVisible = false
      }
    }
  }

  private fun updateNowPlayingMovies() {
    viewModel.nowPlayingPlayList.observe(this) { movies ->
      if (!movies.isNullOrEmpty()) {
        binding.groupNowPlaying.isVisible = true
        nowPlayingAdapter.setTopRatedMovies(movies)
      } else {
        binding.groupNowPlaying.isVisible = false
      }
    }
  }

  private fun updateTrendingView(movie: Movie?) {
    if (movie != null) {
      binding.flTrending.isVisible = true
      Glide.with(this).load(movie.getPosterUrl()).into(binding.ivCover)
      binding.tvGenres.text = getGenresText(movie.genreIds)
      binding.flTrending.setOnClickListener { onMovieClicked(movie) }
      binding.tvInfo.setOnClickListener { onMovieClicked(movie) }
      binding.clPlay.setOnClickListener { onMovieClicked(movie) }
    } else {
      binding.flTrending.isVisible = false
    }
  }

  override fun onMovieClicked(movie: Movie) {
    startActivity(MovieDetailActivity.getCallingIntent(this, movie))
  }
}