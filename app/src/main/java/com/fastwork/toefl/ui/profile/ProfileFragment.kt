package com.fastwork.toefl.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fastwork.toefl.R
import com.fastwork.toefl.databinding.FragmentProfileBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val profileViewModel: ProfileViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        binding.apply {
            viewModel = profileViewModel
            lifecycleOwner = this@ProfileFragment
        }
        profileViewModel.logoutEvent.observe(this, {
            findNavController().navigate(R.id.action_logout)
        })
        binding.btnMainMenu.setOnClickListener {
            activity?.onBackPressed()
        }
        return binding.root
    }
}