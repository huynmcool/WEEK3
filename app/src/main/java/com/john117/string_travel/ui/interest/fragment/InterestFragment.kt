package com.john117.string_travel.ui.interest.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.john117.string_travel.R
import com.john117.string_travel.ui.follow.fragment.FollowFragment
import com.john117.string_travel.ui.interest.adapter.InterestAdapter
import com.john117.string_travel.utils.GridItemDecoration
import com.john117.string_travel.utils.getAccessToken
import com.john117.string_travel.viewmodel.InterestViewModel
import kotlinx.android.synthetic.main.fragment_interest.*

class InterestFragment : Fragment() {

    companion object {
        private var instance: InterestFragment? = null
        fun getInstance(): InterestFragment {
            if (instance == null) instance =
                InterestFragment()
            return instance!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_interest, container, false)
    }

    private val interestViewModel: InterestViewModel by lazy {
        ViewModelProviders
            .of(this)
            .get(InterestViewModel::class.java)
    }

    private val storyAdapter: InterestAdapter by lazy {
        InterestAdapter {
            itemInterestClick()
        }
    }

    private var isGetList = false
    private var countMore = 3
    private var count = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindings()
        inItView()
        addEvent()
    }

    private fun addEvent() {
        btn_next_interest.setOnClickListener {
            putInterest()
        }
    }

    private fun putInterest() {

            tv_message_interest.visibility = View.GONE
            requireContext().getAccessToken()?.let {
                storyAdapter.getListIDInterest()?.let { it1 ->
                    interestViewModel.putListInterest(
                        it,
                        it1
                    )
                }
            }
            if (!FollowFragment().isAdded)
                activity!!.supportFragmentManager.beginTransaction()
                    .add(R.id.frm_interest,
                        FollowFragment()
                    )
                    .addToBackStack(null)
                    .commit()

    }

    override fun onResume() {
        super.onResume()
        tv_message_interest.visibility = View.GONE
    }

    private fun itemInterestClick() {
        count = storyAdapter.getSelectCount()
        if (count >= 3) {
            btn_next_interest.isEnabled = true
            btn_next_interest.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.colorPurple)
            btn_next_interest.text = getString(R.string.btn_next)
        } else {
            btn_next_interest.isEnabled = false
            btn_next_interest.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.colorGrayEnable)
            btn_next_interest.text = getString(R.string._3_more, (countMore - count))
        }
    }

    private fun bindings() {
        interestViewModel.dataInterest.observe(this, Observer { interestData ->
            if (isGetList && interestData != null) {
                progressbar_interest.visibility = View.GONE
                if (interestData.status == true) {
                    interestData.data?.let { storyAdapter.addDataStories(it) }
                    count = storyAdapter.getSelectCount()
                    if (count >= 3) {
                        btn_next_interest.isEnabled = true
                        btn_next_interest.backgroundTintList =
                            ContextCompat.getColorStateList(requireContext(), R.color.colorPurple)
                        btn_next_interest.text = getString(R.string.btn_next)
                    } else {
                        btn_next_interest.isEnabled = false
                        btn_next_interest.backgroundTintList =
                            ContextCompat.getColorStateList(
                                requireContext(),
                                R.color.colorGrayEnable
                            )
                        btn_next_interest.text = getString(R.string._3_more, (countMore - count))
                    }
                }
            }
        })
    }

    private fun inItView() {
        rv_interests.adapter = storyAdapter
        rv_interests.layoutManager = GridLayoutManager(requireContext(), 2)
        rv_interests.addItemDecoration(GridItemDecoration(20, 2))
        requireContext().getAccessToken()?.let { interestViewModel.getListInterest(it) }
        isGetList = true
        progressbar_interest.visibility = View.VISIBLE
    }
}
