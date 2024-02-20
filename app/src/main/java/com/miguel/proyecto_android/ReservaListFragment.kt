package com.miguel.proyecto_android

import android.util.Log
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
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
    
    
    private val adapter = ReservaAdapter(this@ReservaListFragment){ reserva ->
        findNavController().navigate(R.id.action_reservaListFragment_to_detailFragment,
            bundleOf("reserva" to reserva)
        ) }
    private lateinit var VM: ReservasViewModel
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val factory = ReservasViewModelFactory(FirestoreManager((requireActivity() as MainActivity)))
        val viewModel: ReservasViewModel by viewModels{factory}
        
        VM = viewModel
        binding = FragmentReservaListBinding.bind(view)
        binding.apply {
            recyclerView.visibility = View.GONE
            noReservText.visibility = View.GONE
            
            progressBar.visibility = View.VISIBLE
        }
        loadItems(viewModel)
    }
    
    private fun loadItems(viewModel: ReservasViewModel) {

        viewModel.state.observe(viewLifecycleOwner) { state ->
            viewLifecycleOwner.lifecycleScope.launch {
                state.reservasFlow?.collect { reservas ->
                    
               
                   
                    adapter.reservas = reservas
                    Log.i("Adapter_reservas", adapter.reservas.toString())
                    adapter.notifyDataSetChanged()
                    binding.recyclerView.adapter = adapter
                    binding.progressBar.visibility = View.GONE
                    if (!reservas.isNullOrEmpty()) {
                        binding.recyclerView.visibility = View.VISIBLE
                    } else {
                        binding.noReservText.visibility = View.VISIBLE
                    }
                }
            }
        }
    }
    
    fun onBorrar(id: String) {VM.deleteReserva(id)}
    
    
    
    fun onModificar(id: String) {
        findNavController().navigate(
            R.id.action_reservaListFragment_to_modifyFragment,
            bundleOf(ModifyFragment.ID to id)
        )
        
    }
    
}