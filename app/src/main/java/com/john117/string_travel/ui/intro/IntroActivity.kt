package com.john117.string_travel.ui.intro

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.john117.string_travel.R
import com.john117.string_travel.ui.intro.fragment.IntroFragment1
import com.john117.string_travel.ui.intro.fragment.IntroFragment2
import com.john117.string_travel.ui.intro.fragment.IntroFragment3
import com.john117.string_travel.ui.login.RegisterLandingActivity
import com.john117.string_travel.utils.MyFragmentPagerAdapter
import com.john117.string_travel.utils.ViewPagerListener
import kotlinx.android.synthetic.main.activity_intro.*

import kotlin.math.abs
import kotlin.math.max


class IntroActivity : AppCompatActivity() {
    companion object {
        private const val MIN_SCALE = 0.65f
        private const val MIN_ALPHA = 0.3f
        private var instance: IntroActivity? = null
    }

    private var currentPage = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        addTabLayout()
        addEvent()
    }

    private fun addEvent() {
        btn_skip_intro.setOnClickListener {
            val intent = Intent(this, RegisterLandingActivity.getInstance().javaClass)
            startActivity(intent)
            finish()
        }
        btn_next.setOnClickListener {
            currentPage = view_pager_intro.currentItem
            currentPage++
            if (currentPage < 3)
                view_pager_intro.currentItem = currentPage
            else {
                val intent = Intent(this, RegisterLandingActivity.getInstance().javaClass)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun addTabLayout() {
        val adapter = MyFragmentPagerAdapter(this.supportFragmentManager)
        adapter.addFragment(IntroFragment1(), "")
        adapter.addFragment(IntroFragment2(), "")
        adapter.addFragment(IntroFragment3(), "")
        view_pager_intro.adapter = adapter
        view_pager_intro.setPageTransformer(true, this::zoomOutTransformation)
        view_pager_intro.addOnPageChangeListener(ViewPagerListener(this::onPageSelected))
    }

    private fun onPageSelected(position: Int) {
        when (position) {
            0 -> {
                firstDotImageView.setImageResource(R.drawable.ic_dot_selected)
                secondDotImageView.setImageResource(R.drawable.ic_dot)
                thirdDotImageView.setImageResource(R.drawable.ic_dot)
            }
            1 -> {
                firstDotImageView.setImageResource(R.drawable.ic_dot)
                secondDotImageView.setImageResource(R.drawable.ic_dot_selected)
                thirdDotImageView.setImageResource(R.drawable.ic_dot)
            }
            2 -> {
                firstDotImageView.setImageResource(R.drawable.ic_dot)
                secondDotImageView.setImageResource(R.drawable.ic_dot)
                thirdDotImageView.setImageResource(R.drawable.ic_dot_selected)
            }
        }
    }

    private fun zoomOutTransformation(page: View, position: Float) {
        when {
            position < -1 ->
                page.alpha = 0f
            position <= 1 -> {
                page.scaleX = max(MIN_SCALE, 1 - abs(position))
                page.scaleY = max(MIN_SCALE, 1 - abs(position))
                page.alpha = max(MIN_ALPHA, 1 - abs(position))
            }
            else -> page.alpha = 0f
        }
    }
}