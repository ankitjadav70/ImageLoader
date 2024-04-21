package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<B : ViewBinding> : AppCompatActivity() {

    protected lateinit var binding : B
    abstract fun onViewBinding() : B
    abstract fun initView()
    abstract fun initObserver()
    abstract fun initListener()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = onViewBinding()
        setContentView(binding.root)
        initView()
        initObserver()
        initListener()
    }
}