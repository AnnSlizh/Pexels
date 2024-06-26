package by.slizh.pexelsapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import by.slizh.pexelsapp.R
import by.slizh.pexelsapp.data.response.Photo
import by.slizh.pexelsapp.databinding.HomeListRowBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import javax.inject.Inject

class HomeAdapter: RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    private var showDetailPhoto: ((Photo) -> Unit)? = null
    inner class HomeViewHolder(val binding: HomeListRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {

            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.HomeViewHolder {
        val binding =
            HomeListRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeAdapter.HomeViewHolder, position: Int) {
        val photo = differ.currentList[position]

        holder.binding.apply {
            imageRow.loadImageFromGlide(photo.src?.original)
        }

        holder.itemView.setOnClickListener{
            showDetailPhoto?.invoke(photo)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
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

    fun showDetailPhoto(callback: (Photo) -> Unit) {
        this.showDetailPhoto = callback
    }
}