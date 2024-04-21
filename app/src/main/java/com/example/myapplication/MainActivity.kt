package com.example.myapplication

import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.data.model.DataResponseItem
import com.example.myapplication.data.remote.ApiResponse
import com.example.myapplication.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(){

    private val viewModel: MainViewModel by viewModels()
    private lateinit var photoListAdapter: PhotoListAdapter

    override fun onViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initView() {
        photoListAdapter = PhotoListAdapter()
        binding.rcyMedia.apply {
            adapter = photoListAdapter
            layoutManager = GridLayoutManager(this@MainActivity,3)
        }

        viewModel.fetchMedia()

        viewModel.getMediaList.observe(this) {
            when (it) {
                is ApiResponse.Loading -> {
                }
                is ApiResponse.Success -> {
                        photoListAdapter.addAllWithClear(it.data as List<DataResponseItem?>)
                }
                is ApiResponse.ApiError -> {
                }
            }
        }

    }

    override fun initObserver() {

    }

    override fun initListener() {

    }

}