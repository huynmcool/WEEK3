package com.john117.string_travel.ui.launcher

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.john117.string_travel.R
import com.john117.string_travel.ui.intro.IntroActivity
import com.john117.string_travel.ui.main.MainActivity
import com.john117.string_travel.ui.login.RegisterLandingActivity
import com.john117.string_travel.utils.isFirstTime
import com.john117.string_travel.utils.setAccessToken
import com.john117.string_travel.viewmodel.UserViewModel


class LauncherActivity : AppCompatActivity() {

    private val userViewModel: UserViewModel by lazy {
        ViewModelProviders
            .of(this)
            .get(UserViewModel::class.java)
    }
    private var isCheckLogin = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

        // Check first time open app
        if (isFirstTime()) {
            val intent = Intent(this, IntroActivity().javaClass)
            startActivity(intent)
            finish()
        } else {

            val intent = Intent(this, MainActivity().javaClass)
            startActivity(intent)
            finish()


        }
    }

}