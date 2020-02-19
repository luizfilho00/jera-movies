package br.com.jeramovies.domain.repository

import br.com.jeramovies.domain.entity.Movie

interface MoviesRepository {

    suspend fun getMovies(page: Int? = null): List<Movie>
    suspend fun searchMovies(text: String, page: Int): List<Movie>
}