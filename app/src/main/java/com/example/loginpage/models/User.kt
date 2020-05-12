package com.example.loginpage.models

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.loginpage.BR

class User : BaseObservable() {
    @get:Bindable
    var username: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.username)
        }

    @get:Bindable
    var password: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.password)
        }
}