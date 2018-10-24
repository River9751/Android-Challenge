package com.example.river.sidemenu

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_layout.view.*


class Item1Fragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val v = LayoutInflater.from(context).inflate(R.layout.fragment_layout, container, false)

        val bundle = arguments
        val result = bundle!!.getString("MainActivity")

        v.textView.text = result
        return v
    }
}