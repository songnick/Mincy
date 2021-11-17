package com.songnick.mincy.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.songnick.mincy.databinding.FragmentMediaGridBinding
import com.songnick.mincy.viewmodel.MediaListViewModel
import dagger.hilt.android.AndroidEntryPoint
const val TAG = "test"
@AndroidEntryPoint
class MediaFragment:Fragment() {
    private lateinit var binding:FragmentMediaGridBinding;
    private val viewModel:MediaListViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.media.observe(viewLifecycleOwner,{
            Log.i(TAG, " it : $it" )
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMediaGridBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}