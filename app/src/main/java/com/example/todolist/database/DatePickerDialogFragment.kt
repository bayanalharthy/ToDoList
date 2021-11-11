package com.example.todolist.database

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.todolist.TASK_KEY
import java.util.*

class DatePickerDialogFragment:DialogFragment() {

    interface DatePickercallBakc {
        fun onDateSelected(date: Date)
    }




    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {


        val date = arguments?.getSerializable(TASK_KEY) as? Date
        val calendar= Calendar.getInstance()
        if (date !=null){
            calendar.time=date
        }
        val yaer= calendar.get(Calendar.YEAR)
        val month= calendar.get(Calendar.MONTH)
        val day=calendar.get(Calendar.DAY_OF_MONTH)

        val dateListener= DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->



            val resultDate= GregorianCalendar(yaer,month,dayOfMonth).time

            targetFragment?.let {
                (it as DatePickercallBakc).onDateSelected(resultDate)
            }
        }


        return DatePickerDialog(
            requireContext(),dateListener,yaer,month,day
        )


    }
}
