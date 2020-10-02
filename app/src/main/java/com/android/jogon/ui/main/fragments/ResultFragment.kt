package com.android.jogon.ui.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.jogon.R
import kotlinx.android.synthetic.main.fragment_result.*
import java.time.Duration

class ResultFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        var view =inflater.inflate(R.layout.fragment_result, container, false)
        return view
}

}


