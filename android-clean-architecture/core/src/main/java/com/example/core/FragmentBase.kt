package com.example.core


import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

abstract class FragmentBase<VBinding : ViewBinding, ViewModelClass : ViewModel>(): Fragment() {

    protected lateinit var viewModel: ViewModelClass
    protected abstract fun getViewModelClass(): Class<ViewModelClass>

    protected lateinit var binding: VBinding
    protected abstract fun getViewBinding(): VBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        observeData()
    }

    open fun setUpViews() {}

    @SuppressLint("FragmentLiveDataObserve", "HardwareIds")
    open fun observeData() {}

    private fun init() {
        binding = getViewBinding()
        viewModel = ViewModelProvider(this).get(getViewModelClass())
    }
}
