package com.miguel.proyecto_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.miguel.proyecto_android.databinding.ActivityLoginBinding
import com.miguel.proyecto_android.databinding.ActivityMainBinding
import com.miguel.proyecto_android.databinding.ViewReservaBinding


class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		
		var binding = ActivityMainBinding.inflate(layoutInflater)
		with(binding){
			signOut.setOnClickListener{
				(application as App).auth.signOut()
				finish()
			}
			options.setOnClickListener{
				val intent = Intent(this@MainActivity, AnalyticsActivity::class.java)
				startActivity(intent)
			}
		}
		
		
	}
}