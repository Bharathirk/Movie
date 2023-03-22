package com.movies.watchme.data.realm.models

import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject

open class MovieModel constructor(
  @SerializedName("id") var id: Int,
  @SerializedName("poster_path") var posterPath: String,
  @SerializedName("title") var title: String,
  @SerializedName("backdrop_path") var backdropPath: String,
  @SerializedName("overview") var overView: String,
  @SerializedName("release_date") var releaseDate: String?,
  @SerializedName("genre_ids") var genreIds: RealmList<Int>,
  @SerializedName("media_type") var mediaType: String,
) : RealmObject() {
  constructor() : this(0, "", "", "", "", null, RealmList(), "")
}