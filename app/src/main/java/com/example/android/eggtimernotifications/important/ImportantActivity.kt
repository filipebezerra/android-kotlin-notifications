package com.example.android.eggtimernotifications.important

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import com.example.android.eggtimernotifications.R
import com.example.android.eggtimernotifications.databinding.ImportantActivityBinding

class ImportantActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView<ImportantActivityBinding>(this, R.layout.important_activity)
    }
}