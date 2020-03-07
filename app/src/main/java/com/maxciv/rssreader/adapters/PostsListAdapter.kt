package com.maxciv.rssreader.adapters

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.maxciv.rssreader.databinding.ListItemChannelBinding
import com.maxciv.rssreader.databinding.ListItemPostBinding
import com.maxciv.rssreader.model.HabrChannel
import com.maxciv.rssreader.model.HabrPost

/**
 * @author maxim.oleynik
 * @since 07.03.2020
 */
const val LIST_ITEM_TYPE_POST = 0
const val LIST_ITEM_TYPE_CHANNEL = 1

class PostsListAdapter : ListAdapter<PostsListDataItem, RecyclerView.ViewHolder>(PostsListDataItemDiffCallback()) {

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is PostsListDataItem.PostItem -> LIST_ITEM_TYPE_POST
        is PostsListDataItem.ChannelItem -> LIST_ITEM_TYPE_CHANNEL
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            LIST_ITEM_TYPE_POST -> PostViewHolder.from(parent)
            LIST_ITEM_TYPE_CHANNEL -> ChannelViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PostViewHolder -> {
                val item = getItem(position) as PostsListDataItem.PostItem
                holder.bind(item.habrPost)
            }
            is ChannelViewHolder -> {
                val item = getItem(position) as PostsListDataItem.ChannelItem
                holder.bind(item.habrChannel)
            }
        }
    }


    class PostViewHolder private constructor(
            private val binding: ListItemPostBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(habrPost: HabrPost) {
            binding.titleTextView.text = habrPost.title
            binding.creatorTextView.text = habrPost.creator
            binding.dateTextView.text = DateUtils.getRelativeTimeSpanString(habrPost.getPubDateMillis(), System.currentTimeMillis(), 0)

            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): PostViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemPostBinding.inflate(layoutInflater, parent, false)
                return PostViewHolder(binding)
            }
        }
    }

    class ChannelViewHolder private constructor(
            private val binding: ListItemChannelBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(habrChannel: HabrChannel) {
            binding.titleTextView.text = habrChannel.title
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ChannelViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemChannelBinding.inflate(layoutInflater, parent, false)
                return ChannelViewHolder(binding)
            }
        }
    }
}

sealed class PostsListDataItem {

    abstract val id: String

    data class PostItem(val habrPost: HabrPost) : PostsListDataItem() {
        override val id: String = habrPost.guid
    }

    data class ChannelItem(val habrChannel: HabrChannel) : PostsListDataItem() {
        override val id: String = habrChannel.link
    }
}

private class PostsListDataItemDiffCallback : DiffUtil.ItemCallback<PostsListDataItem>() {

    override fun areItemsTheSame(oldItem: PostsListDataItem, newItem: PostsListDataItem): Boolean {
        return oldItem.javaClass == newItem.javaClass && oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PostsListDataItem, newItem: PostsListDataItem): Boolean {
        return oldItem == newItem
    }
}
