package br.com.jeramovies.data.paging

import androidx.paging.PageKeyedDataSource
import br.com.jeramovies.domain.entity.Movie
import br.com.jeramovies.domain.repository.MoviesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MoviesDataSource(
    private val repository: MoviesRepository,
    private val scope: CoroutineScope
) : PageKeyedDataSource<Int, Movie>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        scope.launch {
            val response = repository.getMovies(1)
            callback.onResult(
                response.movies,
                null,
                response.page + 1
            )
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        scope.launch {
            val response = repository.getMovies(params.key)
            callback.onResult(response.movies, if (params.key > 1) params.key - 1 else null)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        scope.launch {
            val response = repository.getMovies(params.key)
            callback.onResult(response.movies, if (params.key > 1) params.key + 1 else null)
        }
    }
}