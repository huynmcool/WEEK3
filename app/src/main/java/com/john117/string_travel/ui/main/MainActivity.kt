package com.john117.string_travel.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.john117.string_travel.R
import com.john117.string_travel.ui.feed.FeedFragment
import com.john117.string_travel.ui.profile.ProfileFragment
import com.john117.string_travel.ui.setting.SettingFragment
import com.john117.string_travel.utils.AddMoreFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        private var instance: MainActivity? = null
        fun getInstance(): MainActivity {
            if (instance == null) instance =
                MainActivity()
            return instance!!
        }
    }

    private var count = 0

    private lateinit var feedFragment :FeedFragment

    private val addFragment by lazy {
        AddMoreFragment.getInstance()
    }

    private val profileFragment by lazy {
        ProfileFragment.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        feedFragment = FeedFragment.getInstance()
        inItView()
    }

    private fun inItView() {
        showFragment(FeedFragment())
        botNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_feed -> {
                    count++
                    if (count == 2){
                        feedFragment = FeedFragment()
                        showFragment(feedFragment)
                        count = 0
                    }
                    else {
                        showFragment(feedFragment)
                    }
                    true
                }

                R.id.menu_add -> {
                    count = 0
                    showFragment(addFragment)
                    true
                }

                R.id.menu_profile -> {
                    count = 0
                    showFragment(profileFragment)
                    true
                }
                else -> false
            }
        }
    }


    private fun showFragment(fragment: Fragment, fragmentName: String? = null) {
        if (fragmentName == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.frm_main, fragment)
                .commit()
        } else
            supportFragmentManager.beginTransaction()
                .replace(R.id.frm_main, fragment)
                .addToBackStack(fragmentName)
                .commit()

    }

    private fun addFragment(fragment: Fragment, fragmentName: String) {
        if (!fragment.isAdded)
            supportFragmentManager.beginTransaction()
                .add(R.id.frm_main, fragment)
                .addToBackStack(fragmentName)
                .commit()
    }

    fun onClickBack(view: View) {
        onBackPressed()
    }


    fun showSettingFragment(view: View) {

            showFragment(SettingFragment.getInstance(), "SettingFragment")

    }
}