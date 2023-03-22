package com.movies.watchme.presentation.favourite

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
class FavouriteViewModel @Inject constructor(private val repo: MoviesRepo) : ViewModel() {

  //LiveData of Favourite movies saved by the user
  private var _favMovies = MutableLiveData<List<Movie>>()
  val favMovies: LiveData<List<Movie>>
    get() = _favMovies

  fun fetchFavouriteMovies() {
    viewModelScope.launch {
      _favMovies.value = repo.fetchFavouriteMovies()
    }
  }
}