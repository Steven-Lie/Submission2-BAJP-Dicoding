package com.dicoding.moviecatalogue.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.moviecatalogue.R
import com.dicoding.moviecatalogue.data.CatalogueEntity
import com.dicoding.moviecatalogue.databinding.ItemListBinding

class ListAdapter : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {
    private var listCatalogues = ArrayList<CatalogueEntity>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setCatalogues(catalogues: List<CatalogueEntity>?) {
        if (catalogues == null) return this.listCatalogues.clear()
        this.listCatalogues.addAll(catalogues)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ListViewHolder(val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val itemListBinding =
            ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(itemListBinding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val catalogue = listCatalogues[position]
        with(holder) {
            Glide.with(itemView.context)
                .load("https://image.tmdb.org/t/p/original/${catalogue.poster}")
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                .into(binding.imgPoster)

            with(binding) {
                tvTitle.text = catalogue.title
                tvOverview.text = catalogue.overview
                ratingBar.stepSize = 0.1f
                ratingBar.rating = (catalogue.rating?.toFloat() as Float) / 2
                tvRating.text = catalogue.rating.toString()
            }

            itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(catalogue)
            }
        }
    }

    override fun getItemCount(): Int = listCatalogues.size

    interface OnItemClickCallback {
        fun onItemClicked(catalogue: CatalogueEntity)
    }
}