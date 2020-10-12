package com.android.jogon.ui.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.android.jogon.R
import com.android.jogon.ui.main.DataBaseHandler
import com.android.jogon.ui.main.models.Run


class DataFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_table_header, container, false)

        val db = DataBaseHandler.getInstance(context!!)
        val data = db.readData()
        val table = view.findViewById(R.id.table) as TableLayout
        table.isStretchAllColumns = (true)

        createHeaders(table)

        for (i in 0 until data.size) {
            populateTableRow(data, i, table)

        }
        db.close()
        return view
    }

    private fun createHeaders(table: TableLayout) {
        val tr = TableRow(context)
        tr.layoutParams = TableLayout.LayoutParams(
            TableLayout.LayoutParams.MATCH_PARENT,
            TableLayout.LayoutParams.WRAP_CONTENT
        )

        val dateTVHeader = TextView(context)
        val timeTVHeader = TextView(context)
        val distTVHeader = TextView(context)
        populateTableCell(tr, dateTVHeader, "Date Added")
        populateTableCell(tr, timeTVHeader, "Duration")
        populateTableCell(tr, distTVHeader, "Distance")
        table.addView(tr)
    }

    private fun populateTableRow(
        data: MutableList<Run>,
        i: Int,
        table: TableLayout
    ) {
        val tr = TableRow(context)
        tr.layoutParams = TableLayout.LayoutParams(
            TableLayout.LayoutParams.MATCH_PARENT,
            TableLayout.LayoutParams.WRAP_CONTENT
        )

        val dateTV = TextView(context)
        val timeTV = TextView(context)
        val distTV = TextView(context)

        val time =
            (data[i].hour.toString() + ":" + data[i].min.toString() + ":" + data[i].sec.toString())

        populateTableCell(tr, dateTV, data[i].date)
        populateTableCell(tr, timeTV, time)
        populateTableCell(tr, distTV, data[i].distance.toString() + " " + data[i].unit)

        table.addView(tr)
    }


    private fun populateTableCell(tr: TableRow, tv: TextView, data: String) {
        tv.layoutParams = TableRow.LayoutParams(
            TableRow.LayoutParams.MATCH_PARENT,
            TableRow.LayoutParams.WRAP_CONTENT
        )
        tv.text = data
        tr.addView(tv)
    }

}
