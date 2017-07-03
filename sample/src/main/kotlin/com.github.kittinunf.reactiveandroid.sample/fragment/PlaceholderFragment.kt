package com.github.kittinunf.reactiveandroid.sample.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.kittinunf.reactiveandroid.sample.R
import kotlinx.android.synthetic.main.fragment_place_holder.*

class PlaceholderFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_place_holder, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvName.text = arguments.getString(ARG_SECTION_NAME, "")
    }

    companion object {

        private val ARG_SECTION_NAME = "section_name"

        fun newInstance(sectionName: String): PlaceholderFragment {
            return PlaceholderFragment().apply {
                val args = Bundle()
                args.putString(ARG_SECTION_NAME, sectionName)
                arguments = args
            }
        }

    }
}
