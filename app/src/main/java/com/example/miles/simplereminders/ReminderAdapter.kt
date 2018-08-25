package com.example.miles.simplereminders

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import javax.sql.CommonDataSource

import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.widget.ImageView
import android.widget.TextView
import org.w3c.dom.Text


class ReminderAdapter(private val context: Context, private val dataSource: ArrayList<Reminder>) : BaseAdapter() {
    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    // Companion object for styles?

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val holder: ViewHolder

        if(convertView == null) {
            view = inflater.inflate(R.layout.list_item_reminder, parent, false)

            holder = ViewHolder()
            holder.reminderTextTextView = view.findViewById(R.id.reminder_list_reminder_text)

            view.tag = holder
        }
        else {
            view = convertView
            holder = convertView.tag as ViewHolder
        }

        val reminderTextTextView = holder.reminderTextTextView

        val reminder = getItem(position) as Reminder

        reminderTextTextView.text = reminder.reminderText
        // can set styles, fonts etc here

        return view
    }

    private class ViewHolder {
        lateinit var reminderTextTextView: TextView
    }
}