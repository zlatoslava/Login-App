package com.example.loginpage.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.loginpage.R
import com.example.loginpage.databinding.ActivityLoginBinding
import com.example.loginpage.util.LoginResult
import com.example.loginpage.viewmodels.UserViewModel
import com.example.loginpage.viewmodels.ViewModelFactory
import com.google.android.material.snackbar.Snackbar

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var userVM: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        userVM =
            ViewModelProvider(this, ViewModelFactory(application)).get(UserViewModel::class.java)

        binding.userVM = userVM
        binding.executePendingBindings() //To apply binding immediately (before the view state is restored)

        observeDataValidation()
        observeLoginResult()
    }

    //Observe changes in variables(passwordValidation and usernameValidation)
    private fun observeDataValidation() {
        // Reacting to password validation result
        userVM.passwordValidation.observe(this, Observer {
            when (it) {
                LoginResult.EMPTY_PASSWORD.value -> setPasswordError(getString(R.string.password_error_no_password))
                LoginResult.SHORT_PASSWORD.value -> setPasswordError(getString(R.string.password_error_short_password))
                else -> binding.editPassword.error = null
            }
        })

        // Reacting to username validation result
        userVM.usernameValidation.observe(this, Observer { newValue: Int ->
            when (newValue) {
                LoginResult.EMPTY_USERNAME.value -> setUserNameError(getString((R.string.username_error_no_username)))
                LoginResult.LONG_USERNAME.value -> setUserNameError(getString((R.string.username_error_long_username)))
                else -> binding.editUsername.error = null
            }
        })
    }

    //Reacting to login result(response from ViewModel)
    private fun observeLoginResult() {
        userVM.loginResult.observe(this, Observer {
            when (it) {
                LoginResult.LOGIN_ERROR.value -> showSnackbar()
                LoginResult.SUCCESSFUL.value -> startMainActivity();
            }
        })
    }

    private fun showSnackbar() {
        Snackbar.make(binding.root, R.string.login_error_incorrect_input, Snackbar.LENGTH_LONG)
            .setAction("Sign up") {
                signUp(it)
            }.show()
    }

    //Setting error if validation was wrong in username input view
    private fun setUserNameError(errorMsg: String) {
        binding.editUsername.error = errorMsg
        binding.editUsername.requestFocus()
    }

    //Setting error if validation was wrong in password input view
    private fun setPasswordError(errorMsg: String) {
        binding.editPassword.error = errorMsg
        binding.editPassword.requestFocus()
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    fun signUp(view: View) {
        Intent(this, SignUpActivity::class.java).also {
            startActivity(it)
        }
    }
}
