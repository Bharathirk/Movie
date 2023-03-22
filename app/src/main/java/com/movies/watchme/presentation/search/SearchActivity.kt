package com.movies.watchme.presentation.search

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.movies.watchme.R
import com.movies.watchme.data.models.Movie
import com.movies.watchme.databinding.ActivitySearchBinding
import com.movies.watchme.presentation.base.BaseActivity
import com.movies.watchme.presentation.moviedetail.MovieDetailActivity
import com.movies.watchme.presentation.search.SearchMovieAdapter.MovieClickListener

class SearchActivity : BaseActivity(), MovieClickListener {

  companion object {
    fun getCallingIntent(context: Context): Intent {
      return Intent(context, SearchActivity::class.java)
    }
  }

  private lateinit var binding: ActivitySearchBinding
  private lateinit var searchView: SearchView
  private val viewModel: SearchViewModel by viewModels()
  private lateinit var searchAdapter: SearchMovieAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivitySearchBinding.inflate(layoutInflater)
    setContentView(binding.root)
    setSupportActionBar(binding.toolbar)
    showBackButton(true)
    setupMenuProvider()
    setUpViews()
    observeSearchMovies()
  }

  private fun setUpViews() {
    searchAdapter = SearchMovieAdapter(this, this)
    binding.rvSearch.apply {
      layoutManager = GridLayoutManager(this@SearchActivity, 2)
      adapter = searchAdapter
    }
  }

  private fun observeSearchMovies() {
    viewModel.searchedMovies.observe(this) { movies ->
      if (movies.isNullOrEmpty()) {
        binding.rvSearch.isVisible = false
      } else {
        binding.rvSearch.isVisible = true
        searchAdapter.setMovies(movies)
      }
    }
  }

  private fun setupMenuProvider() {
    addMenuProvider(object : MenuProvider, OnQueryTextListener {
      override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = searchItem.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(this)
        searchView.setIconifiedByDefault(true)
        searchView.maxWidth = Int.MAX_VALUE
      }

      override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return true
      }

      override fun onQueryTextSubmit(query: String?): Boolean {
        searchView.clearFocus()
        if (!query.isNullOrEmpty()) {
          viewModel.searchMovie(query)
        }
        return false
      }

      override fun onQueryTextChange(newText: String?): Boolean {
        if (!newText.isNullOrEmpty()) {
          viewModel.searchMovie(newText)
        }
        return false
      }
    })
  }

  override fun onMovieClicked(movie: Movie) {
    startActivity(MovieDetailActivity.getCallingIntent(this, movie))
  }
}