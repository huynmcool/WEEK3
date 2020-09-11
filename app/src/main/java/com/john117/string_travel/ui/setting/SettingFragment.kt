package com.john117.string_travel.ui.setting

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.john117.string_travel.R
import com.john117.string_travel.ui.login.RegisterLandingActivity
import com.john117.string_travel.utils.VERSION_STRING
import com.john117.string_travel.utils.getAccessToken
import com.john117.string_travel.utils.showDialogErrorLogin
import com.john117.string_travel.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_setting.*



class SettingFragment : Fragment() {

    companion object {
        private var instance: SettingFragment? = null
        fun getInstance(): SettingFragment {
            if (instance == null) instance =
                SettingFragment()
            return instance!!
        }
    }

    private var isLogOut = false
    private val userViewModel: UserViewModel by lazy {
        ViewModelProviders
            .of(this)
            .get(UserViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inItViews()
        addEvents()
        bindings()
    }

    private fun bindings() {
        userViewModel.dataLogout.observe(this, Observer { dataLogout ->
            progressbar_logout.visibility = View.GONE
            if (dataLogout != null) {
                if (isLogOut) {
                    isLogOut = false
                    if (dataLogout.status == true) {
                        Toast.makeText(requireContext(), "Log out success!", Toast.LENGTH_LONG)
                            .show()
                        val intent = Intent(
                            requireContext(),
                            RegisterLandingActivity.getInstance().javaClass
                        )
                        startActivity(intent)
                        activity?.finish()
                    } else {
                        showDialogErrorLogin()
                    }
                }
            }
        })

        userViewModel.errorData.observe(this, Observer { isErr->
            if(isErr)
                showDialogErrorLogin()
        })
    }

    private fun inItViews() {
        tv_version.text = getString(R.string.version, VERSION_STRING)
    }

    private fun addEvents() {
        btn_log_out.setOnClickListener {

                progressbar_logout.visibility = View.VISIBLE
                activity?.getAccessToken()?.let { it1 -> userViewModel.logOut(it1) }
                isLogOut = true

        }
    }
}