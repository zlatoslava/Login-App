package com.example.loginpage.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.loginpage.models.User

class UserViewModel(application: Application) : AndroidViewModel(application) {

    var user: User = User()

    fun login() {

    }

    fun signUp() {

    }
}