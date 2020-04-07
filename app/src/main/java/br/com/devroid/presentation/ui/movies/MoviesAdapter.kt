package br.com.devroid.presentation.ui.movies

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import br.com.devroid.domain.entity.Movie

class MoviesAdapter(
    private val onClick: (Movie) -> Unit,
    private val saveToListCallback: (Movie) -> Unit
) : PagedListAdapter<Movie, MovieViewHolder>(DiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MovieViewHolder.inflate(parent)

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position), onClick, saveToListCallback)
    }

    companion object DiffUtilCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
            newItem.id == oldItem.id

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie) =
            newItem == oldItem
    }
}