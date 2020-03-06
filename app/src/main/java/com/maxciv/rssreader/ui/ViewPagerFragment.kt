package com.maxciv.rssreader.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.maxciv.rssreader.R
import com.maxciv.rssreader.databinding.FragmentViewPagerBinding

/**
 * @author maxim.oleynik
 * @since 06.03.2020
 */
class ViewPagerFragment : Fragment() {

    private lateinit var binding: FragmentViewPagerBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_view_pager, container, false)

        return binding.root
    }
}
