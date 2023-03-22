package com.movies.watchme.data.response

import com.google.gson.annotations.SerializedName
import com.movies.watchme.data.realm.models.MovieModel

data class MovieResponse(
  @SerializedName("results") val movieList: List<MovieModel>,
  @SerializedName("page") val page: Int,
  @SerializedName("status_message") val errorMessage: String?,
  @SerializedName("success") val isSuccess: Boolean = true,
)