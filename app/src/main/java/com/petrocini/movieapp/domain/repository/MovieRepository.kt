package com.petrocini.movieapp.domain.repository

import com.petrocini.movieapp.domain.models.Movie
import com.petrocini.movieapp.utils.Response
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun fetchDiscoverMovie(): Flow<Response<List<Movie>>>
    fun fetchTrendingMovie(): Flow<Response<List<Movie>>>
}