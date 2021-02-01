package com.sehatq.test.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.sehatq.test.R
import com.sehatq.test.databinding.FragmentCartBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CartFragment : Fragment() {
    private val cartViewModel by viewModel<CartViewModel>()
    private lateinit var binding: FragmentCartBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false)
        binding.viewModel = cartViewModel
        binding.lifecycleOwner = this
        return binding.root
    }
}