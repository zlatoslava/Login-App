package com.example.loginpage.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.loginpage.R
import com.example.loginpage.databinding.ActivitySignUpBinding
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
    }
}
