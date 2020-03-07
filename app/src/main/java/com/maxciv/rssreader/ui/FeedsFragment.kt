package com.maxciv.rssreader.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.maxciv.rssreader.R
import com.maxciv.rssreader.adapters.FeedsPagerAdapter
import com.maxciv.rssreader.databinding.FragmentFeedsBinding
import com.maxciv.rssreader.viewmodels.FeedsViewModel

/**
 * @author maxim.oleynik
 * @since 06.03.2020
 */
class FeedsFragment : Fragment() {

    private lateinit var binding: FragmentFeedsBinding
    private val viewModel: FeedsViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_feeds, container, false)
        binding.lifecycleOwner = this

        binding.viewPager.adapter = FeedsPagerAdapter(this, viewModel)

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = getString(viewModel.feedTypes[position].feedTitleResId)
        }.attach()

        return binding.root
    }
}
