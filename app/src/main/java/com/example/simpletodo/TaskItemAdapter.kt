package com.example.simpletodo

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskItemAdapter(val listOfItems: List<String>,
                    val longClickListener: OnLongClickListener) :
    RecyclerView.Adapter<TaskItemAdapter.ViewHolder>(){

    interface OnLongClickListener {
        fun onItemLongClicked(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        //inflate the custom layout
        val contactView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false)
        //return a new holder instance
        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //get the data model based on position
        val item = listOfItems.get(position)

        holder.textView.text = item
    }

    override fun getItemCount(): Int {
        return listOfItems.size
    }

    //provide a direct reference to each of the views within a data item
    //used to cache the views within the item layout for fast access
    inner class ViewHolder(itemView: View) :  RecyclerView.ViewHolder(itemView){
        //Store references to elements in our layout view
        val textView: TextView

        init{
            textView = itemView.findViewById(android.R.id.text1)

            itemView.setOnLongClickListener {
                longClickListener.onItemLongClicked(adapterPosition)
                true
            }
        }
    }

}