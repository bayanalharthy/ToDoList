package com.example.todolist

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.database.DatePickerDialogFragment
import com.example.todolist.database.ToDoList
import java.text.DateFormat
import java.util.*

const val TASK_KEY = "date task date"

class ToDoFragment : Fragment(), DatePickerDialogFragment.DatePickercallBakc {

    private lateinit var titleEditText: EditText
    private lateinit var toDoList: ToDoList
    private lateinit var dateBtn: ImageView
    private lateinit var doneCheckBox: CheckBox
    private lateinit var descriptionEditText: EditText
    private lateinit var deleteBtn: ImageView
    private lateinit var add1Btn: ImageView
    private lateinit var WelcomeTxt: TextView

    private var toDoListData = ToDoList()


    private val fragmentViewModel by lazy { ViewModelProvider(this).get(ToDoFragmentViewModel::class.java) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_todo_list_item, container, false)
        titleEditText = view.findViewById(R.id.enter_task)
        dateBtn = view.findViewById(R.id.to_do_date)
        WelcomeTxt = view.findViewById(R.id.txt_welcome)
        descriptionEditText = view.findViewById(R.id.enter_des)
        add1Btn = view.findViewById(R.id.ADD_2)
        deleteBtn = view.findViewById(R.id.to_do_delete)
        doneCheckBox = view.findViewById(R.id.to_do_done)


        val sharedPreference = getActivity()?.getSharedPreferences("BayanApp", Context.MODE_PRIVATE)


        if (sharedPreference != null) {
            if (sharedPreference.getBoolean("isOpen", false)) {
                WelcomeTxt.text = sharedPreference.getString("UserName", "Welcome Guest")
            }
        }


//        dateBtn.apply {
//            text = toDoList.date.toString()
//        }

        return view
    }

    override fun onStart() {
        super.onStart()
        dateBtn.setOnClickListener {
            val args = Bundle()
            args.putSerializable(TASK_KEY, toDoList.date)
            val datePicker = DatePickerDialogFragment()
            datePicker.setTargetFragment(this, 0)
            datePicker.show(this.parentFragmentManager, "date picker")
        }



        add1Btn.setOnClickListener {

            toDoList.title = titleEditText.text.toString()
            toDoList.description = descriptionEditText.text.toString()

            fragmentViewModel.addToDoList(toDoList)
            // fragmentViewModel.saveUpdate(toDoList)
            val fragment = List_Fragment()
            activity?.let {
                it.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit()
            }
        }
        deleteBtn.setOnClickListener {
            fragmentViewModel.delete(toDoList)
            val fragment = List_Fragment()
            activity?.let {
                it.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit()
            }

        }


        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {


            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                toDoList.title = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {

            }

        }
        val textWatcher1 = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {


                toDoList.description = s.toString()

            }

            override fun afterTextChanged(s: Editable?) {
            }

        }




        titleEditText.addTextChangedListener(textWatcher)

        descriptionEditText.addTextChangedListener(textWatcher1)



        doneCheckBox.setOnCheckedChangeListener { _, isChecked ->
            toDoList.done = isChecked

        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (toDo == "Update") {
            val toDoListId = arguments?.getSerializable(KEY_ID) as UUID
            fragmentViewModel.loadToDoList(toDoListId)
        } else {
            toDoList = ToDoList()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentViewModel.toDoLiveData.observe(
            viewLifecycleOwner, androidx.lifecycle.Observer {
                it?.let {
                    toDoList = it
                    titleEditText.setText(it.title)

                    doneCheckBox.isChecked = it.done ?: false
                }
            }
        )
    }

    override fun onStop() {
        super.onStop()
        Log.e("Error Stop", "Yes");
        fragmentViewModel.saveUpdate(toDoList)
    }

    override fun onDateSelected(date: Date) {

        toDoList.date = date

    }
}





