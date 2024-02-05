package com.miguel.proyecto_android

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.miguel.proyecto_android.databinding.FragmentModifyBinding
import java.util.UUID


class ModifyFragment : Fragment(R.layout.fragment_modify) {
    companion object{
        const val POS = "posicion"
    }
    
    val db = FirebaseFirestore.getInstance()
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentModifyBinding.bind(view).apply {
            val posicion = arguments?.getInt(POS)
            val user = ((activity as MainActivity)).getCurrentUser().toString()
                
            val id: String
            var image = randomImage()

            if (posicion != -1) {
                val reserva = ReservaManager.findReserva(posicion!!)
                id = reserva.id
                fecha.setText(reserva.fecha)
                image = reserva.foto
                Glide.with(picture)
                    .load(image)
                    .into(picture)
            }else{
                id = UUID.randomUUID().toString()
                Glide.with(picture)
                    .load(image)
                    .into(picture)
            }

            btnSave.setOnClickListener{
                val reserva = Reserva(
                    id = id,
                    fecha =  fecha.text.toString(),
                    foto = image,
                    user = user
                )
                if(posicion != -1){
                    ReservaManager.modifyReserva(posicion, reserva)
                   
                }else{
                    ReservaManager.addReserva(reserva)
                    
                }
                db.collection("reservas").document(id)
                    .set(reserva).addOnFailureListener { e ->
                        Log.e("DB_SAVE","Error actualizando reserva: $e")
                    }
                findNavController().navigate(R.id.action_global_reservaListFragment)
            }

            btnCancel.setOnClickListener{
                parentFragmentManager.popBackStack()
            }

        }

        
        

    }
    fun randomImage(): String {
        return "https://picsum.photos/300"
    }
}