package com.john117.string_travel.utils

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.john117.string_travel.R


class AddMoreFragment : Fragment() {

    companion object {
        private var instance: AddMoreFragment? = null
        fun getInstance(): AddMoreFragment {
            if (instance == null) instance =
                AddMoreFragment()
            return instance!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_more, container, false)
    }


}