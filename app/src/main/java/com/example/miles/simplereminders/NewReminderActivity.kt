package com.example.miles.simplereminders

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.content.ContextCompat.startActivity
import android.view.Menu
import android.view.View
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.miles.simplereminders.R.id.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_new_reminder.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.Year
import java.util.*

class NewReminderActivity : AppCompatActivity() {

//    var dateIcon: ImageView? = null
//    var linearLayout: LinearLayout? = null as LinearLayout
    var selectedDate: String = "31 August 2018"
    private var year: Int = 0
    private var month: Int = 0
    private var day: Int = 0
    private var hour: Int = 0
    private var minute: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_reminder)
        setSupportActionBar(findViewById(R.id.new_reminder_toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "New Reminder"

        val calendar = Calendar.getInstance()
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH)
        day = calendar.get(Calendar.DAY_OF_MONTH)
        val todayYear = calendar.get(Calendar.YEAR)
        val todayMonth = calendar.get(Calendar.MONTH)
        val todayDay = calendar.get(Calendar.DAY_OF_MONTH)

        date_linear_layout.setOnClickListener{
            val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{view, mYear, mMonth, mDay ->
                year = mYear
                month = mMonth
                day = mDay

                if(todayYear == mYear && todayMonth == mMonth && todayDay == mDay) {
                    date_text_view.text = "Today"
                } else if (todayYear == mYear && todayMonth == mMonth && todayDay + 1 == mDay) {
                    date_text_view.text = "Tomorrow"
                } else {
                    date_text_view.text =  "${day.toString()}/${(month + 1).toString()}/${year.toString()}"
                }
            }, year, month, day)
            datePickerDialog.show()
        }

        time_linear_layout.setOnClickListener{
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener{timePicker, mHour, mMinute ->
                cal.set(Calendar.HOUR_OF_DAY, mHour)
                cal.set(Calendar.MINUTE, mMinute)

                hour = mHour
                minute = mMinute

                time_text_view.text = SimpleDateFormat("h:mm a").format(cal.time)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false).show()
        }
    }

    fun onNewReminderSaveButtonPressed(view: View) {
        val inputString = new_reminder_input_remind_me_to.text.toString()

        // Get the list of reminders
        val appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext())
        val json = appSharedPrefs.getString("reminders", "")
        val gson = Gson()
        val reminders = gson.fromJson<Any>(json, object : TypeToken<ArrayList<Reminder>>(){}.type) as ArrayList<Reminder>
        reminders.add(Reminder(inputString))

        // Save the reminders
        val prefsEditor = appSharedPrefs.edit()
//        val gson = Gson()
        val json2 = gson.toJson(reminders) //tasks is an ArrayList instance variable
        prefsEditor.putString("reminders", json2)
        prefsEditor.commit()
        


        val remindersScreenIntent = Intent(this, MainActivity::class.java)
        startActivity(remindersScreenIntent)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }


}
