package com.miguel.proyecto_android

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.google.firebase.analytics.FirebaseAnalytics
import com.miguel.proyecto_android.core.AuthManager
import com.miguel.proyecto_android.core.FirestoreManager
import com.miguel.proyecto_android.databinding.FragmentSettingsBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SettingsFragment : Fragment(R.layout.fragment_settings) {
	private lateinit var firebaseAnalytics: FirebaseAnalytics
	private lateinit var auth: AuthManager
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		
		val factory = ClientesViewModelFactory(FirestoreManager((requireActivity() as MainActivity)))
		val viewModel: ClientesViewModel by viewModels{factory}
		auth = AuthManager((activity as MainActivity))
		firebaseAnalytics = FirebaseAnalytics.getInstance((activity as MainActivity))
		
		
		val binding = FragmentSettingsBinding.bind(view).apply {
			constraintLayout2.visibility = View.GONE
			progressBar.visibility = View.VISIBLE
			/*FirebaseAuth.getInstance().signOut()
			(activity as MainActivity).finish()*/
			
			
			
			singOut.setOnClickListener {
				
				auth.signOut()
				(activity as MainActivity).finish()
			}
			
			
		}
		loadClient(viewModel, binding)
		
	}
	
	private fun loadClient(viewModel: ClientesViewModel, binding: FragmentSettingsBinding) {
		viewModel.viewModelScope.launch(Dispatchers.Main) {
			val user1 = viewModel.getCliente()
			val user = auth.getCurrentUser()
			binding.apply {
				emailUser.text = auth.getCurrentUser()?.email
				nameUser.text = user1.nickname
				phoneUser.text = user1.phone.toString()
				addressUser.text = user1.address
				if (user?.photoUrl != null) {
					Glide.with(imageUser).load(user?.photoUrl).into(imageUser)
				} else {
					Glide.with(imageUser).load("https://picsum.photos/200").into(imageUser)
				}
				constraintLayout2.visibility = View.VISIBLE
				progressBar.visibility = View.GONE
			}
		}
	}
}