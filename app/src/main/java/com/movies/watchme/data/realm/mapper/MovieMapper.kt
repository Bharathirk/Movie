package com.movies.watchme.data.realm.mapper

import com.movies.watchme.data.models.MediaType
import com.movies.watchme.data.models.Movie
import com.movies.watchme.data.realm.models.MovieModel
import com.movies.watchme.utils.toRealmList

fun MovieModel.toPojo() = Movie(
  id = id,
  title = title,
  posterPath = posterPath,
  backdropPath = backdropPath,
  overView = overView,
  releaseDate = releaseDate,
  genreIds = genreIds.toPojos(),
  mediaType = Movie.mapToMediaType(mediaType)
)

fun Movie.toModel() = MovieModel(
  id = id,
  title = title,
  posterPath = posterPath,
  backdropPath = backdropPath,
  overView = overView,
  releaseDate = releaseDate,
  genreIds = genreIds.toRealmList(),
  mediaType = mediaType.name
)

fun Iterable<MovieModel>.toPojos(): List<Movie> = this.map { it.toPojo() }

fun Iterable<Movie>.toModels(): List<MovieModel> = this.map { it.toModel() }