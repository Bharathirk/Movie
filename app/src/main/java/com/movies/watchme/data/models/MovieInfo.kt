package com.movies.watchme.data.models

import com.google.gson.annotations.SerializedName

data class MovieInfoResponse(
  @SerializedName("status_message") val errorMessage: String?,
  @SerializedName("success") val isSuccess: Boolean = true,
  val movieInfo: MovieInfo,
)

class MovieInfo {
}

/*
// Start


// 20230313141608
// https://api.themoviedb.org/3/movie/315162?&api_key=44815b7d00914e0936d40ca6869c21e3&language=en

{
  "adult": false,
  "backdrop_path": "/jr8tSoJGj33XLgFBy6lmZhpGQNu.jpg",
  "belongs_to_collection": {
    "id": 94602,
    "name": "Puss in Boots Collection",
    "poster_path": "/anHwj9IupRoRZZ98WTBvHpTiE6A.jpg",
    "backdrop_path": "/feU1DWV5zMWxXUHJyAIk3dHRQ9c.jpg"
  },
  "budget": 90000000,
  "genres": [
    {
      "id": 16,
      "name": "Animation"
    },
    {
      "id": 12,
      "name": "Adventure"
    },
    {
      "id": 35,
      "name": "Comedy"
    },
    {
      "id": 10751,
      "name": "Family"
    }
  ],
  "homepage": "https://www.dreamworks.com/movies/puss-in-boots-the-last-wish",
  "id": 315162,
  "imdb_id": "tt3915174",
  "original_language": "en",
  "original_title": "Puss in Boots: The Last Wish",
  "overview": "Puss in Boots discovers that his passion for adventure has taken its toll: He has burned through eight of his nine lives, leaving him with only one life left. Puss sets out on an epic journey to find the mythical Last Wish and restore his nine lives.",
  "popularity": 3180.495,
  "poster_path": "/kuf6dutpsT0vSVehic3EZIqkOBt.jpg",
  "production_companies": [
    {
      "id": 521,
      "logo_path": "/kP7t6RwGz2AvvTkvnI1uteEwHet.png",
      "name": "DreamWorks Animation",
      "origin_country": "US"
    },
    {
      "id": 33,
      "logo_path": "/8lvHyhjr8oUKOOy2dKXoALWKdp0.png",
      "name": "Universal Pictures",
      "origin_country": "US"
    }
  ],
  "production_countries": [
    {
      "iso_3166_1": "US",
      "name": "United States of America"
    }
  ],
  "release_date": "2022-12-07",
  "revenue": 456000000,
  "runtime": 103,
  "spoken_languages": [
    {
      "english_name": "English",
      "iso_639_1": "en",
      "name": "English"
    },
    {
      "english_name": "Spanish",
      "iso_639_1": "es",
      "name": "Español"
    }
  ],
  "status": "Released",
  "tagline": "Say hola to his little friends.",
  "title": "Puss in Boots: The Last Wish",
  "video": false,
  "vote_average": 8.395,
  "vote_count": 4509
}

// End
 */