package com.movies.watchme.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movies.watchme.data.models.Movie
import com.movies.watchme.data.repo.MoviesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val CATEGORY_TRENDING_FIRST = "day"
private const val CATEGORY_TOP_RATED = "top_rated"
private const val CATEGORY_POPULAR = "popular"
private const val CATEGORY_NOW_PLAYING = "now_playing"

@HiltViewModel
class HomeViewModel @Inject constructor(private val repo: MoviesRepo) : ViewModel() {

  //LiveData for show Progress Bar
  private var _eventNetworkError = MutableLiveData(false)
  val eventNetworkError: LiveData<Boolean>
    get() = _eventNetworkError

  //LiveData of Trending First
  private var _trendingFirst = MutableLiveData<Movie?>()
  val trendingFirst: LiveData<Movie?>
    get() = _trendingFirst

  //LiveData of Top Rated movies
  private var _topRatedPlayList = MutableLiveData<List<Movie>>()
  val topRatedPlayList: LiveData<List<Movie>>
    get() = _topRatedPlayList

  //LiveData of Popular movies
  private var _popularPlayList = MutableLiveData<List<Movie>>()
  val popularPlayList: LiveData<List<Movie>>
    get() = _popularPlayList

  //LiveData of now playing movies
  private var _nowPlayingPlayList = MutableLiveData<List<Movie>>()
  val nowPlayingPlayList: LiveData<List<Movie>>
    get() = _nowPlayingPlayList

  fun fetchMovies() {
    viewModelScope.launch {
      _trendingFirst.value = repo.fetchTrendingFirst(CATEGORY_TRENDING_FIRST)
      _topRatedPlayList.value = repo.fetchMovieList(CATEGORY_TOP_RATED)
      _popularPlayList.value = repo.fetchMovieList(CATEGORY_POPULAR)
      _nowPlayingPlayList.value = repo.fetchMovieList(CATEGORY_NOW_PLAYING)
    }
  }
}