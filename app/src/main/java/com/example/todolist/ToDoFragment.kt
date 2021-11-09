package com.example.todolist

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.DatePicker
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.database.DatePickerDialogFragment
import com.example.todolist.database.ToDoList
import java.util.*

const val KEY_ID = "myToDoListId"
const val TASK_KEY ="date task date"

class ToDoFragment : Fragment() {

    private lateinit var titleEditText: EditText
    private lateinit var toDoList: ToDoList
    private lateinit var dateBtn: Button
    private lateinit var doneCheckBox: CheckBox
    private lateinit var descriptionEditText: EditText
    private lateinit var addBtn: Button
    private lateinit var deleteBtn: Button

    private val fragmentViewModel by lazy { ViewModelProvider(this).get(ToDoFragmentViewModel::class.java) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

       val view = inflater.inflate(R.layout.fragment_todo_list_item, container, false)
        titleEditText = view.findViewById(R.id.todo_text)
        dateBtn = view.findViewById(R.id.to_do_date)
        descriptionEditText = view.findViewById(R.id.description_text)
        addBtn=view.findViewById(R.id.to_do_add)
        deleteBtn=view.findViewById(R.id.to_do_delete)
        doneCheckBox = view.findViewById(R.id.to_do_done)

        dateBtn.apply {
            text = toDoList.date.toString()
        }

        return view
    }

    override fun onStart() {
        super.onStart()
        dateBtn.setOnClickListener{

            val args=Bundle()
            args.putSerializable(TASK_KEY,toDoList.date)
            val datePicker=DatePickerDialogFragment()
            DatePicker.arguments=args
            datePicker.setTargetFragment(this,0)

            datePicker.show(this.parentFragmentManager,"date picker")

            val textWatcher = object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    Log.d("BAYAN", s.toString())
                    toDoList.title = s.toString()
                }

                override fun afterTextChanged(s: Editable?) {

                }
        }

            titleEditText.addTextChangedListener(textWatcher)
            doneCheckBox.setOnCheckedChangeListener{_, isChecked ->
                toDoList.done=isChecked

            }
    }

       }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toDoList= ToDoList()

        val toDoListId=arguments?.getSerializable(KEY_ID) as UUID
        fragmentViewModel.loadToDoList(toDoListId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentViewModel.toDoLiveData.observe(
            viewLifecycleOwner,androidx.lifecycle.Observer{
                it?.let {
                    toDoList=it
                    titleEditText.setText(it.title)
                    dateBtn.text=it.date.toString()
                    doneCheckBox.isChecked=it.done?:false
                }
            }
        )
    }

    override fun onStop() {
        super.onStop()
        fragmentViewModel.saveUpdate(toDoList)
    }

    override fun onDateSelected(date: Date){

        toDoList.date=date
        dateBtn.text=date.toString()
    }
}





