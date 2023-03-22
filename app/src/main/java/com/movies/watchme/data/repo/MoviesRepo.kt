package com.movies.watchme.data.repo

import com.movies.watchme.data.api.MoviesApi
import com.movies.watchme.data.models.MediaType.PERSON
import com.movies.watchme.data.models.Movie
import com.movies.watchme.data.models.MovieInfo
import com.movies.watchme.data.realm.mapper.toModel
import com.movies.watchme.data.realm.mapper.toPojo
import com.movies.watchme.data.realm.mapper.toPojos
import com.movies.watchme.data.realm.models.MovieModel
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.kotlin.where
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Calendar
import javax.inject.Inject

class MoviesRepo @Inject constructor(
  private val api: MoviesApi,
  private val realmConfiguration: RealmConfiguration,
) {

  /**
   * Fetch the Trending movies list and return one latest movie.
   */
  suspend fun fetchTrendingFirst(
    category: String,
  ): Movie? = withContext(Dispatchers.IO) {
    val movieResponse = api.fetchTrending(category)
    val movies = movieResponse.movieList.toPojos()
    val filteredResults = movies.filter { it.mediaType != PERSON }
    if (filteredResults.isNotEmpty()) {
      val dayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
      val filteredResultsCount = filteredResults.count()
      filteredResults.getOrNull(dayOfMonth % filteredResultsCount)
    } else {
      null
    }
  }

  /**
   * Fetch all the movies based on the category like top_rated, popular and now_playing.
   */
  suspend fun fetchMovieList(
    category: String,
  ): List<Movie> = withContext(Dispatchers.IO) {
    val movieResponse = api.fetchMoviesByCategory(category)
    movieResponse.movieList.toPojos()
  }

  /**
   * Fetch all the movies based on the search query by the user.
   */
  suspend fun fetchMovieBySearchQuery(
    searchQuery: String,
  ): List<Movie> = withContext(Dispatchers.IO) {
    val movieResponse = api.fetchMoviesBySearchQuery(searchQuery)
    movieResponse.movieList.toPojos()
  }

  /*----------------------------------------- Database Operations Starts -------------------------------- */

  // Create Realm instance.
  private fun createRealm(): Realm {
    return Realm.getInstance(realmConfiguration)
  }

  /**
   * Validate whether the movie is under the Favourite table or not.
   * @param movieId Id of the movie.
   * @return Boolean returns true if the movie is under the Favourite table.
   */
  suspend fun isFavouriteMovie(movieId: Int): Boolean {
    return withContext(Dispatchers.IO) {
      createRealm().use { realm ->
        return@use realm.where<MovieModel>().equalTo("id", movieId).findFirst()?.toPojo() != null
      }
    }
  }

  /**
   * Fetch all the movies under the Favourite table.
   */
  suspend fun fetchFavouriteMovies(): List<Movie> {
    return withContext(Dispatchers.IO) {
      createRealm().use { realm ->
        return@use realm.where<MovieModel>().findAll().toPojos()
      }
    }
  }

  /**
   * Save the Movie to Favourite table
   * @param favouriteMovie <code> Movie </code> to be saved under Favourite table
   * @return Boolean which returns whether the movie is saved or not.
   */
  suspend fun saveMovieToFavourite(favouriteMovie: Movie): Boolean {
    return try {
      withContext(Dispatchers.IO) {
        createRealm().use { realm ->
          realm.executeTransaction {
            val favouriteModel = favouriteMovie.toModel()
            it.insertOrUpdate(favouriteModel)
          }
        }
      }
      true
    } catch (exception: Exception) {
      false
    }
  }

  /**
   * Remove the Movie from the Favourite table
   * @param favouriteMovie <code> Movie </code> to be removed from the Favourite table
   * @return Boolean which returns whether the movie is removed form the table or not.
   */
  suspend fun removeMovieFromFavourite(favouriteMovie: Movie): Boolean {
    return withContext(Dispatchers.IO) {
      createRealm().use { realm ->
        val favouriteMovieModel =
          realm.where(MovieModel::class.java).equalTo("id", favouriteMovie.id).findFirst()
        if (favouriteMovieModel != null) {
          realm.executeTransaction {
            favouriteMovieModel.deleteFromRealm()
          }
          true
        } else {
          false
        }
      }
    }
  }

  /*----------------------------------------- Database Operations Ends -------------------------------- */
}