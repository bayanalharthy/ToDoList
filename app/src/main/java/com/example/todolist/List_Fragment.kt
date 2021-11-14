package com.example.todolist

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isGone
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.database.ToDoList
import java.text.SimpleDateFormat
import java.util.*

const val KEY_ID = "myToDoListId"

var toDo = ""

class List_Fragment : Fragment() {

    private lateinit var ToDoListRecyclerView: RecyclerView
    val toDoListViewModel by lazy { ViewModelProvider(this).get(ToDoListViewModel::class.java) }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.new_todo1, menu)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.new_todo -> {
                toDo = "Todo"
                val fragment = ToDoFragment()

                activity?.let {
                    it.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commit()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list_recycle, container, false)
        ToDoListRecyclerView = view.findViewById(R.id.ToDo_recycler_view)

        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.reverseLayout = true
        linearLayoutManager.stackFromEnd = true

        ToDoListRecyclerView.layoutManager = linearLayoutManager
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toDoListViewModel.liveDataToDoList.observe(
            viewLifecycleOwner, Observer {
                it.reversed()
                updateUI(it)
            }

        )
    }

    private fun updateUI(ToDoList: List<ToDoList>) {
        val adapter = ToDoListAdapter(ToDoList)
        ToDoList.reversed()
        adapter.ToDoList.reversed()
        ToDoListRecyclerView.adapter = adapter
    }

    private inner class ToDoListHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {

        private lateinit var toDoList: ToDoList
        private val titleTextView: TextView = itemView.findViewById(R.id.title_task)
        private val dateTextView: TextView = itemView.findViewById(R.id.date_task_item)
        private val desc_txt_task: TextView = itemView.findViewById(R.id.desc_txt_task)
        private val txt_date_due: TextView = itemView.findViewById(R.id.txt_date_due)
        private val img_done: ImageView = itemView.findViewById(R.id.img_done)


        init {

            itemView.setOnClickListener(this)
        }


        fun bind(toDoList: ToDoList) {
            this.toDoList = toDoList
            titleTextView.text = toDoList.title
            desc_txt_task.text = toDoList.description
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm")
            val currentDateNow = sdf.format(toDoList.date)
            dateTextView.text = currentDateNow.toString()

            if (toDoList.done) {
                img_done.visibility = View.VISIBLE
            } else {
                img_done.visibility = View.GONE
            }


            val currentDate = Date()
Log.e("CurrentDateBayan : " , currentDate.toString())
Log.e("CurrentDateFromDB : " , toDoList.date.toString())

            if(toDoList.date != null ){
                if(currentDate.after(toDoList.date)){
                    txt_date_due.text = "Your Time Is Over"
                }
                else {
                    txt_date_due.visibility = View.GONE

                }

            }



            var date = Date()


                if (toDoList.dueDate != null) {




            if (toDoList.date.after(toDoList.dueDate)) {
                Toast.makeText(context, "time off", Toast.LENGTH_SHORT).show()
            }
                }
        }

        override fun onClick(v: View?) {

            if (v == itemView) {
                toDo = "Update"
                val args = Bundle()
                args.putSerializable(KEY_ID, toDoList.id)
                val fragment = ToDoFragment()
                fragment.arguments = args

                activity?.let {
                    it.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commit()
                }
            }

        }
    }

    private inner class ToDoListAdapter(var ToDoList: List<ToDoList>) :
        RecyclerView.Adapter<ToDoListHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoListHolder {
            val view = layoutInflater.inflate(R.layout.todo_item, parent, false)
            return ToDoListHolder(view)

        }

        override fun onBindViewHolder(holder: ToDoListHolder, position: Int) {
            val ToDoList = ToDoList[position]
            holder.bind(ToDoList)
        }

        override fun getItemCount(): Int = ToDoList.size

    }


}


