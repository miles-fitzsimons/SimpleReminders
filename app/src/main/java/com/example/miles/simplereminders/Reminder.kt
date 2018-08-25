package com.example.miles.simplereminders

import android.content.Context
import org.json.JSONException
import org.json.JSONObject

class Reminder(val reminderText: String) {
    companion object {
        fun getRemindersFromFile(fileName: String, context: Context): ArrayList<Reminder> {
            val reminderList = ArrayList<Reminder>()

            try {
                // Load data
                val jsonString = loadJsonFromAsset(fileName, context)
                val json = JSONObject(jsonString)
                val reminders = json.getJSONArray("reminders")

                (0 until reminders.length()).mapTo(reminderList) {
                    Reminder(reminders.getJSONObject(it).getString("reminderText"))
                }
            }
            catch (e: JSONException) {
                e.printStackTrace()
            }
            return reminderList
        }

        private fun loadJsonFromAsset(fileName: String, context: Context): String? {
            var json: String? = null

            try {
                val inputStream = context.assets.open(fileName)
                val size = inputStream.available()
                val buffer = ByteArray(size)
                inputStream.read(buffer)
                inputStream.close()
                json = String(buffer, Charsets.UTF_8)
            }
            catch (ex: java.io.IOException){
                ex.printStackTrace()
                return null
            }
            return json
        }
    }
}