package com.nassiansoft.watchit

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : DaggerAppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Watchit)
        setContentView(R.layout.activity_main)


    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        NavigationUI.setupActionBarWithNavController(this,this.findNavController(R.id.fragment))

        super.onPostCreate(savedInstanceState)
    }


    override fun onSupportNavigateUp(): Boolean {
        return this.findNavController(R.id.fragment).navigateUp()
    }
}