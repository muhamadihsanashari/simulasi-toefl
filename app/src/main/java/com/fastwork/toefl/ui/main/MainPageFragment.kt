package com.fastwork.toefl.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fastwork.toefl.R
import com.fastwork.toefl.databinding.FragmentMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainPageFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding

    private val mainMenuViewModel by viewModel<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.apply {
            viewModel = mainMenuViewModel
            lifecycleOwner = this@MainPageFragment
        }
        mainMenuViewModel.getListParagraph()
        setupObserver()
        return binding.root
    }

    private fun setupObserver() {
        mainMenuViewModel.onPracticeClicked.observe(this, {
            findNavController().navigate(R.id.toPracticeFragment)
        })
    }
}