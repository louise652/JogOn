package com.android.jogon.ui.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.android.jogon.R
import com.android.jogon.ui.main.DataBaseHandler
import com.android.jogon.ui.main.models.Run
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_result.*
import java.text.SimpleDateFormat
import java.util.*

class ResultFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {

        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_result, container, false)
        //Duration number pickers
        val hour = view.findViewById<NumberPicker>(R.id.hour)
        val min = view.findViewById<NumberPicker>(R.id.minute)
        val sec = view.findViewById<NumberPicker>(R.id.seconds)

        initNumberPicker(hour, 9)
        initNumberPicker(min, 59)
        initNumberPicker(sec, 59)


        //distance entry
        val distance = view.findViewById<EditText>(R.id.distance)

        //distance units
        val distanceUnits = view.findViewById<Spinner>(R.id.units)


        distanceUnits.setSelection(0)
        var units = distanceUnits.selectedItem
        distanceUnits?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                units = distanceUnits.selectedItem
            }

        }

        val fabBtn = view.findViewById<FloatingActionButton>(R.id.fab)

        fabBtn.setOnClickListener {

            addRunEntry(hour, distance, units.toString())

        }
        return view
    }

    /**
     * Add run entry to results
     */

    private fun addRunEntry(
        hour: NumberPicker,
        distance: EditText,
        units: String
    ) {

        val db = DataBaseHandler.getInstance(context!!)

        val dist = distance.text.toString().toDouble()

        val sdf = SimpleDateFormat("dd/M/yy HH:mm", Locale.UK)
        val currentDate = sdf.format(Date())
        val runData = Run(currentDate, hour.value, minute.value, seconds.value, dist, units)

        db.insertData(runData)
        db.close()

    }

    /**
     * Sets the number picker max and min values
     */
    private fun initNumberPicker(numberPicker: NumberPicker, maxVal: Int) {
        numberPicker.minValue = 0
        numberPicker.maxValue = maxVal
        numberPicker.wrapSelectorWheel = true
    }


}


