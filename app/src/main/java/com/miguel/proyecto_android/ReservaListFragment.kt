package com.miguel.proyecto_android

import android.util.Log
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.miguel.proyecto_android.core.FirestoreManager
import com.miguel.proyecto_android.databinding.FragmentReservaListBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReservaListFragment : Fragment(R.layout.fragment_reserva_list) {
    
    private lateinit var binding: FragmentReservaListBinding
    private val db = FirebaseFirestore.getInstance()
    
    private lateinit var factory: ReservasViewModelFactory
    val viewModel: ReservasViewModel by viewModels{factory}
    
    
    private val adapter = ReservaAdapter(this@ReservaListFragment){ reserva ->
        findNavController().navigate(R.id.action_reservaListFragment_to_detailFragment,
            bundleOf("reserva" to reserva)
        ) }
    
    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        factory  = ReservasViewModelFactory(FirestoreManager((requireActivity() as MainActivity)))
       
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        viewModel.state.observe(viewLifecycleOwner) { state ->
            // Actualizar la interfaz de usuario con las reservas
            
                    binding = FragmentReservaListBinding.bind(view).apply {
                            loadItems(state)
                
            }
           
        }
        

    }
    
    
    
    
    private fun loadItems(state: ReservasViewModel.UiState) {
        viewLifecycleOwner.lifecycleScope.launch {
                state.reservasFlow?.collect { reservas ->
                
                binding.recyclerView.visibility = View.GONE
                binding.noReservText.visibility = View.GONE
                
                binding.progressBar.visibility =View.VISIBLE
                
                adapter.reservas = reservas
                adapter.notifyDataSetChanged()
                binding.progressBar.visibility = View.GONE
                if (!ReservaManager.isReservasEmpty()) {
                    binding.recyclerView.visibility = View.VISIBLE
                }else{
                    binding.noReservText.visibility = View.VISIBLE
                }
                }
                
        }
    }

    fun onBorrar(posicion: Int) {
        val id = ReservaManager.findReserva(posicion).id
        db.collection("reservas").document(id).delete()
            .addOnSuccessListener{
                Log.i("DB_BORRAR", "Reserva Borrada: id=$id")
                ReservaManager.deleteReserva(posicion)
                binding.recyclerView.adapter?.notifyItemRemoved(posicion)
                
            }
            .addOnFailureListener{
                Log.e("DB_BORRAR", "No se pudo borrar la reserva: id=$id")
                
            }
       

    }
    
    

    fun onModificar(posicion: Int) {
        findNavController().navigate(
            R.id.action_reservaListFragment_to_modifyFragment,
            bundleOf(ModifyFragment.POS to posicion)
        )

    }

}