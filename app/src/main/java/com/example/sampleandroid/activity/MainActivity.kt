package com.example.sampleandroid.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sampleandroid.R
import com.example.sampleandroid.container.App
import com.example.sampleandroid.fragment.SearchFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as App).container.inject(this)

        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, SearchFragment.newInstance())
                .commitNow()
        }
    }
}
