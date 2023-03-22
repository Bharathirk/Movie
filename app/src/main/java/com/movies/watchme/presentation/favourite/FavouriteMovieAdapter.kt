package com.movies.watchme.presentation.favourite

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.movies.watchme.data.models.Movie
import com.movies.watchme.databinding.ItemMovieBinding
import com.movies.watchme.presentation.favourite.FavouriteMovieAdapter.FavouriteMovieViewHolder

class FavouriteMovieAdapter(
  private val context: Context, private val listener: MovieClickListener
) : RecyclerView.Adapter<FavouriteMovieViewHolder>() {

  private val movies = mutableListOf<Movie>()

  fun setFavMovies(movies: List<Movie>) {
    this.movies.clear()
    this.movies.addAll(movies)
    notifyDataSetChanged()
  }

  interface MovieClickListener {
    fun onMovieClicked(movie: Movie)
  }

  inner class FavouriteMovieViewHolder(private val binding: ItemMovieBinding) :
    RecyclerView.ViewHolder(binding.root), OnClickListener {

    init {
      binding.root.setOnClickListener(this)
    }

    fun bindDataToViews(movie: Movie) {
      Glide.with(context).load(movie.getPosterUrl()).into(binding.posterImage)
      binding.name.text = movie.title
    }

    override fun onClick(p0: View?) {
      val position = bindingAdapterPosition
      if (position == RecyclerView.NO_POSITION) {
        return
      }
      listener.onMovieClicked(movies[position])
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteMovieViewHolder {
    return FavouriteMovieViewHolder(
      ItemMovieBinding.inflate(
        LayoutInflater.from(context), parent, false
      )
    )
  }

  override fun getItemCount(): Int {
    return movies.size
  }

  override fun onBindViewHolder(holder: FavouriteMovieViewHolder, position: Int) {
    val movie = movies[position]
    holder.bindDataToViews(movie)
  }
}