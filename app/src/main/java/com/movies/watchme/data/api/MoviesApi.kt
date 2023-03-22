package com.movies.watchme.data.api

import com.movies.watchme.BuildConfig
import com.movies.watchme.data.models.MovieInfo
import com.movies.watchme.data.response.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

  @GET("trending/all/{time_window}") suspend fun fetchTrending(
    @Path("time_window") timeWindow: String = ApiConstants.KEY_TRENDING_TIME_WINDOW,
    @Query("page") page: Int = 1,
    @Query("api_key") key: String = BuildConfig.API_KEY,
    @Query("language") language: String = ApiConstants.LANGUAGE
  ): MovieResponse

  @GET("${ApiConstants.KEY_MOVIE}{category}") suspend fun fetchMoviesByCategory(
    @Path("category") category: String,
    @Query("api_key") key: String = BuildConfig.API_KEY,
    @Query("language") language: String = ApiConstants.LANGUAGE
  ): MovieResponse

  @GET(ApiConstants.KEY_SEARCH_MOVIE) suspend fun fetchMoviesBySearchQuery(
    @Query("query") searchQuery: String,
    @Query("api_key") key: String = BuildConfig.API_KEY,
    @Query("language") language: String = ApiConstants.LANGUAGE
  ): MovieResponse

  @GET("${ApiConstants.KEY_MOVIE}{id}") suspend fun fetchMovieById(
    @Path("id") id: Int,
    @Query("api_key") key: String = BuildConfig.API_KEY,
    @Query("language") language: String = ApiConstants.LANGUAGE
  ): MovieInfo
}