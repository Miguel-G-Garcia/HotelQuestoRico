package com.miguel.proyecto_android

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.miguel.proyecto_android.core.FirestoreManager
import com.miguel.proyecto_android.model.Reserva
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class ReservasViewModel(val firestore: FirestoreManager): ViewModel() {

    private val _state = MutableLiveData(UiState())
    val state: LiveData<UiState> get() = _state



    init {
        
        viewModelScope.launch {
            _state.value = _state.value?.copy(reservasFlow = firestore.getReservasFlow())
        }
    }

   fun navigateToCreate() {
        _state.value = _state.value?.copy(navigateToCreate = true)
    }

    fun navigateToCreateDone() {
        _state.value = _state.value?.copy(navigateToCreate = false)
    }

    fun deleteReserva(reservaID: String){
        viewModelScope.launch(Dispatchers.IO) {
            firestore.deleteReservaById(reservaID)
        }
    }
    fun addReserva(reserva: Reserva){
        viewModelScope.launch(Dispatchers.IO) {
            firestore.addReserva(reserva)
        }
    }
    fun updateReserva(reserva: Reserva){
        viewModelScope.launch(Dispatchers.IO) {
            firestore.updateReserva(reserva)
        }
    }
    
    suspend fun findReservaById(id: String): Reserva{
        val reserva = firestore.findReservaById(id)
        Log.i("RM_find",reserva.toString())
        return reserva
    }
    
    
    data class UiState(
        val loading: Boolean = false,
        val navigateToCreate: Boolean = false,
        val reservasFlow: Flow<List<Reserva>>? = null
    )
}

class ReservasViewModelFactory(private val firestore: FirestoreManager): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ReservasViewModel(firestore) as T
    }

}