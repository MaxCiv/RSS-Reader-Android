package com.maxciv.rssreader.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.maxciv.rssreader.R
import com.maxciv.rssreader.databinding.FragmentPostsListBinding
import com.maxciv.rssreader.viewmodels.PostsListViewModel

/**
 * @author maxim.oleynik
 * @since 06.03.2020
 */
class PostsListFragment : Fragment() {

    private lateinit var binding: FragmentPostsListBinding

    private val viewModel: PostsListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            viewModel.restoreStateFromBundle(savedInstanceState)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_posts_list, container, false)

        val itemText = arguments?.getString(KEY_ITEM_TEXT) ?: "Text is missing!"

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.saveStateToBundle(outState)
    }


    companion object {

        private const val KEY_ITEM_TEXT = "KEY_ITEM_TEXT"

        fun create(itemText: String): PostsListFragment {
            return PostsListFragment().apply {
                arguments = Bundle(1).apply {
                    putString(KEY_ITEM_TEXT, itemText)
                }
            }
        }
    }
}