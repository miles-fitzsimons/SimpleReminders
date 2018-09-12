package com.example.miles.simplereminders

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        if(intent.getBooleanExtra("notification", false)) {
            txtTitleView.text = intent.getStringExtra("title")
            txtMsgView.text = intent.getStringExtra("message")
        }
    }
}