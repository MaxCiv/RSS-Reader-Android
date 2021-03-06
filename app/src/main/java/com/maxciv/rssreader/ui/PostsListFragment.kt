package com.maxciv.rssreader.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.maxciv.rssreader.Cache
import com.maxciv.rssreader.R
import com.maxciv.rssreader.adapters.PostsListAdapter
import com.maxciv.rssreader.adapters.PostsListDataItem
import com.maxciv.rssreader.databinding.FragmentPostsListBinding
import com.maxciv.rssreader.model.FeedType
import com.maxciv.rssreader.model.HabrFeed
import com.maxciv.rssreader.util.showToast
import com.maxciv.rssreader.viewmodels.PostsListViewModel

/**
 * @author maxim.oleynik
 * @since 06.03.2020
 */
class PostsListFragment : Fragment() {

    private lateinit var binding: FragmentPostsListBinding
    private val viewModel: PostsListViewModel by viewModels()

    private val feedType: FeedType by lazy { requireArguments().getSerializable(KEY_FEED_TYPE) as FeedType }

    private val adapter: PostsListAdapter by lazy { PostsListAdapter(viewModel) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setNewHabrFeed(Cache.getFeedFromCache(feedType))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_posts_list, container, false)
        binding.lifecycleOwner = this

        binding.recyclerView.apply {
            adapter = this@PostsListFragment.adapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadHabrFeed(feedType, showErrorMessageOnFail = true)
        }

        binding.refreshButton.setOnClickListener {
            viewModel.loadHabrFeed(feedType, showErrorMessageOnFail = true)
        }

        viewModel.habrFeed.observe(viewLifecycleOwner, Observer {
            it?.let { newHabrFeed ->
                submitHabrFeedToAdapter(newHabrFeed)
            }
        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            if (isLoading == true) {
                if (isCurrentFeedEmpty()) {
                    binding.loadingProgressBar.visibility = View.VISIBLE
                    binding.swipeRefreshLayout.isEnabled = false
                }
            } else {
                binding.loadingProgressBar.visibility = View.GONE
                binding.swipeRefreshLayout.isEnabled = true
                binding.swipeRefreshLayout.isRefreshing = false
            }
        })

        viewModel.toastHelper.showToastEvent.observe(viewLifecycleOwner, Observer {
            it?.let { messageResId ->
                requireContext().showToast(messageResId)
                viewModel.toastHelper.onShowEnded()
            }
        })

        viewModel.navigateToDetailedPostEvent.observe(viewLifecycleOwner, Observer {
            it?.let { habrPost ->
                this.findNavController().navigate(FeedsFragmentDirections.toDetailedPost(habrPost))
                viewModel.onNavigateToDetailedPostEnded()
            }
        })

        viewModel.navigateToBrowserEvent.observe(viewLifecycleOwner, Observer {
            it?.let { link ->
                startActivity(Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(link)
                })
                viewModel.onNavigateToBrowserEnded()
            }
        })

        viewModel.loadHabrFeed(feedType)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadHabrFeed(feedType)
    }

    private fun submitHabrFeedToAdapter(habrFeed: HabrFeed) {
        val sortedPosts = habrFeed.posts.sortedByDescending { it.getPubDateMillis() }

        val wrappedPosts: MutableList<PostsListDataItem> = sortedPosts
                .map { PostsListDataItem.PostItem(it) }
                .toMutableList()

        if (habrFeed.channel.isValid()) {
            wrappedPosts.add(PostsListDataItem.ChannelItem(habrFeed.channel))
        }

        if (wrappedPosts.isEmpty()) {
            showStubPostsNotFound()
        } else {
            hideStubPostsNotFound()
        }

        adapter.submitList(wrappedPosts)
    }

    private fun isCurrentFeedEmpty(): Boolean {
        return adapter.currentList.isEmpty()
    }

    private fun hideStubPostsNotFound() {
        binding.refreshButton.visibility = View.GONE
        binding.postsNotFoundTextView.visibility = View.GONE
    }

    private fun showStubPostsNotFound() {
        binding.refreshButton.visibility = View.VISIBLE
        binding.postsNotFoundTextView.visibility = View.VISIBLE
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