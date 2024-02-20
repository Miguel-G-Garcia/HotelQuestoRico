package com.miguel.proyecto_android

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.miguel.proyecto_android.core.FirestoreManager
import com.miguel.proyecto_android.model.Cliente
import com.miguel.proyecto_android.model.Reserva
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class ClientesViewModel(val firestore: FirestoreManager): ViewModel() {



    fun addCliente(cliente: Cliente){
        viewModelScope.launch(Dispatchers.IO) {
            firestore.addClient(cliente)
        }
    }
    suspend fun getCliente(): Cliente {
            return firestore.getCliente()
    }
    fun updateCliente(cliente: Cliente){
        viewModelScope.launch(Dispatchers.IO) {
            firestore.addClient(cliente)
        }
    }
    fun updateReserva(reserva: Reserva){
        viewModelScope.launch(Dispatchers.IO) {
            firestore.updateReserva(reserva)
        }
    }
    
}

class ClientesViewModelFactory(private val firestore: FirestoreManager): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ClientesViewModel(firestore) as T
    }

}