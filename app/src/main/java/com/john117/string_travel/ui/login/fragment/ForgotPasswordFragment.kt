package com.john117.string_travel.ui.login.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.john117.string_travel.R
import com.john117.string_travel.databinding.FragmentForgotPasswordBinding
import com.john117.string_travel.utils.getTextTrim
import com.john117.string_travel.utils.hideKeyboard
import com.john117.string_travel.utils.isValidEmail
import com.john117.string_travel.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.dialog_send_mail_forgot_password.view.*
import kotlinx.android.synthetic.main.fragment_forgot_password.*



class ForgotPasswordFragment : Fragment() {

    private val userViewModel: UserViewModel by lazy {
        ViewModelProviders
            .of(activity!!)
            .get(UserViewModel::class.java)
    }
    private var isClickForgotPW = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this@ForgotPasswordFragment
        binding.userViewModel = userViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindings()
        addEvent()
    }

    private fun bindings() {
        userViewModel.dataForgotPassword.observe(this, Observer { userData ->
            if (isClickForgotPW) {
                // Send mail success
                if (userData.status == true) {
                    tv_message_forgot_pw.visibility = View.GONE
                    showDialogSendMailSuccess()
                    btn_send_mail.isEnabled = false
                    btn_send_mail.backgroundTintList =
                        ContextCompat.getColorStateList(requireContext(), R.color.colorGrayEnable)
                    isClickForgotPW = false
                }
                // Send mail fail
                else {
                    tv_message_forgot_pw.visibility = View.VISIBLE
                    tv_message_forgot_pw.text = userData.message
                    btn_send_mail.isEnabled = true
                    btn_send_mail.backgroundTintList =
                        ContextCompat.getColorStateList(requireContext(), R.color.colorPurple)
                }
            }
        })
    }

    @SuppressLint("InflateParams")
    private fun showDialogSendMailSuccess() {
        val mDialogView = LayoutInflater.from(requireContext())
            .inflate(R.layout.dialog_send_mail_forgot_password, null)
        val mBuilder = AlertDialog.Builder(requireContext())
            .setView(mDialogView)
            .setCancelable(false) //click outside = false

        val mAlertDialog = mBuilder.show()
        mDialogView.btn_forgot_pw_ok.setOnClickListener {
            mAlertDialog.dismiss()
            showFragmentLogin()
        }
    }

    private fun showFragmentLogin() {
        if (!LoginFragment().isAdded)
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.frm_landing,
                    LoginFragment.getInstance()
                )
                .addToBackStack(null)
                .commit()
    }

    private fun addEvent() {
        btn_send_mail.setOnClickListener {
            sendMail()
        }
        ll_forgot_pw.setOnClickListener { it.hideKeyboard() }
    }

    private fun sendMail() {

            tv_message_forgot_pw.visibility = View.GONE
            if (isValidEmail(edt_email_forgot_pw.getTextTrim())) {
                userViewModel.forgotPassword(edt_email_forgot_pw.getTextTrim())
                isClickForgotPW = true
                btn_send_mail.isEnabled = false
                btn_send_mail.backgroundTintList =
                    ContextCompat.getColorStateList(requireContext(), R.color.colorGrayEnable)
            } else {
                edt_email_forgot_pw.error = getString(R.string.err_email_not_valid)
            }

    }
}