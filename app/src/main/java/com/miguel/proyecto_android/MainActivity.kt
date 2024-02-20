package com.miguel.proyecto_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.miguel.proyecto_android.core.AuthManager
import com.miguel.proyecto_android.core.FirestoreManager
import com.miguel.proyecto_android.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
	private lateinit var firebaseAnalytics: FirebaseAnalytics
	private lateinit var auth: AuthManager
	
	

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)
		auth = AuthManager(this)
		firebaseAnalytics = FirebaseAnalytics.getInstance(this)
		
		with(binding){
			toolbar.setOnItemSelectedListener { item ->
				when (item.itemId) {
					R.id.home -> findNavController(navHostFragment.id).popBackStack(R.id.reservaListFragment, false)
					R.id.opciones -> findNavController(navHostFragment.id).navigate(R.id.action_global_blankFragment)
					R.id.addReservation -> {
						findNavController(navHostFragment.id).navigate(R.id.action_global_modifyFragment,
							bundleOf(ModifyFragment.ID to "null"))
					}
					
				}
				true
			}
			
			
		}
		
		
	}
	
	
	
	
	
}