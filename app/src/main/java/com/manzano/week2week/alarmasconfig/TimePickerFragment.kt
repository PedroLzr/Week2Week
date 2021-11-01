package com.manzano.week2week.alarmasconfig

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.util.*

class TimePickerFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        // Use the current time as the default values for the picker
        val calendar: Calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR)
        val minute = calendar.get(Calendar.MINUTE)

        return TimePickerDialog(
            activity,
            activity as TimePickerDialog.OnTimeSetListener,
            hour,
            minute,
            true)
    }
}