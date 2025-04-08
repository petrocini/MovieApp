package com.petrocini.movieapp.data.remote.api

import com.petrocini.movieapp.data.remote.dto.MovieDto
import com.petrocini.movieapp.utils.K
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {

    @GET(K.MOVIE_ENDPOINT)
    suspend fun fetchDiscoverMovie(
        @Query("api_key") apiKey: String = "",
        @Query("include_adult") includeAdult: Boolean = false
    ): MovieDto

    @GET(K.TRENDING_MOVIE_ENDPOINT)
    suspend fun fetchTrendingMovie(
        @Query("api_key") apiKey: String = "",
        @Query("include_adult") includeAdult: Boolean = false
    ): MovieDto

}