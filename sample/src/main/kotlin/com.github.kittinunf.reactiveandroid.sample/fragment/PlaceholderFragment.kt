package com.judrummer.androidbestrhythm.viewpager.section

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.kittinunf.reactiveandroid.sample.R
import kotlinx.android.synthetic.main.fragment_place_holder.view.*

class PlaceholderFragment : Fragment() {

    lateinit var rootView: View

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater!!.inflate(R.layout.fragment_place_holder, container, false)
        rootView.tvName.text = arguments.getString(ARG_SECTION_NAME)
        return rootView
    }

    companion object {

        private val ARG_SECTION_NAME = "sectionname"

        fun newInstance(sectionName: String): PlaceholderFragment {
            val fragment = PlaceholderFragment()
            val args = Bundle()
            args.putString(ARG_SECTION_NAME, sectionName)
            fragment.arguments = args
            return fragment
        }

    }
}