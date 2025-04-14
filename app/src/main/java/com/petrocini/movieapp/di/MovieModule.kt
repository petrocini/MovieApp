package com.petrocini.movieapp.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.petrocini.movieapp.data.remote.api.MovieApiService
import com.petrocini.movieapp.data.remote.dto.Result
import com.petrocini.movieapp.data.remote.mapper_impl.MovieApiMapperImpl
import com.petrocini.movieapp.data.remote.repository.MovieRepositoryImpl
import com.petrocini.movieapp.data.remote.utils.ApiMapper
import com.petrocini.movieapp.domain.models.Movie
import com.petrocini.movieapp.domain.repository.MovieRepository
import com.petrocini.movieapp.utils.TmdbApiConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieModule {

    private val json = Json {
        coerceInputValues = true
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun provideMovieRepository(
        movieApiService: MovieApiService,
        mapper: ApiMapper<List<Movie>, Result>
    ): MovieRepository = MovieRepositoryImpl(
        movieApiService, mapper
    )

    @Provides
    @Singleton
    fun provideMovieMapper(): ApiMapper<List<Movie>, Result> = MovieApiMapperImpl()

    @Provides
    @Singleton
    fun provideMovieApiService(): MovieApiService {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(TmdbApiConstants.BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(MovieApiService::class.java)
    }


}