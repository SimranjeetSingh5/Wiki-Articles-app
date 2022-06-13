package com.example.wikiarticlesapp.adapters

import android.view.LayoutInflater
import android.view.RoundedCorner
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.RequestOptions.fitCenterTransform
import com.example.wikiarticlesapp.R
import com.example.wikiarticlesapp.databinding.ItemArticlePreviewBinding
import com.example.wikiarticlesapp.databinding.ItemRandomArticleBinding
import com.example.wikiarticlesapp.models.Allcategories
import com.example.wikiarticlesapp.models.Item
import com.example.wikiarticlesapp.models.RanItem


class ArticlesAdapter(val layoutType:String): RecyclerView.Adapter<RecyclerView.ViewHolder>() {



    private val differCallback by lazy {
        object :DiffUtil.ItemCallback<Item>(){
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.pageid == newItem.pageid
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }

    }
    }
    val differ = AsyncListDiffer(this,differCallback)

    private val randifferCallback = object :DiffUtil.ItemCallback<RanItem>(){
        override fun areItemsTheSame(oldItem: RanItem, newItem: RanItem): Boolean {
            return oldItem.pageid == newItem.pageid
        }

        override fun areContentsTheSame(oldItem: RanItem, newItem: RanItem): Boolean {
            return oldItem == newItem
        }


    }
    val randiffer = AsyncListDiffer(this,randifferCallback)

    private val catdifferCallback = object :DiffUtil.ItemCallback<Allcategories>(){
        override fun areItemsTheSame(oldItem: Allcategories, newItem: Allcategories): Boolean {
            return oldItem.category == newItem.category
        }

        override fun areContentsTheSame(oldItem: Allcategories, newItem: Allcategories): Boolean {
            return oldItem == newItem
        }
    }
    val catDiffer = AsyncListDiffer(this,catdifferCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null

        viewHolder = if (layoutType=="Featured") {
            val binding = ItemArticlePreviewBinding.inflate(LayoutInflater.from(parent.context))
            ArticleViewHolder(binding)
        } else {
            val binding = ItemRandomArticleBinding.inflate(LayoutInflater.from(parent.context))
            RandomViewHolder(binding)
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (layoutType) {
            "Featured" -> {
                val articleHolder:ArticleViewHolder = holder as ArticleViewHolder
                val article= differ.currentList[position]
                Glide.with(articleHolder.itemView.context)
                    .load(article.imageinfo[0].url)
                    .thumbnail(0.25f)
                    .placeholder(R.drawable.ic_star)
                    .into(articleHolder.binding.ivArticleImage);
    //        Glide.with(articleHolder.itemView.context).load(article.imageinfo[0].url).into(articleHolder.binding.ivArticleImage)
                articleHolder.binding.tvSource.text = article.imageinfo[0].user
                val title = article.title?.subSequence(6, article.title?.length!! -4)
                articleHolder.binding.artTitle.text = title
//                articleHolder.binding.tvDescription.text = article.imageinfo[0].descriptionurl
                articleHolder.binding.tvPublishedAt.text = article.imageinfo[0].timestamp!!.subSequence(0,10)
                setOnItemClickListener {
                    onItemClickListener?.let {
                        it(article)
                    }
                }
            }
            "Random" -> {

                val randomHolder:RandomViewHolder = holder as RandomViewHolder
                val random = randiffer.currentList[position]
                randomHolder.binding1.Title.text = random.title
                randomHolder.binding1.Description.text = random.revisions[0].starRev
            }
            "Category" -> {

                val catHolder:RandomViewHolder = holder as RandomViewHolder
                val category = catDiffer.currentList[position]
                catHolder.binding1.Title.text = category.category
            }
        }
    }

    override fun getItemCount(): Int {
        return when (layoutType) {
            "Featured" -> {
                differ.currentList.size
            }
            "Random" -> {
                randiffer.currentList.size
            }
            else -> {
                catDiffer.currentList.size
            }
        }
    }

    private var onItemClickListener:((Item)->Unit) ?= null

    private fun setOnItemClickListener(listener:(Item)->Unit){
        onItemClickListener = listener
    }

}


class ArticleViewHolder(val binding: ItemArticlePreviewBinding):RecyclerView.ViewHolder(binding.root)
class RandomViewHolder(val binding1: ItemRandomArticleBinding):RecyclerView.ViewHolder(binding1.root)



