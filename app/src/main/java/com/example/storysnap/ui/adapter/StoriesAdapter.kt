package com.example.storysnap.ui.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.storysnap.data.remote.response.ListStoryItem
import com.example.storysnap.databinding.ItemsStoryBinding
import com.example.storysnap.ui.home.DetailStoryActivity

class StoriesAdapter : PagingDataAdapter<ListStoryItem, StoriesAdapter.StoryViewHolder>(LIST_STORY_DIFF_CALLBACK) {

    inner class StoryViewHolder(private val binding: ItemsStoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(story: ListStoryItem) {
            binding.tvItemName.text = story.name
            binding.tvItemDesc.text = story.description
            Glide.with(binding.ivItemPhoto)
                .load(story.photoUrl)
                .into(binding.ivItemPhoto)
            binding.ItemStory.setOnClickListener {
                val intent = Intent(it.context, DetailStoryActivity::class.java)
                intent.putExtra(DetailStoryActivity.EXTRA_IDENTIFIER, story.id)
                itemView.context.startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(itemView.context as Activity).toBundle())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val binding = ItemsStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val story = getItem(position)
        if (story != null) {
            holder.bind(story)
        }
    }

    companion object {
         val LIST_STORY_DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
