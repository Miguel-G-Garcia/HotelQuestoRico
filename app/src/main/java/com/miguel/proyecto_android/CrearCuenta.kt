package com.miguel.proyecto_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.miguel.proyecto_android.core.AuthManager
import com.miguel.proyecto_android.core.AuthRes
import com.miguel.proyecto_android.databinding.ActivityCrearCuentaBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.UUID

class CrearCuenta : AppCompatActivity() {
   
    val db = FirebaseFirestore.getInstance()
    private lateinit var auth: AuthManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityCrearCuentaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = AuthManager(this)
        with(binding) {
            btnRegistrar.setOnClickListener {
                signUp(etEmail.text.toString(), etPassword.text.toString())
            }
            tvIniciaSesion.setOnClickListener {
                finish()
            }
        }
    }

    private fun ActivityCrearCuentaBinding.signUp(eMail: String, password: String) {
        if (!eMail.isNullOrEmpty() && !password.isNullOrEmpty()) {
            GlobalScope.launch {
                when (auth.createUserWithEmailAndPassword(
                    eMail,
                    password
                )){
                    is AuthRes.Success -> {
                        Snackbar.make(root, "Usuario creado correctamente", Snackbar.LENGTH_LONG    )
                            .show()
                        
                        db.collection("client").document(auth.getCurrentUser()?.email.toString())
                            .set(
                                hashMapOf("nickname" to nickName.text.toString(),
                                    "phone" to phone.text.toString())
                            ).addOnFailureListener { e ->
                                println("Error actualizando reserva: $e")
                            }
                        finish()
                    }
                    is AuthRes.Error -> {
                        Snackbar.make(root, "Error al crear el usuario", Snackbar.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
        else{
            Snackbar.make(root, "Debes llenar todos los campos", Snackbar.LENGTH_SHORT).show()
        }
    }
}