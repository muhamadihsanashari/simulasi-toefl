package com.sehatq.test.ui.login

import android.text.Editable
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.sehatq.test.R
import com.sehatq.test.utils.SingleLiveEvent
import com.sehatq.test.utils.Validator.isEmailValid
import com.sehatq.test.utils.Validator.isPasswordValid

class LoginViewModel : ViewModel() {


    private var email = ObservableField<String>()
    private var password = ObservableField<String>()

    val loginFormState = MutableLiveData<LoginFormState>()
    val loginResultState = MutableLiveData<LoginResultState>()
    val btnFacebookEvent = SingleLiveEvent<Long>()
    val btnGoogleEvent = SingleLiveEvent<Long>()

    var mCallbackManager = CallbackManager.Factory.create()

    init {
        LoginManager.getInstance().registerCallback(mCallbackManager,
            object : FacebookCallback<LoginResult?> {
                override fun onSuccess(loginResult: LoginResult?) {
                    loginResultState.value = LoginResultState(R.string.login_success)
                }

                override fun onCancel() {
                    loginResultState.value =
                        LoginResultState(error = R.string.login_facebook_cancel)
                }

                override fun onError(exception: FacebookException) {
                    loginResultState.value = LoginResultState(error = R.string.login_failed)
                }
            })
    }


    fun btnFacebookClick() {
        btnFacebookEvent.value = System.currentTimeMillis()
    }

    fun btnGoogleClick() {
        btnGoogleEvent.value = System.currentTimeMillis()
    }

    fun btnLoginClick() {
        if (isEmailValid(email.get().toString()) && isPasswordValid(password.get().toString())) {
            loginResultState.value = LoginResultState(success = R.string.login_success)
        } else {
            loginResultState.value = LoginResultState(error = R.string.login_failed)
        }
    }

    fun afterEmailChanged(s: Editable) {
        email.set(s.toString())
        checkForm()
    }

    fun afterPasswordChange(s: Editable) {
        password.set(s.toString())
        checkForm()
    }

    fun checkForm() {
        if (!isEmailValid(email.get().toString()) && email.get().toString()
                .isNotEmpty()
        ) {
            loginFormState.value = LoginFormState(emailError = R.string.invalid_username)
        } else if (!isPasswordValid(password.get().toString()) && password.get().toString()
                .isNotEmpty()
        ) {
            loginFormState.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            loginFormState.value = LoginFormState(isDataValid = true)
        }
    }

}