package br.com.jeramovies.presentation.ui.movies

import android.widget.Toast
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.toLiveData
import br.com.jeramovies.data.paging.dataSource.InTheatersMoviesDataSource
import br.com.jeramovies.data.paging.dataSource.PopularMoviesDataSource
import br.com.jeramovies.data.paging.dataSource.TopRatedMoviesDataSource
import br.com.jeramovies.domain.entity.Movie
import br.com.jeramovies.domain.entity.MoviePersistError
import br.com.jeramovies.domain.entity.MoviePersisted
import br.com.jeramovies.domain.entity.MovieRemoved
import br.com.jeramovies.domain.repository.InTheatersMoviesRepository
import br.com.jeramovies.domain.repository.MyListRepository
import br.com.jeramovies.domain.repository.PopularMoviesRepository
import br.com.jeramovies.domain.repository.TopRatedMoviesRepository
import br.com.jeramovies.domain.resource.StringResource
import br.com.jeramovies.presentation.ui.movieDetails.MovieDetailsNavData
import br.com.jeramovies.presentation.util.base.BaseViewModel

class MoviesViewModel(
    private val popularMoviesRepository: PopularMoviesRepository,
    private val inTheatersMoviesRepository: InTheatersMoviesRepository,
    private val topRatedMoviesRepository: TopRatedMoviesRepository,
    private val myListRepository: MyListRepository,
    private val strings: StringResource
) : BaseViewModel() {

    val popularMovies by lazy { popularMoviesFactory.toLiveData(config) }
    val inTheatersMovies by lazy { inTheatersMoviesFactory.toLiveData(config) }
    val topRatedMovies by lazy { topRatedMoviesFactory.toLiveData(config) }

    private val popularMoviesFactory = object : DataSource.Factory<Int, Movie>() {
        override fun create(): DataSource<Int, Movie> {
            return PopularMoviesDataSource(
                popularMoviesRepository,
                viewModelScope,
                onLoading = { _loading.value = it },
                onFailure = { showDialog(it) }
            )
        }
    }

    private val topRatedMoviesFactory = object : DataSource.Factory<Int, Movie>() {
        override fun create(): DataSource<Int, Movie> {
            return TopRatedMoviesDataSource(
                topRatedMoviesRepository,
                viewModelScope,
                onLoading = { _loading.value = it },
                onFailure = { showDialog(it) }
            )
        }
    }

    private val inTheatersMoviesFactory = object : DataSource.Factory<Int, Movie>() {
        override fun create(): DataSource<Int, Movie> {
            return InTheatersMoviesDataSource(
                inTheatersMoviesRepository,
                viewModelScope,
                onLoading = { _loading.value = it },
                onFailure = { showDialog(it) }
            )
        }
    }

    fun onMovieClick(movie: Movie) {
        goTo(MovieDetailsNavData(movie.id))
    }

    fun onSaveClicked(movie: Movie) {
        when (myListRepository.updateSavedMovieStatus(movie)) {
            is MoviePersisted -> showToast(strings.movieSavedToList, Toast.LENGTH_SHORT)
            is MovieRemoved -> showToast(strings.movieRemovedFromList, Toast.LENGTH_SHORT)
            is MoviePersistError -> showToast(strings.moviePersistError, Toast.LENGTH_SHORT)
        }
    }
}