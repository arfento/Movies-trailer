package com.ims.movies_trailer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ims.movies_trailer.data.models.Movie
import com.ims.movies_trailer.databinding.MovieItemBinding
import com.ims.movies_trailer.ui.viewmodel.MoviesViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview


@ExperimentalCoroutinesApi
@FlowPreview
class MovieAdapter(
    private val moviesViewModel: MoviesViewModel
) : PagedListAdapter<Movie, MovieAdapter.ViewHolder>(MovieDiffCallback) {


    class ViewHolder private constructor(val movieItemBinding: MovieItemBinding) :
        RecyclerView.ViewHolder(movieItemBinding.root) {
        companion object {
            fun fromParent(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MovieItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(movie: Movie, moviesViewModel: MoviesViewModel) {
            movieItemBinding.movie = movie
            movieItemBinding.viewmodel = moviesViewModel
            movieItemBinding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.fromParent(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)
        movie?.let {
            holder.bind(it, moviesViewModel)
        }
    }

}

object MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }

}
