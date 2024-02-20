package com.miguel.proyecto_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.miguel.proyecto_android.core.AuthManager
import com.miguel.proyecto_android.core.AuthRes
import com.miguel.proyecto_android.core.FirestoreManager
import com.miguel.proyecto_android.databinding.ActivityCrearCuentaBinding
import com.miguel.proyecto_android.model.Cliente
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.UUID

class CrearCuenta : AppCompatActivity() {
   
    val db = FirebaseFirestore.getInstance()
    private lateinit var auth: AuthManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory =
            ClientesViewModelFactory(FirestoreManager(this))
        
        val binding = ActivityCrearCuentaBinding.inflate(layoutInflater)
        val viewModel: ClientesViewModel by viewModels { factory }
        
        setContentView(binding.root)
        auth = AuthManager(this)
        with(binding) {
            btnRegistrar.setOnClickListener {
                signUp(etEmail.text.toString(), etPassword.text.toString(), viewModel)
            }
            tvIniciaSesion.setOnClickListener {
                finish()
            }
        }
    }

    private fun ActivityCrearCuentaBinding.signUp(
        eMail: String,
        password: String,
        viewModel: ClientesViewModel
    ) {
        val nickName = atNickName.text.toString()
        val phone = atPhone.text.toString()
        if (eMail.isNotEmpty() && password.isNotEmpty() && nickName.isNotEmpty() && phone.isNotEmpty()) {
            GlobalScope.launch {
                when (auth.createUserWithEmailAndPassword(
                    eMail,
                    password
                )){
                    is AuthRes.Success -> {
                        Snackbar.make(root, "Usuario creado correctamente", Snackbar.LENGTH_LONG    )
                            .show()
                        when (auth.signInWithEmailAndPassword(
                            eMail,
                            password
                        )){
                            is AuthRes.Success -> {
                                Snackbar.make(root, "Inicio de sesión correcto", Snackbar.LENGTH_SHORT)
                                    .show()
                                viewModel.addCliente(
                                    Cliente(
                                        nickName,
                                        phone.replace("[^0-9]".toRegex(), "").toInt() ,
                                        atAddress.text.toString(),
                                    )
                                )
                                val intent = Intent(this@CrearCuenta, MainActivity::class.java)
                                startActivity(intent)
                            }
                            is AuthRes.Error -> {
                                Snackbar.make(root, "Error al iniciar sesión", Snackbar.LENGTH_SHORT).show()
                            }
                        }
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