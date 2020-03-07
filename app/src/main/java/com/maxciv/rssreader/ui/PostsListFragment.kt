package com.maxciv.rssreader.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.maxciv.rssreader.R
import com.maxciv.rssreader.adapters.PostsListAdapter
import com.maxciv.rssreader.adapters.PostsListDataItem
import com.maxciv.rssreader.databinding.FragmentPostsListBinding
import com.maxciv.rssreader.model.FeedType
import com.maxciv.rssreader.model.HabrPost
import com.maxciv.rssreader.viewmodels.PostsListViewModel
import timber.log.Timber

/**
 * @author maxim.oleynik
 * @since 06.03.2020
 */
class PostsListFragment : Fragment() {

    private lateinit var binding: FragmentPostsListBinding

    private val viewModel: PostsListViewModel by viewModels()

    private val feedType: FeedType by lazy { requireArguments().getSerializable(KEY_FEED_TYPE) as FeedType }

    private val adapter = PostsListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            viewModel.restoreStateFromBundle(savedInstanceState)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_posts_list, container, false)
        binding.lifecycleOwner = this

        binding.recyclerView.apply {
            adapter = this@PostsListFragment.adapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.posts.observe(viewLifecycleOwner, Observer {
            it?.let { habrPosts ->
                submitPostsToAdapter(habrPosts)
            }
        })

        viewModel.loadHabrPosts(feedType)

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.saveStateToBundle(outState)
    }

    private fun submitPostsToAdapter(posts: List<HabrPost>? = viewModel.posts.value) {
        if (posts == null) {
            adapter.submitList(listOf())
            return
        }

        val sortedPosts = posts.sortedByDescending { it.pubDate }

        val wrappedPosts: MutableList<PostsListDataItem> = sortedPosts
                .map { PostsListDataItem.PostItem(it) }
                .toMutableList()

        if (sortedPosts.isNotEmpty()) {
            wrappedPosts.add(PostsListDataItem.ChannelItem(sortedPosts[0].channel))
        }

        adapter.submitList(wrappedPosts)
    }


    companion object {

        private const val KEY_FEED_TYPE = "KEY_FEED_TYPE"

        fun create(feedType: FeedType): PostsListFragment {
            return PostsListFragment().apply {
                arguments = Bundle(1).apply {
                    putSerializable(KEY_FEED_TYPE, feedType)
                }
            }
        }
    }
}