package com.fastwork.toefl.ui.login

import android.content.SharedPreferences
import android.text.Editable
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.fastwork.toefl.R
import com.fastwork.toefl.core.BaseViewModel
import com.fastwork.toefl.data.repository.UserRepository
import com.fastwork.toefl.utils.ROLES_KEY
import com.fastwork.toefl.utils.USER_ID_KEY
import com.fastwork.toefl.utils.USER_NAME
import com.fastwork.toefl.utils.Validator.isPasswordValid
import com.fastwork.toefl.utils.Validator.isUsernameValid
import kotlinx.coroutines.launch


class LoginViewModel(
    private val userRepository: UserRepository,
    private val sharedPreferences: SharedPreferences
) : BaseViewModel() {


    var email = ObservableField<String>()
    var password = ObservableField<String>()

    val loginFormState = MutableLiveData<LoginFormState>()
    val loginResultState = MutableLiveData<LoginResultState>()

    fun btnLoginClick() {
        launch {
            val result = userRepository.login(email.get().toString(), password.get().toString())
            if (result != null) {
                sharedPreferences.edit().putInt(USER_ID_KEY, result.id).apply()
                sharedPreferences.edit().putString(ROLES_KEY, result.role).apply()
                sharedPreferences.edit().putString(USER_NAME, result.username).apply()
                loginResultState.value = LoginResultState(success = R.string.login_success)
            } else {
                loginResultState.value = LoginResultState(error = R.string.login_failed)
            }
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

    private fun checkForm() {
        if (!isUsernameValid(email.get().toString()) && email.get().toString().isNotEmpty()
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