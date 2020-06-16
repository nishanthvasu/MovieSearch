package com.nishanth.moviesearch.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.nishanth.moviesearch.R
import com.nishanth.moviesearch.data.model.SearchResults
import com.nishanth.moviesearch.util.show

class CustomAdapterMovies :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_ITEM = 0
        private const val VIEW_TYPE_LOADING = 1
    }

    private var moviesList = ArrayList<SearchResults.SearchItem?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == VIEW_TYPE_ITEM) {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_movie, parent, false)
            MovieViewHolder(view)
        } else {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_lazy_loading, parent, false)
            LoadingViewHolder(view)
        }


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is MovieViewHolder) {
            if (moviesList[position] != null) {
                holder.bindItems(moviesList[position]!!)
            }
        } else if (holder is LoadingViewHolder) {
            holder.showLoadingView()
        }
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (moviesList[position] == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    fun setData(newMoviesList: ArrayList<SearchResults.SearchItem?>?) {
        if (newMoviesList != null) {
            if (moviesList.isNotEmpty())
                moviesList.removeAt(moviesList.size - 1)
            moviesList.clear()
            moviesList.addAll(newMoviesList)
        } else {
            moviesList.add(newMoviesList)
        }
        notifyDataSetChanged()
    }

    fun getData() = moviesList

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imagePoster: ImageView = itemView.findViewById(R.id.image_poster)
        private val textTitle: TextView = itemView.findViewById(R.id.text_title)
        private val textYear: TextView = itemView.findViewById(R.id.text_year)
        private val textImdbID: TextView = itemView.findViewById(R.id.text_imdbID)
        private val textType: TextView = itemView.findViewById(R.id.text_type)

        @SuppressLint("SetTextI18n")
        fun bindItems(movie: SearchResults.SearchItem) {
            textTitle.text = movie.title
            textYear.text = movie.year
            textImdbID.text = movie.imdbID
            textType.text = movie.type
            Glide.with(imagePoster.context).load(movie.poster)
                .centerCrop()
                .thumbnail(0.5f)
                .placeholder(R.drawable.ic_film_strip)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imagePoster)
        }

    }

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val progressBar: ProgressBar = itemView.findViewById(R.id.progress_bar)

        fun showLoadingView() {
            progressBar.show()
        }
    }

}