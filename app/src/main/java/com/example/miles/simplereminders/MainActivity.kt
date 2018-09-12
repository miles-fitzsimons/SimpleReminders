package com.example.miles.simplereminders

import android.app.Notification
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.NotificationCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ListView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var listView: ListView

    private val mNotificationTime = Calendar.getInstance().timeInMillis + 5000
    private var mNotified = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.main_toolbar))

        if(!mNotified) {
            NotificationUtils().setNotification(mNotificationTime, this@MainActivity)
        }

        val appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext())
        val json = appSharedPrefs.getString("reminders", "")
        val gson = Gson()
        var reminders: ArrayList<Reminder> = ArrayList()
        if(!json.none()) {
            reminders = gson.fromJson<Any>(json, object : TypeToken<ArrayList<Reminder>>() {}.type) as ArrayList<Reminder>
        }

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

    fun onNotifyMeButtonPressed(view: View) {
        temp_text_view.text = "Notified!"
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
    }


    fun onNewReminderButtonPressed(view: View) {
        val newReminderIntent = Intent(this, NewReminderActivity::class.java)
        startActivity(newReminderIntent);
    }

}
