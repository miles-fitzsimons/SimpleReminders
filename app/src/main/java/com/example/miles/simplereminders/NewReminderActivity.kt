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
import kotlin.collections.ArrayList

class NewReminderActivity : AppCompatActivity() {

    private var year: Int = 0
    private var month: Int = 0
    private var day: Int = 0
    private var hour: Int = 0
    private var minute: Int = 0
    private var dateString: String = ""

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
                dateString = "${day} ${getMonth(month)} ${year}"

                if(todayYear == mYear && todayMonth == mMonth && todayDay == mDay) {
                    date_text_view.text = "Today"
                } else if (todayYear == mYear && todayMonth == mMonth && todayDay + 1 == mDay) {
                    date_text_view.text = "Tomorrow"
                } else {
                    date_text_view.text =  dateString
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

    private fun getMonth(month: Int): String {
        when (month) {
            0 -> return "January"
            1 -> return "February"
            2 -> return "March"
            3 -> return "April"
            4 -> return "May"
            5 -> return "June"
            6 -> return "July"
            7 -> return "August"
            8 -> return "September"
            9 -> return "October"
            10 -> return "November"
            11 -> return "December"
        }
        return "Oops!"
    }

    fun onNewReminderSaveButtonPressed(view: View) {
        // TODO ensure input isn't empty - show toast?
        val inputString = new_reminder_input_remind_me_to.text.toString()

        // Get the current list of reminders
        val appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext())
        val json = appSharedPrefs.getString("reminders", "")
        val gson = Gson()
        var reminders:ArrayList<Reminder> = ArrayList()
        if(!json.none()) {
            reminders = gson.fromJson<Any>(json, object : TypeToken<ArrayList<Reminder>>() {}.type) as ArrayList<Reminder>
        }

        // Add the new reminder and save
        reminders.add(Reminder(inputString, dateString))
        val prefsEditor = appSharedPrefs.edit()
        val updatedJson = gson.toJson(reminders) //tasks is an ArrayList instance variable
        prefsEditor.putString("reminders", updatedJson)
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
