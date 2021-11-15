package com.fastwork.toefl.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fastwork.toefl.R
import com.fastwork.toefl.databinding.FragmentSplashBinding
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.viewModel


@DelicateCoroutinesApi
class SplashScreen : Fragment() {

    lateinit var binding: FragmentSplashBinding
    private val splashViewModel: SplashViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        GlobalScope.launch(Dispatchers.Main) {
            delay(3000)
            if (!splashViewModel.isLogin) {
                findNavController().navigate(R.id.loginFragment)
            } else {
                findNavController().navigate(R.id.mainFragment)
            }
        }
    }
}