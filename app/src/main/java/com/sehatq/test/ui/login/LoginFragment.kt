package com.sehatq.test.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.sehatq.test.R
import com.sehatq.test.databinding.FragmentLoginBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginFragment : Fragment() {

    private val loginViewModel by viewModel<LoginViewModel>()
    private lateinit var binding: FragmentLoginBinding

    private val RC_SIGN_IN = 8

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        loginViewModel.btnFacebookEvent.observe(this, Observer {
            LoginManager.getInstance()
                .logInWithReadPermissions(this, listOf("public_profile", "user_friends"))
        })

        loginViewModel.btnGoogleEvent.observe(this, Observer {

            val gso =
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build()

            val mGoogleSignInClient = context?.let { it1 ->
                GoogleSignIn.getClient(it1, gso)
            }

            val signInIntent: Intent = mGoogleSignInClient!!.signInIntent

            startActivityForResult(signInIntent, RC_SIGN_IN)

        })

        loginViewModel.loginFormState.observe(viewLifecycleOwner, Observer {
            val loginFormState = it ?: return@Observer

            if (loginFormState.emailError != null) {
                binding.edtEmail.error = getString(loginFormState.emailError)
            }

            if (loginFormState.passwordError != null) { binding.edtPassword.error = getString(loginFormState.passwordError)
            }
        })

        loginViewModel.loginResultState.observe(viewLifecycleOwner, Observer {
            val loginResult = it ?: return@Observer

            if (loginResult.error != null) {
                Toast.makeText(context, getString(loginResult.error), Toast.LENGTH_SHORT).show()
            }

            if (loginResult.success != null) {
                findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (loginViewModel.mCallbackManager.onActivityResult(requestCode, resultCode, data)) {
            return
        }
        if (requestCode == RC_SIGN_IN){
            loginViewModel.handleIntent(data)
        }
    }
}

