package com.songnick.mincy.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.songnick.mincy.adapter.MediaListAdapter
import com.songnick.mincy.databinding.FragmentMediaGridBinding
import com.songnick.mincy.viewmodel.MediaListViewModel
import dagger.hilt.android.AndroidEntryPoint
const val TAG = "test"
@AndroidEntryPoint
class MediaFragment:Fragment() {
    private lateinit var binding:FragmentMediaGridBinding;
    private val viewModel:MediaListViewModel by viewModels()

    private val adapter = MediaListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMediaGridBinding.inflate(inflater, container, false)
        binding.mediaList.adapter =  adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.media.observe(viewLifecycleOwner,{
            adapter.submitList(it)
        })
    }
}