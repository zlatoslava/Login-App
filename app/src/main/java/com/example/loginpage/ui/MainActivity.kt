package com.example.loginpage.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.example.loginpage.R
import com.example.loginpage.util.PREF_USERNAME
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val preferences = PreferenceManager.getDefaultSharedPreferences(application)
        val username = preferences.getString(PREF_USERNAME, "")
        text_main_activity.text = "Welcome $username!"
    }
}
