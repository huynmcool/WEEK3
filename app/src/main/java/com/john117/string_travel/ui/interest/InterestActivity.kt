package com.john117.string_travel.ui.interest

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.john117.string_travel.R
import com.john117.string_travel.ui.interest.fragment.InterestFragment
import com.john117.string_travel.ui.main.MainActivity


class InterestActivity : AppCompatActivity() {

    companion object {
        private var instance: InterestActivity? = null
        fun getInstance(): InterestActivity {
            if (instance == null) instance =
                InterestActivity()
            return instance!!
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interests)

        if (!InterestFragment().isAdded)
            supportFragmentManager.beginTransaction()
                .add(R.id.frm_interest,
                    InterestFragment.getInstance(), "aa")
                .commit()
    }

    fun clickBackFollow(view: View) {
        onBackPressed()
    }

    fun showMainActivity(view: View) {
        val intent = Intent(this, MainActivity.getInstance().javaClass)
        startActivity(intent)
        finish()
    }
}
