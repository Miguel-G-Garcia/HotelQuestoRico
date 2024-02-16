package com.miguel.proyecto_android;

import android.app.Application
import com.miguel.proyecto_android.core.AuthManager
import com.miguel.proyecto_android.core.FirestoreManager
class App: Application() {
    lateinit var auth: AuthManager
    lateinit var firestore: FirestoreManager

    override fun onCreate() {
        super.onCreate()
        auth = AuthManager(this)
        firestore = FirestoreManager(this)
        
    }
}