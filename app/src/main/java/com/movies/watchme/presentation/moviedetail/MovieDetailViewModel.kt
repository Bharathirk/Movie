package com.movies.watchme.presentation.moviedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movies.watchme.data.eventbus.MainThreadBus
import com.movies.watchme.data.eventbus.events.UnFavouriteEvent
import com.movies.watchme.data.models.Movie
import com.movies.watchme.data.repo.MoviesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
  private val repo: MoviesRepo, private val bus: MainThreadBus
) : ViewModel() {

  private var isFromFavMovieScreen = false

  fun setIsFromFavMovieScreen(isFromFavMovieScreen: Boolean) {
    this.isFromFavMovieScreen = isFromFavMovieScreen
  }

  //LiveData of Searched Movies
  private var _isFavMovie = MutableLiveData<Boolean>()
  val isFavMovie: LiveData<Boolean>
    get() = _isFavMovie

  // LiveData of checking whether the movie is saved to the favourite or not
  val hasSavedToFavourite = MutableLiveData<Boolean>()

  // LiveData of checking whether the movie is removed from the favourite or not
  val hasRemovedFromFavourite = MutableLiveData<Boolean>()

  init {
    // Register the bus event to remove the movie from the favourite list
    bus.register(this)
  }

  fun isFavouriteMovie(movieId: Int) {
    viewModelScope.launch {
      _isFavMovie.value = repo.isFavouriteMovie(movieId)
    }
  }

  fun saveMovieToFavourite(movie: Movie) {
    viewModelScope.launch {
      val isSaveToFav = repo.saveMovieToFavourite(favouriteMovie = movie)
      hasSavedToFavourite.postValue(isSaveToFav)
    }
  }

  fun removeMovieFromFavourite(movie: Movie) {
    viewModelScope.launch {
      val isRemovedFromFav = repo.removeMovieFromFavourite(favouriteMovie = movie)
      // Event will be triggered when th movie is from the Favourite screen.
      if (isFromFavMovieScreen) {
        // Trigger the UnFavouriteEvent to remove the movie from favourite list
        bus.post(UnFavouriteEvent())
      }
      // Trigger the livedata value to finish the detail activity if it is from the Favourite Activity.
      hasRemovedFromFavourite.postValue(isRemovedFromFav)
    }
  }

  // Unregister Bus event
  fun destroy() {
    bus.unregister(this)
  }
}