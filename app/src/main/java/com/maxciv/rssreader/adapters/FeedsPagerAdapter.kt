package com.maxciv.rssreader.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.maxciv.rssreader.ui.PostsListFragment
import com.maxciv.rssreader.viewmodels.FeedsViewModel

/**
 * @author maxim.oleynik
 * @since 06.03.2020
 */
class FeedsPagerAdapter(
        fragment: Fragment,
        private val viewModel: FeedsViewModel
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return viewModel.feedTypes.count()
    }

    override fun createFragment(position: Int): Fragment {
        return PostsListFragment.create(viewModel.feedTypes[position])
    }
}
