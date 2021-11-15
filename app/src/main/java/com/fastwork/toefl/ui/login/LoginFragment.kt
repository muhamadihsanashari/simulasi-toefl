package com.fastwork.toefl.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.fastwork.toefl.R
import com.fastwork.toefl.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class LoginFragment : Fragment() {

    private val loginViewModel by viewModel<LoginViewModel>()
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_login,
            container, false
        )
        binding.viewModel = loginViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginViewModel.loginFormState.observe(viewLifecycleOwner, Observer {
            val loginFormState = it ?: return@Observer

            if (loginFormState.emailError != null) {
                binding.tiUsername.error = getString(loginFormState.emailError)
            } else {
                binding.tiUsername.error = null
            }

            if (loginFormState.passwordError != null) {
                binding.tiPassword.error = getString(loginFormState.passwordError)
            } else {
                binding.tiPassword.error = null
            }
        })

        loginViewModel.loginResultState.observe(viewLifecycleOwner, Observer {
            val loginResult = it ?: return@Observer

            if (loginResult.error != null) {
                Snackbar.make(
                    binding.root,
                    getText(loginResult.error),
                    Snackbar.LENGTH_SHORT
                ).show()
            }

            if (loginResult.success != null) {
                Snackbar.make(binding.root, getText(loginResult.success), Snackbar.LENGTH_SHORT)
                    .show()
                findNavController().navigate(R.id.mainFragment)
            }
        })
    }

}

