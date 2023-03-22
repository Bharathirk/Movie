package com.movies.watchme.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.movies.watchme.BuildConfig
import com.movies.watchme.data.models.MediaType.MOVIE
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie constructor(
  val id: Int,
  val posterPath: String,
  val title: String,
  val backdropPath: String,
  val overView: String,
  val releaseDate: String?,
  val genreIds: List<Int>,
  val mediaType: MediaType,
) : Parcelable {
  constructor() : this(0, "", "", "", "", null, emptyList(), MOVIE)

  fun getBackDropUrl() = BuildConfig.IMAGE_BASE_URL.plus(ImageSize.NORMAL.value).plus(backdropPath)

  fun getPosterUrl() = BuildConfig.IMAGE_BASE_URL.plus(ImageSize.ORIGINAL.value).plus(posterPath)

  companion object {
    fun mapToMediaType(mediaType: String): MediaType {
      return when (mediaType) {
        "movie" -> MOVIE
        "tv" -> MediaType.TV
        "person" -> MediaType.PERSON
        else -> {
          MOVIE
        }
      }
    }
  }
}

enum class ImageSize(val value: String) {
  NORMAL("w500"),
  ORIGINAL("original"),
}

enum class MediaType {
  @SerializedName("movie")
  MOVIE,

  @SerializedName("tv")
  TV,

  @SerializedName("person")
  PERSON,
}