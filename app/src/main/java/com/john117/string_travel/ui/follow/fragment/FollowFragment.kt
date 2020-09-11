package com.john117.string_travel.ui.follow.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.john117.string_travel.R
import com.john117.string_travel.ui.follow.adapter.FollowAdapter
import com.john117.string_travel.utils.CURRENT_PER_PAGE
import com.john117.string_travel.utils.getAccessToken
import com.john117.string_travel.utils.showDialogErrorLogin
import com.john117.string_travel.viewmodel.FollowViewModel
import kotlinx.android.synthetic.main.fragment_follow.*


class FollowFragment : Fragment() {
    companion object {
        private var instance: FollowFragment? = null
        fun getInstance(): FollowFragment {
            if (instance == null) instance =
                FollowFragment()
            return instance!!
        }
    }

    private val followAdapter: FollowAdapter by lazy {
        FollowAdapter(
            userFollowClick = { userID: Int, _: Boolean ->
                itemFollowClick(userID)
            },
            onLoadMore = {
                loadMoreUser()
            })
    }
    private val followViewModel: FollowViewModel by lazy {
        ViewModelProviders
            .of(activity!!)
            .get(FollowViewModel::class.java)
    }
    private var page = 1
    private var isLoadMore = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_follow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireContext().getAccessToken()?.let {
            followViewModel.getUserFollowList(it, page, CURRENT_PER_PAGE.toString())
        }
        inItViews()
        bindings()
        addEvents()
    }


    private fun itemFollowClick(id: Int) {
        activity?.getAccessToken()?.let { followViewModel.postFollowUser(it, id) }
    }

    private fun loadMoreUser() {
        isLoadMore = true
        page++
        requireContext().getAccessToken()?.let {
            followViewModel.getUserFollowList(it, page, CURRENT_PER_PAGE.toString())
        }
    }

    private fun addEvents() {
        swipeRefreshLayoutFollow.setOnRefreshListener {
            refreshLayout()
        }
    }

    private fun refreshLayout() {
        page = 1
        requireContext().getAccessToken()?.let {
            followViewModel.getUserFollowList(it, page, CURRENT_PER_PAGE.toString())
        }
        swipeRefreshLayoutFollow.isRefreshing = false
    }

    private fun inItViews() {
        swipeRefreshLayoutFollow.setColorSchemeResources(
            R.color.colorPurple
        )

        rv_user_follow.run {
            rv_user_follow.adapter = followAdapter
            rv_user_follow.layoutManager = LinearLayoutManager(context)
        }
    }

    private fun bindings() {
        followViewModel.listUserFollow.observe(this, Observer { listUserFollow ->

            if (listUserFollow != null) {
                if (listUserFollow.status == true) {
                    progressbar_follow.visibility = View.GONE
                    if (isLoadMore) {
                        listUserFollow.data?.let { followAdapter.upDateStories(it) }
                        isLoadMore = false
                    } else {
                        listUserFollow.data?.let { followAdapter.addDataStories(it) }
                    }
                } else
                    showDialogErrorLogin()
            }
        })
    }

}