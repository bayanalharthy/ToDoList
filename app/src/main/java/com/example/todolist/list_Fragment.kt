package com.example.todolist

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.database.ToDoList


class list_Fragment : Fragment() {

private lateinit var ToDoListRecyclerView: RecyclerView
   val toDoListViewModel by lazy { ViewModelProvider(this).get(ToDoListViewModel::class.java) }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.new_todo,menu)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.new_todo->{
                val toDoList=ToDoList()
                toDoListViewModel.addToDoList(ToDoList())
                val args=Bundle()
                args.putSerializable(KEY_ID,toDoList.id)
                val fragment= ToDoFragment()
                fragment.arguments=args


                activity?.let {
                    it.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container,fragment)
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
        ToDoListRecyclerView=view.findViewById(R.id.ToDo_recycler_view)

        val linearLayoutManager = LinearLayoutManager(context)
        ToDoListRecyclerView.layoutManager=linearLayoutManager

        return view

    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toDoListViewModel.liveDataToDoList.observe(
            viewLifecycleOwner, Observer {

                updateUI(it)
            }

        )
    }

    private fun updateUI(ToDoList: List<ToDoList>) {
        val toDoListAdapter =ToDoListAdapter(ToDoList)
        ToDoListRecyclerView.adapter = toDoListAdapter
    }
    private inner class ToDoListHolder(view: View) : RecyclerView.ViewHolder(view),View.OnClickListener {

        private lateinit var toDoList: ToDoList
        private val titleTextView: TextView = itemView.findViewById(R.id.todo_text)
        private val dateTextView: TextView = itemView.findViewById(R.id.to_do_date)
        private val doneImageView: ImageView = itemView.findViewById(R.id.to_do_done)



        init {

            itemView.setOnClickListener(this)


        }
        fun bind(toDoList: ToDoList) {
            this.toDoList=toDoList
            titleTextView.text = toDoList.title
            dateTextView.text = toDoList.date.toString()

                View.GONE

            }

        override fun onClick(v: View?) {

        }


    }

    private inner class ToDoListAdapter(var ToDoList: List<ToDoList>) :
        RecyclerView.Adapter<ToDoListHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ToDoListHolder  {
            val view = layoutInflater.inflate(R.layout.fragment_todo_list_item, parent, false)
            return ToDoListHolder(view)

        }

        override fun onBindViewHolder(holder: ToDoListHolder, position: Int) {
            val ToDoList = ToDoList[position]
            holder.bind(ToDoList)
        }

        override fun getItemCount(): Int = ToDoList.size





    }



}

