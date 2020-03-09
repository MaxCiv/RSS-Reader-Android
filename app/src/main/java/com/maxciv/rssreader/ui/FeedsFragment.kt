package com.maxciv.rssreader.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.maxciv.rssreader.AppPreferences
import com.maxciv.rssreader.R
import com.maxciv.rssreader.adapters.FeedsPagerAdapter
import com.maxciv.rssreader.databinding.FragmentFeedsBinding
import com.maxciv.rssreader.model.FeedType
import com.maxciv.rssreader.viewmodels.FeedsViewModel

/**
 * @author maxim.oleynik
 * @since 06.03.2020
 */
class FeedsFragment : Fragment() {

    private lateinit var binding: FragmentFeedsBinding
    private val viewModel: FeedsViewModel by viewModels()

    private val prefs: AppPreferences by lazy { AppPreferences(requireContext()) }

    private val onPageChangeCallback: ViewPager2.OnPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            prefs.currentFeedType = FeedType.values()[position]
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_feeds, container, false)
        binding.lifecycleOwner = this

        binding.viewPager.apply {
            adapter = FeedsPagerAdapter(this@FeedsFragment, viewModel.feedTypes)
            setCurrentItem(prefs.currentFeedType.ordinal, false)
            registerOnPageChangeCallback(onPageChangeCallback)
        }

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = getString(viewModel.feedTypes[position].feedTitleResId)
        }.attach()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.viewPager.unregisterOnPageChangeCallback(onPageChangeCallback)
    }
}
