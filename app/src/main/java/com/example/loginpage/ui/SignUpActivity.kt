package com.example.loginpage.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.loginpage.R
import com.example.loginpage.databinding.ActivitySignUpBinding
import com.example.loginpage.util.LoginResult
import com.example.loginpage.viewmodels.UserViewModel
import com.example.loginpage.viewmodels.ViewModelFactory

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var userVM: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)

        userVM =
            ViewModelProvider(this, ViewModelFactory(application)).get(UserViewModel::class.java)
        binding.userVM = userVM
        binding.executePendingBindings() //To apply binding immediately (before the view state is restored)

        observeDataValidation()
        observeSignUpResult()
    }

    //Observe changes in variables(passwordValidation and usernameValidation)
    private fun observeDataValidation() {
        // Reacting to password validation result
        userVM.passwordValidation.observe(this, Observer {
            when (it) {
                LoginResult.EMPTY_PASSWORD.value -> setPasswordError(getString(R.string.password_error_no_password))
                LoginResult.SHORT_PASSWORD.value -> setPasswordError(getString(R.string.password_error_short_password))
                LoginResult.PASSWORD_CONFIRMATION_ERROR.value -> setPasswordConfirmationError(getString(R.string.password_error_password_not_confirmed))
                else -> resetPasswordError()
            }
        })

        // Reacting to username validation result
        userVM.usernameValidation.observe(this, Observer { newValue: Int ->
            when (newValue) {
                LoginResult.EMPTY_USERNAME.value -> setUserNameError(getString(R.string.username_error_no_username))
                LoginResult.LONG_USERNAME.value -> setUserNameError(getString((R.string.username_error_long_username)))
                else -> binding.editUsername.error = null
            }
        })
    }

    private fun observeSignUpResult() {
        userVM.loginResult.observe(this, Observer {
            if (it == LoginResult.SUCCESSFUL.value) {
                startMainActivity()
            }
        })
    }

    private fun resetPasswordError(){
        binding.editPassword.error = null
        binding.editPasswordConfirm.error = null
    }

    private fun setPasswordConfirmationError(errorMsg: String) {
        binding.editPasswordConfirm.error = errorMsg
        binding.editPasswordConfirm.requestFocus()
    }

    private fun setUserNameError(errorMsg: String) {
        binding.editUsername.error = errorMsg
        binding.editUsername.requestFocus()
    }

    private fun setPasswordError(errorMsg: String) {
        binding.editPassword.error = errorMsg
        binding.editPassword.requestFocus()
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}
