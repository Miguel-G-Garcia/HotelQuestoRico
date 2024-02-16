package com.miguel.proyecto_android.core

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.miguel.proyecto_android.App
import com.miguel.proyecto_android.ReservasViewModel
import com.miguel.proyecto_android.model.Reserva
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirestoreManager(context: Context) {
    val firestore = FirebaseFirestore.getInstance()
    val auth = (context.applicationContext as App).auth
    val userId = auth.getCurrentUser()?.uid
    val COLECCION = "reservas"

    suspend fun addReserva(reserva: Reserva){
        firestore.collection(COLECCION).add(reserva).await()
    }

    suspend fun updateReserva(reserva: Reserva) {
        val reservaRef = reserva.id?.let {
            firestore.collection("reservas").document(it)
        }
        reservaRef?.set(reserva)?.await()
    }

    suspend fun deleterReservaById(reservaId: String) {
        firestore.collection("reservas").document(reservaId).delete().await()
    }
    
    suspend fun getReservaList(): MutableList<Reserva> {
        val listReservas = mutableListOf<Reserva>()
        firestore.collection("reservas")
            .whereEqualTo("userId", userId)
            .orderBy("title")
            .get()
            .addOnSuccessListener{
                    documents ->
                for (document in documents) {
                    
                    val reserva = document.toObject(Reserva::class.java)
                    listReservas.add(reserva)
                }
                Log.i("VM_LOG_GET", "Datos recogidos correctamente" + listReservas.toString())
                
            }
            .addOnFailureListener{exception ->
                Log.e("VM_LOG_ERROR", "Error obteniendo documentos: $exception")
            }
            .await()
        return listReservas
    }

    fun getReservasFlow(): Flow<List<Reserva>> = callbackFlow {
        val reservasRef = firestore.collection("reservas")
            .whereEqualTo("user", userId)
            .orderBy("id")
        val subscription = reservasRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }
            snapshot?.let{ QuerySnapshot ->
                val reservas = mutableListOf<Reserva>()
                for (document in QuerySnapshot.documents) {
                    val reserva = document.toObject(Reserva::class.java)
                    reserva?.id = document.id
                    reserva?.let { reservas.add(reserva) }
                }
                trySend(reservas ?: emptyList()).isSuccess
            }
        }
        awaitClose { subscription.remove() }
        
    }
    
 
}
