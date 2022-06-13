package eg.esperantgada.imagegallery.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import eg.esperantgada.imagegallery.R
import eg.esperantgada.imagegallery.data.Photo
import eg.esperantgada.imagegallery.databinding.ImageListItemBinding
import eg.esperantgada.imagegallery.databinding.SearchPhotoResultItemListBinding
import eg.esperantgada.imagegallery.room.entities.SearchItem

class SearchPhotoAdapter:
    PagingDataAdapter<SearchItem, SearchPhotoAdapter.SearchImagePagingViewHolder>(DiffCallback) {


    inner class SearchImagePagingViewHolder(
        private val binding: SearchPhotoResultItemListBinding
    ): RecyclerView.ViewHolder(binding.root){



        //Use Glide library to load photos from the API and bind views with photos details as needed
        fun bind(searchItem: SearchItem){
            binding.apply {
                Glide.with(itemView)
                    .load(searchItem.getImageUrl())
                    .circleCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error_icon)
                    .into(imageView)

                title.text = searchItem.title
            }
        }

    }



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): SearchPhotoAdapter.SearchImagePagingViewHolder {
        val inflatedLayout = SearchPhotoResultItemListBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return SearchImagePagingViewHolder(inflatedLayout)
    }


    override fun onBindViewHolder(holder: SearchPhotoAdapter.SearchImagePagingViewHolder, position: Int) {
        val currentPhoto = getItem(position)

        if (currentPhoto != null){
            holder.bind(currentPhoto)
        }
    }


    companion object DiffCallback : DiffUtil.ItemCallback<SearchItem>(){
        override fun areItemsTheSame(
            oldItem: SearchItem,
            newItem: SearchItem
        ): Boolean = oldItem.id == newItem.id


        override fun areContentsTheSame(
            oldItem: SearchItem,
            newItem: SearchItem
        ): Boolean = oldItem == newItem

    }
}