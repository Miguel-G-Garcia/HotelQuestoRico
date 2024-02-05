package com.miguel.proyecto_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import com.miguel.proyecto_android.core.AuthManager
import com.miguel.proyecto_android.databinding.ActivityAnalyticsBinding
class AnalyticsActivity : AppCompatActivity() {
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private lateinit var auth: AuthManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAnalyticsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = AuthManager(this)
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)

        val user = auth.getCurrentUser()

        binding.emailUser.text = user?.email
        binding.nameUser.text = user?.displayName
        if (user?.photoUrl != null) {
            Glide.with(binding.imageUser).load(user?.photoUrl).into(binding.imageUser)
        } else {
            Glide.with(binding.imageUser).load("https://picsum.photos/200").into(binding.imageUser)
        }
        binding.exit.setOnClickListener {
            finish()
        }
        binding.singOut.setOnClickListener {
            auth.signOut()
            finish()
        }

        binding.config.setOnClickListener {
            firebaseAnalytics.logEvent("button_clicked"){
                param("button_name", "button2")
            }
        }
    }
}