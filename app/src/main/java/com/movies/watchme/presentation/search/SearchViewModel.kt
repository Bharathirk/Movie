package com.movies.watchme.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movies.watchme.data.models.Movie
import com.movies.watchme.data.repo.MoviesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repo: MoviesRepo) : ViewModel() {

  //LiveData of Searched Movies
  private var _searchedMovies = MutableLiveData<List<Movie>>()
  val searchedMovies: LiveData<List<Movie>>
    get() = _searchedMovies

  fun searchMovie(query: String) {
    viewModelScope.launch {
      _searchedMovies.value = repo.fetchMovieBySearchQuery(query)
    }
  }
}