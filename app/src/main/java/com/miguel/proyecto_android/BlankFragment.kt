package com.miguel.proyecto_android

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.miguel.proyecto_android.core.AuthManager
import com.miguel.proyecto_android.databinding.FragmentBlankBinding


class BlankFragment : Fragment(R.layout.fragment_blank) {
	private lateinit var firebaseAnalytics: FirebaseAnalytics
	private lateinit var auth: AuthManager
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		
		auth = AuthManager((activity as MainActivity))
		firebaseAnalytics = FirebaseAnalytics.getInstance((activity as MainActivity))
		
		
		val binding = FragmentBlankBinding.bind(view).apply {
			
			
			
			/*FirebaseAuth.getInstance().signOut()
			(activity as MainActivity).finish()*/
			
			val user = auth.getCurrentUser()
			
			emailUser.text = user?.email
			nameUser.text = user?.displayName
			if (user?.photoUrl != null) {
				Glide.with(imageUser).load(user?.photoUrl).into(imageUser)
			} else {
				Glide.with(imageUser).load("https://picsum.photos/200").into(imageUser)
			}
			
			singOut.setOnClickListener {
				auth.signOut()
				(activity as MainActivity).finish()
			}
			
			
		}
	}
}