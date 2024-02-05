package com.miguel.proyecto_android

import android.util.Log
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.miguel.proyecto_android.databinding.FragmentReservaListBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ReservaListFragment : Fragment(R.layout.fragment_reserva_list) {
    private lateinit var binding: FragmentReservaListBinding
        val db = FirebaseFirestore.getInstance()
    
   
       
    
    
    
    private val adapter = ReservaAdapter(this@ReservaListFragment){ reserva ->
        findNavController().navigate(R.id.action_reservaListFragment_to_detailFragment,
            bundleOf("reserva" to reserva)
        ) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        

        binding = FragmentReservaListBinding.bind(view).apply {
            
           
           // if (adapter.itemCount == 0){
                loadItems()
           // }
            recyclerView.adapter = adapter
        }

    }
    
    
    
    
    private fun loadItems() {
            GlobalScope.launch(Dispatchers.Main) {
                
                binding.recyclerView.visibility = View.GONE
                
                binding.progressBar.visibility =View.VISIBLE
               
                val db = FirebaseFirestore.getInstance()
                val user = ((activity as MainActivity)).getCurrentUser().toString()
                val reservas = mutableListOf<Reserva>()
                db.collection("reservas")
                    .whereEqualTo("user", user)
                    .get()
                    .addOnSuccessListener { documents ->
                        for (document in documents) {
                            
                            val reserva = document.toObject(Reserva::class.java)
                            reservas.add(reserva)
                            Log.i("DB_LOG", "Datos recogidos correctamente" + reserva.toString())
                        }
                        adapter.reservas = reservas
                        adapter.notifyDataSetChanged()
                        binding.progressBar.visibility = View.GONE
                        binding.recyclerView.visibility = View.VISIBLE
                        
                        ReservaManager.setReservas(reservas)
                        Log.i("DB_MANAGER", "Datos del manager" + ReservaManager.getReservas().toString())
                    }
                    .addOnFailureListener { exception ->
                        Log.e("DB_LOG_ERROR", "Error obteniendo documentos: $exception")
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
                
                loadItems()
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