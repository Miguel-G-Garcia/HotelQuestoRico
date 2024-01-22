package com.miguel.proyecto_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.miguel.proyecto_android.App
import com.miguel.proyecto_android.R
import com.miguel.proyecto_android.core.AuthManager
import com.miguel.proyecto_android.core.AuthRes
import com.miguel.proyecto_android.databinding.ActivityRecuperaContrasenaBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RecuperaContrasena : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRecuperaContrasenaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRecuperaContrasena.setOnClickListener {
            GlobalScope.launch {
                when ((application as App).auth.resetPassword(binding.etEmail.text.toString())){
                    is AuthRes.Success -> {
                        Snackbar.make(binding.root, "Correo enviado correctamente", Snackbar.LENGTH_SHORT).show()
                        finish()
                    }
                    is AuthRes.Error -> {
                        Snackbar.make(binding.root, "Error al enviar el correo", Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}