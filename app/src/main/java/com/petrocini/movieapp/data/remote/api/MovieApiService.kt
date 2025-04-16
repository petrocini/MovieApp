package com.petrocini.movieapp.data.remote.api

import com.petrocini.movieapp.BuildConfig
import com.petrocini.movieapp.data.remote.dto.MovieDto
import com.petrocini.movieapp.data.remote.dto.Result
import com.petrocini.movieapp.utils.TmdbApiConstants
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {

    @GET(TmdbApiConstants.MOVIE_ENDPOINT)
    suspend fun fetchDiscoverMovie(
        @Query("api_key") apiKey: String = BuildConfig.apiKey,
        @Query("include_adult") includeAdult: Boolean = false
    ): Result

    @GET(TmdbApiConstants.TRENDING_MOVIE_ENDPOINT)
    suspend fun fetchTrendingMovie(
        @Query("api_key") apiKey: String = BuildConfig.apiKey,
        @Query("include_adult") includeAdult: Boolean = false
    ): Result

}