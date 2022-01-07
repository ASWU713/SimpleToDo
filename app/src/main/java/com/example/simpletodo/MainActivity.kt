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
    lateinit var adapter: TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener{
            override fun onItemLongClicked(position: Int){
                //1. remove the item from the list
                listOfTasks.removeAt(position)
                //2. notify the adapter that our data set has changed
                adapter.notifyDataSetChanged()

                saveItems()
            }
        }
        loadItems()

        //look up recyclerview in layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        //Create adapter passing in the sample user data
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener)
        //attach the adapter to the recyclerview to populate items
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        //set up the btn and input field, so that the user can enter a task and add it to the list
        val inputTextField = findViewById<EditText>(R.id.addTaskField)
        //get a reference to the button
        //and then set an onclick listener
        findViewById<Button>(R.id.button).setOnClickListener{
            //1.Grab the text the user has inputted into @id/addtaskfield
            val userInputtedTask = inputTextField.text.toString()

            //2. Add the string to our list of tasks: ListOfTasks
            listOfTasks.add(userInputtedTask)
            //notify the adapter that our data has been update
            adapter.notifyItemInserted(listOfTasks.size - 1)

            //3. Reset text field
            inputTextField.setText("")

            saveItems()

        }
    }

    //save the data the user has inputted
    //save data by writing and reading from a file

    //Get the file we need
    fun getDataFile() : File {
        return File(filesDir, "data.txt")
    }

    //load the items by reading every line in the data file
    fun loadItems(){
        try{
            listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch(ioException: IOException){
            ioException.printStackTrace()
        }

    }

    //save items by writing them into our data file
    fun saveItems(){
        try{
            FileUtils.writeLines(getDataFile(), listOfTasks)
        } catch(ioException: IOException){
            ioException.printStackTrace()
        }


    }
}