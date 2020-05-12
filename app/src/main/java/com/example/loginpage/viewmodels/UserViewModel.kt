package com.example.loginpage.viewmodels

import android.app.Application
import androidx.preference.PreferenceManager
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.loginpage.models.User
import com.example.loginpage.util.LoginResult
import com.example.loginpage.util.PREF_USERNAME
import com.example.loginpage.util.PREF_USER_PASSWORD

class UserViewModel(application: Application) : AndroidViewModel(application) {

    var user: User = User()
    val preferences = PreferenceManager.getDefaultSharedPreferences(application)
    var loginResult = MutableLiveData<Int>()
    var passwordValidation = MutableLiveData<Int>()
    var usernameValidation = MutableLiveData<Int>()

    fun login() {
        validatePassword()
        validateUsername()

        if (isInputValid()) {
            checkIfUserLogged()
        }
    }

    fun signUp() {

    }

    private fun checkIfUserLogged(){
        val loggedInUsername =
            preferences.getString(PREF_USERNAME, "Default")
        val loggedInPassword =
            preferences.getString(PREF_USER_PASSWORD, "")

        Log.d("mtag", "$loggedInUsername + $loggedInPassword")

        if (loggedInUsername == "Default") {
            loginResult.value = LoginResult.NO_SUCH_USER.value
            return
        }
        if (loggedInUsername != user.username.trim() || loggedInPassword != user.password.trim()) {
            loginResult.value = LoginResult.LOGIN_ERROR.value
            return
        }

        loginResult.value = LoginResult.SUCCESSFUL.value
    }

    //fun isPasswordValid() = user.userPassword.length > 5

    private fun validatePassword() {
        when {
            user.password.isEmpty() -> passwordValidation.value = LoginResult.EMPTY_PASSWORD.value
            user.password.length < 5 -> passwordValidation.value = LoginResult.SHORT_PASSWORD.value
            else -> passwordValidation.value = LoginResult.OK.value
        }
    }

    private fun validateUsername() {
        when {
            user.username.isEmpty() -> usernameValidation.value = LoginResult.EMPTY_USERNAME.value
            user.username.length > 10 -> usernameValidation.value = LoginResult.LONG_USERNAME.value
            else -> usernameValidation.value = LoginResult.OK.value
        }
    }

    private fun isInputValid() =
        passwordValidation.value == LoginResult.OK.value && usernameValidation.value == LoginResult.OK.value
}