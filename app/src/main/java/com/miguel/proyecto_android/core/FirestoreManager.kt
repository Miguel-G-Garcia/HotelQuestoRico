package com.miguel.proyecto_android.core

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.miguel.proyecto_android.App
import com.miguel.proyecto_android.ReservasViewModel
import com.miguel.proyecto_android.model.Cliente
import com.miguel.proyecto_android.model.Reserva
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirestoreManager(context: Context) {
    val firestore = FirebaseFirestore.getInstance()
    val auth = (context.applicationContext as App).auth
    val userId = auth.getCurrentUser()?.email

    suspend fun addReserva(reserva: Reserva){
        reserva.user = userId!!
        Log.i("ForeStore_Manager_Resrv_ID",reserva.id)
        firestore.collection("reservas").document(reserva.id).set(reserva).await()
    }
    suspend fun addClient(cliente: Cliente){
        firestore.collection("cliente").document(auth.getCurrentUser()?.email!!).set(cliente).await()
    }

    suspend fun updateReserva(reserva: Reserva) {
        reserva.user = userId!!
        
        val reservaRef = reserva.id?.let {
            firestore.collection("reservas").document(it)
        }
        reservaRef?.set(reserva)?.await()
    }
    
    suspend fun deleteReservaById(reservaId: String) {
        firestore.collection("reservas").document(reservaId).delete().await()
    }
    suspend fun findReservaById(reservaId: String): Reserva {
        var reserva = Reserva()
        firestore.collection("reservas").document(reservaId)
            .get()
            .addOnSuccessListener {
                reserva = it.toObject(Reserva::class.java)!!
                Log.i("FSM_reserva", reserva.toString())
            }.addOnFailureListener{
                Log.e("FSM_reserva_ERROR","No se pudo recoger la reserva")
            }
            .await()
        Log.i("FSM_reserva", reserva.toString())
        return reserva
    }

    fun getReservasFlow(): Flow<List<Reserva>> = callbackFlow {
        val reservasQuery = firestore.collection("reservas")
            .whereEqualTo("user", userId)
        val subscription = reservasQuery.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }
            
            snapshot?.let{ querySnapshot ->
                val reservas = mutableListOf<Reserva>()
                
                for (document in querySnapshot.documents) {
                    val reserva = document.toObject(Reserva::class.java)
                    reserva?.id = document.id
                    reserva?.let { reservas.add(reserva) }
                }
                trySend(reservas ?: emptyList()).isSuccess
            }
        }
        awaitClose { subscription.remove() }
        
    }
    
    suspend fun getCliente(): Cliente {
        /*var cliente = Cliente()
        firestore.collection("client").document(userId!!)
            .get()
            .addOnSuccessListener {
                cliente = it.toObject(Cliente::class.java)!!
            }.addOnFailureListener{
                Log.e("FSM_rcliente_ERROR","No se pudo recoger la reserva")
            }
            .await()
        Log.i("FSM_cliente", cliente.toString())*/
        val clienteDocument = firestore.collection("cliente").document(userId!!).get().await()
        return clienteDocument.toObject(Cliente::class.java) ?: Cliente()
        //return cliente
    }
 
}
