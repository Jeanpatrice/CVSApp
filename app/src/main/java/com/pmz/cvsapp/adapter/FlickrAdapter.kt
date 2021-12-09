package com.pmz.cvsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pmz.cvsapp.databinding.ItemFlickrBinding
import com.pmz.cvsapp.model.remote.response.Item
import com.pmz.cvsapp.utils.loadImage

class FlickrAdapter(
    private val listener: (item: Item) -> Unit
) : RecyclerView.Adapter<FlickrAdapter.FlickrViewHolder>() {

    private val flickrList: MutableList<Item> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        FlickrViewHolder.getInstance(parent).also { viewHolder ->
            viewHolder.itemView.setOnClickListener {
                listener.invoke(flickrList[viewHolder.adapterPosition])
            }
        }

    override fun onBindViewHolder(holder: FlickrViewHolder, position: Int) {
        holder.loadFlickr(flickrList[position])
    }

    override fun getItemCount() = flickrList.size

    fun submitList(images: List<Item>) {
        flickrList.clear()
        flickrList.addAll(images)
        notifyDataSetChanged()
    }

    class FlickrViewHolder(
        private val binding: ItemFlickrBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun loadFlickr(flickr: Item) = with(binding) {
            tvTitle.text = flickr.title
            flickr.getDecodedDescription()?.let {
                ivImg.loadImage(it["link"] ?: "")
                ivImg.contentDescription = it["alt"] ?: flickr.title
            }
        }

        companion object {
            fun getInstance(parent: ViewGroup): FlickrViewHolder {
                val binding =
                    ItemFlickrBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return FlickrViewHolder(binding)
            }
        }
    }


}