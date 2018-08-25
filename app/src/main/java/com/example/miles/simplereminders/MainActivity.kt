package com.example.miles.simplereminders

import android.arch.persistence.room.Room
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import kotlinx.android.synthetic.main.activity_main.*
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import com.google.gson.Gson
import org.json.JSONObject
//import com.example.miles.simplereminders.R.id.reminder_list
import java.io.File
import java.io.IOException
import android.content.SharedPreferences
import android.R.id.edit
import android.content.SharedPreferences.Editor
import android.preference.PreferenceManager
import com.google.gson.reflect.TypeToken









class MainActivity : AppCompatActivity() {
    private lateinit var listView: ListView // ????

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.main_toolbar))

        val appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext())
        val json = appSharedPrefs.getString("reminders", "")
        val gson = Gson()
        val reminders = gson.fromJson<Any>(json, object : TypeToken<ArrayList<Reminder>>(){}.type) as ArrayList<Reminder>

        if(reminders.size == 0) {
            temp_text_view.text = "EMPTY"
        }
        else {
            temp_text_view.text = "NOT EMPTY"
            listView = findViewById<ListView>(R.id.reminder_list_view)
            val listItems = arrayOfNulls<String>(reminders.size)
            for(i in 0 until reminders.size) {
              val reminder = reminders[i]
               listItems[i] = reminder.reminderText
           }

           val adapter = ReminderAdapter(this, reminders)
          listView.adapter = adapter
        }





//        var reminder = Reminder("ANOTHER one AGAIN")
//        var reminderArr = ArrayList<Reminder>()
//        reminderArr.add(reminder)
////        reminderArr.add(Reminder("Hello World!"))
//        val prefsEditor = appSharedPrefs.edit()
////        val gson = Gson()
////        val json = gson.toJson(reminderArr) //tasks is an ArrayList instance variable
//        prefsEditor.putString("reminders", json)
//        prefsEditor.commit()
//
//        val json2 = appSharedPrefs.getString("reminders", "")
//        val xx = gson.fromJson<Any>(json2, object : TypeToken<ArrayList<Reminder>>(){}.type) as ArrayList<Reminder>
////
////        val yy = 0
//
//
//        listView = findViewById<ListView>(R.id.reminder_list_view)
//        val reminderList = xx // Reminder.getRemindersFromFile("reminders.json", this)
//        val listItems = arrayOfNulls<String>(reminderList.size)
//        for(i in 0 until reminderList.size) {
//            val reminder = reminderList[i]
//            listItems[i] = reminder.reminderText
//        }
//
//        val adapter = ReminderAdapter(this, reminderList)
//        listView.adapter = adapter
//
//        val context = this
//        listView.setOnItemClickListener{_, _, position, _ ->
//            // do a thing?
//            // go to edit mode?
//        }


//        val fileName = "simpleReminders"
//        val fileName = "remindersTemp.json"
//        try {
//            val file = File(filesDir, fileName)
//            if(file.exists() && file.readText() != "") {
//                // reminder_list.text = file.readText()
//                // render the listView
//
//            }
//            else {
//                File(filesDir, fileName).createNewFile()
////                reminder_list.text = "Tap the + button"
//                // create the file, render something maybe
//            }
//        }
//        catch(e: IOException) {
//            // DISPLAY A TOAST OR SOMETHING?
//        }













    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
//        when(item!!.itemId) {
//            R.id.action_share -> {
//                reminder_list.text = "SHARING"
//                return true
//            }
//            R.id.action_about -> {
//                reminder_list.text = "ABOUTING"
//                return true
//            }
//            R.id.action_settings -> {
//                reminder_list.text = "SETTING SETTINGS"
//                return true
//            }
//
//        }
        return super.onOptionsItemSelected(item)
    }

    fun onNewReminderButtonPressed(view: View) {
        val newReminderIntent = Intent(this, NewReminderActivity::class.java)
        startActivity(newReminderIntent);
    }

}
