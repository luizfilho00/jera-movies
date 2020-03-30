package br.com.jeramovies.domain.repository

import androidx.paging.DataSource
import br.com.jeramovies.domain.entity.Movie

interface TopRatedMoviesRepository : BaseMoviesRepository {

    fun getAll(): DataSource.Factory<Int, Movie>
}