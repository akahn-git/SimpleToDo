package com.example.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    var listOfTasks = mutableListOf<String>()
    lateinit var adapter:TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener=object : TaskItemAdapter.OnLongClickListener{
            override fun onItemLongClicked(position: Int) {
                //remove item from list
                listOfTasks.removeAt(position)

                //notify the adapter that something has changed
                adapter.notifyDataSetChanged()

                //save items
                saveItems()
            }

        }

        loadItems()

        //look up recyclerView in layout
        val recyclerView= findViewById<RecyclerView>(R.id.recyclerView)
        //create adapter passing in the sample user data
        adapter = TaskItemAdapter(listOfTasks,onLongClickListener)
        //attach the adapter to the recyclerView to populate items
        recyclerView.adapter=adapter
        //set the layout manager to position items
        recyclerView.layoutManager=LinearLayoutManager(this)

        //set up the button and input field so that the user can enter a task

        val inputFieldText = findViewById<EditText>(R.id.addTaskField)
        findViewById<Button>(R.id.button).setOnClickListener{
            //1. Grab the text the user has inputted into @id/addTaskField
            val userInputtedTask=inputFieldText.text.toString()

            //2. Add the string to our list of tasks: listOfTasks
            listOfTasks.add(userInputtedTask)

            //notify the adapter that our data has been updated
            adapter.notifyItemInserted(listOfTasks.size-1)

            //3. Reset text field
            inputFieldText.setText("")

            //4. save items
            saveItems()
        }
    }

    //save the data the user has inputted by writing and reading from a file

    //get the file we need
    fun getDataFile():File{
        //every line represents a specific task in our list
        return File(filesDir,"data.txt")
    }

    //load the items by reading every line in the file
    fun loadItems(){
        try {
            listOfTasks=FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        }catch (ioException:IOException){
            ioException.printStackTrace()
        }
    }

    //save items by writing them to the file
    fun saveItems(){
        try {
            FileUtils.writeLines(getDataFile(), listOfTasks)
        }catch (ioException:IOException){
            ioException.printStackTrace()
        }
    }
}