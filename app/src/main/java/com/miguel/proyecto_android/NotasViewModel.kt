package com.miguel.proyecto_android

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.miguel.proyecto_android.core.FirestoreManager
import com.miguel.proyecto_android.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class NotasViewModel(val firestore: FirestoreManager): ViewModel() {

    private val _state = MutableLiveData(UiState())
    val state: LiveData<UiState> get() = _state



    init {
        _state.value = _state.value?.copy(notes = firestore.getNotesFlow())
    }

    fun navigateToCreate() {
        _state.value = _state.value?.copy(navigateToCreate = true)
    }

    fun navigateToCreateDone() {
        _state.value = _state.value?.copy(navigateToCreate = false)
    }

    fun deleteNote(note: Note){
        viewModelScope.launch(Dispatchers.IO) {
            firestore.deleteNoteById(note.id)
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val notes: Flow<List<Note>>? = null,
        val navigateToCreate: Boolean = false
    )
}

class NotasViewModelFactory(private val firestore: FirestoreManager): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NotasViewModel(firestore) as T
    }

}