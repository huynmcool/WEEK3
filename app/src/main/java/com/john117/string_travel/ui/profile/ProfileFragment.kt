package com.john117.string_travel.ui.profile
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialog
import com.john117.string_travel.R
import com.john117.string_travel.model.UserData
import com.john117.string_travel.utils.*
import com.john117.string_travel.viewmodel.UserViewModel

import kotlinx.android.synthetic.main.fragment_profile.*



class ProfileFragment : Fragment() {

    companion object {
        private var instance: ProfileFragment? = null
        fun getInstance(): ProfileFragment {
            if (instance == null) instance =
                ProfileFragment()
            return instance!!
        }
    }

    private val userViewModel: UserViewModel by lazy {
        ViewModelProviders
            .of(activity!!)
            .get(UserViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressbar_profile.visibility = View.VISIBLE
        if (activity?.getUserID() != -1) {
            activity?.getAccessToken()?.let {
                activity?.getUserID()?.let { it1 ->
                    userViewModel.getUserProfile(
                        it,
                        it1
                    )
                }
            }
        } else {
            Toast.makeText(
                requireContext(),
                "Can't load information of account!",
                Toast.LENGTH_LONG
            ).show()
        }
        bindings()
        addTabLayout()
        addEvent()
    }

    override fun onResume() {
        super.onResume()

        if (activity?.getUserID() != -1) {
            activity?.getAccessToken()?.let {
                activity?.getUserID()?.let { it1 ->
                    userViewModel.getUserProfile(
                        it,
                        it1
                    )
                }
            }
        } else {
            Toast.makeText(
                requireContext(),
                "Can't load information of account!",
                Toast.LENGTH_LONG
            ).show()
        }

    }

    private fun bindings() {
        userViewModel.dataUserProfile.observe(this, Observer<UserData>  { userData ->
            progressbar_profile.visibility = View.GONE
            if (userData != null) {
                if (userData.status == true)
                    inItViews(userData)
                else{
                    showDialogErrorLogin()
                }
            }
        })

        userViewModel.errorData.observe(this, Observer { isErr->
            if(isErr==true)
                showDialogErrorLogin()
        })
    }

    private fun inItViews(userData: UserData) {
        userData.data?.refresh_token?.let { activity?.setAccessToken(it) }
        // Set image avatar
        Glide.with(requireContext())
            .load(userData.data?.profilePhoto)
            .error(R.drawable.ic_acc_img)
            .into(iv_avatar)

        tv_username_prof.text = userData.data?.username
        tv_bio_prof.text = userData.data?.bio
        tv_web.text = userData.data?.website
        tv_posts_count.text = userData.data?.postsCounter.toString()
        tv_itinerary_counter.text = userData.data?.itineraryCounter.toString()
        tv_following.text = userData.data?.followingCounter.toString()
        tv_follows.text = userData.data?.followerCounter.toString()

    }

    private fun addEvent() {

        tv_web.setOnClickListener {
            var url = tv_web.text.trim().toString()
            if (!url.contains("https://www."))
                url = "https://www.$url"
            Log.d("uriiii", url)
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)//https://www.facebook.com/
            startActivity(intent)
        }
    }


    private fun addTabLayout() {
        val adapter = MyFragmentPagerAdapter(activity!!.supportFragmentManager)
        viewpager_tab_profile.adapter = adapter
        viewpager_tab_profile.offscreenPageLimit = 1
        tab_layout_profile.setupWithViewPager(viewpager_tab_profile)
    }
}