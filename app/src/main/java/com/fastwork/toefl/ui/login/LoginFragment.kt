package com.fastwork.toefl.ui.login

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.facebook.login.LoginManager
import com.fastwork.toefl.R
import com.fastwork.toefl.databinding.FragmentLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


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
        setupPermission()
        binding.viewModel = loginViewModel
        binding.lifecycleOwner = this
        return binding.root
    }


    private fun setupPermission() {
        val readStoragePermission = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        val writeStoragePermission = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        val listPermissionsNeeded = ArrayList<String>()

        if (readStoragePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (writeStoragePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (listPermissionsNeeded.isNotEmpty()) {
            requestPermissions(
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                ), 1
            )
            return
        }
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

            if (loginFormState.passwordError != null) {
                binding.edtPassword.error = getString(loginFormState.passwordError)
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
        if (requestCode == RC_SIGN_IN) {
            loginViewModel.handleIntent(data)
        }
    }
}

