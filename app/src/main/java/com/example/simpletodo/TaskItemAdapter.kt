package com.example.simpletodo

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * a bridge that tells the recycler view how to displa ythe data you give it
 */

class TaskItemAdapter(val listOfItems:List<String>,val longClickListener: OnLongClickListener) : RecyclerView.Adapter<TaskItemAdapter.ViewHolder>() {

    interface OnLongClickListener{
        fun onItemLongClicked(position: Int)
    }


    //provide a direct reference to each of the views within a data item
    //used to cache the views within the item layout for fast access
    inner class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        //store references to elements in our layout view
        val textView:TextView

        init{
            textView=itemView.findViewById(android.R.id.text1)

            itemView.setOnLongClickListener{
                longClickListener.onItemLongClicked(adapterPosition)
                true
            }
        }
    }

    //usually involves inflating a layout from xml and returning the holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context =parent.context
        val inflater = LayoutInflater.from(context)
        //inflate the custom layout
        val contactVIew = inflater.inflate(android.R.layout.simple_list_item_1,parent,false)
        //return a new holder instance
        return ViewHolder(contactVIew)

    }

    //involves populating data into the item through holder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //get the data model based on position
        val item = listOfItems.get(position)

        //set item views based on your views and data model
        holder.textView.text = item

    }

    override fun getItemCount(): Int {
        return listOfItems.size
    }
}