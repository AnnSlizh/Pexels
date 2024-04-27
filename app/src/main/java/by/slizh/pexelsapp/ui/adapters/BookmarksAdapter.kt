package by.slizh.pexelsapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import by.slizh.pexelsapp.R
import by.slizh.pexelsapp.data.local.entity.PhotoEntity
import by.slizh.pexelsapp.data.response.Photo
import by.slizh.pexelsapp.databinding.BookamarksListRowBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy


class BookmarksAdapter : RecyclerView.Adapter<BookmarksAdapter.BookmarksViewHolder>() {

    private var bookmarksList = emptyList<PhotoEntity>()

    private var showDetailPhoto: ((PhotoEntity) -> Unit)? = null

    inner class BookmarksViewHolder(val binding: BookamarksListRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BookmarksAdapter.BookmarksViewHolder {
        val binding =
            BookamarksListRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookmarksViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookmarksAdapter.BookmarksViewHolder, position: Int) {
        val bookmark = bookmarksList[position]

        holder.binding.apply {
            bookmarkImage.loadImageFromGlide(bookmark.src?.original)
            bookmarkPhotographer.text = bookmark.photographer
        }

        holder.itemView.setOnClickListener{
            showDetailPhoto?.invoke(bookmark)
        }

    }

    override fun getItemCount(): Int {
        return bookmarksList.size
    }

    private fun ImageView.loadImageFromGlide(url: String?) {
        if (url != null) {
            Glide.with(this)
                .load(url)
                .fitCenter()
                .placeholder(R.drawable.placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(this)
        }
    }

    fun setBookmarksList(list: List<PhotoEntity>) {
        this.bookmarksList = list
        notifyDataSetChanged()
    }

    fun showDetailPhoto(callback: (PhotoEntity) -> Unit) {
        this.showDetailPhoto = callback
    }
}