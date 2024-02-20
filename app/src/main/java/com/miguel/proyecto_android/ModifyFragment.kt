package com.miguel.proyecto_android

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.text.set
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.miguel.proyecto_android.core.FirestoreManager
import com.miguel.proyecto_android.databinding.FragmentModifyBinding
import com.miguel.proyecto_android.model.Reserva
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.UUID


class ModifyFragment : Fragment(R.layout.fragment_modify) {
    companion object {
        const val ID = "id"
    }
    

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory =
            ReservasViewModelFactory(FirestoreManager((requireActivity() as MainActivity)))
        
        val binding = FragmentModifyBinding.bind(view)
        val viewModel: ReservasViewModel by viewModels { factory }
        binding.progressBar.visibility = View.VISIBLE
        binding.constraintLayout.visibility = View.GONE
        
        val id = arguments?.getString(ID)
        
        val image = randomImage()
        
        if (!id.equals("null")) {
            loadReserva(id!!, binding, viewModel)
           
        } else {
            with(binding) {
                Glide.with(picture)
                    .load(image)
                    .into(picture)
                binding.progressBar.visibility = View.GONE
                binding.constraintLayout.visibility = View.VISIBLE
            }
            
        }
        with(binding) {
            btnSave.setOnClickListener {
                
                if (!id.equals("null")) {
                    viewModel.updateReserva(
                        Reserva(
                            id = id!!,
                            fechaInicio = fechainicio.text.toString(),
                            fechaFinal = fechafinal.text.toString(),
                            ocupantes = ocupantes.text.toString(),
                            foto = image,
                            ""
                        )
                    )
                } else {
                    viewModel.addReserva(
                        Reserva(
                            id = UUID.randomUUID().toString(),
                            fechaInicio = fechainicio.text.toString(),
                            fechaFinal = fechafinal.text.toString(),
                            ocupantes = ocupantes.text.toString(),
                            foto = image,
                            ""
                        )
                    )
                    
                }
                
                findNavController().navigate(R.id.action_global_reservaListFragment)
            }
            
            
        }
        
    }
    
    private fun loadReserva(id: String, binding: FragmentModifyBinding, viewModel: ReservasViewModel) {
        
        viewModel.viewModelScope.launch(Dispatchers.Main) {
            val reserva = viewModel.findReservaById(id)
            binding.fechainicio.setText(reserva.fechaInicio)
            binding.fechafinal.setText(reserva.fechaFinal)
            binding.ocupantes.setText(reserva.ocupantes)
            Glide.with(binding.picture).load(reserva.foto).into(binding.picture)
            binding.progressBar.visibility = View.GONE
            binding.constraintLayout.visibility = View.VISIBLE
        }
    }
    
    fun randomImage(): String {
        return "https://picsum.photos/300"
    }
    }

