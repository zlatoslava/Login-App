package com.example.loginpage.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(val app: Application): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(UserViewModel::class.java)) {

            @Suppress("UNCHECKED_CAST")
            return UserViewModel(app) as T
        }
        throw IllegalAccessException("Unknown ViewModel type")
    }
}