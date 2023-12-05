package com.miguel.proyecto_android

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.miguel.proyecto_android.databinding.FragmentModifyBinding


class ModifyFragment : Fragment(R.layout.fragment_modify) {
    companion object{
        const val POS = "posicion"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentModifyBinding.bind(view).apply {
            val posicion = arguments?.getInt(POS)

            var image = "https://picsum.photos/300"

            if (posicion != -1) {
                val client = ClientManager.findClient(posicion!!)
                nombre.setText(client.nombre)
                gmail.setText(client.gmail)
                telefono.setText(client.telefono.toString())
                image = client.foto
                Glide.with(picture)
                    .load(image)
                    .into(picture)
            }else{
                Glide.with(picture)
                    .load(image)
                    .into(picture)
            }

            btnSave.setOnClickListener{
                val client = Client(
                    nombre =  nombre.text.toString(),
                    gmail =  gmail.text.toString(),
                    telefono =  telefono.text.toString().toInt(),
                    foto = image
                )
                if(posicion != -1){
                    ClientManager.modifyCLient(posicion, client)
                }else{
                    ClientManager.addClient(client)
                }

                parentFragmentManager.popBackStack()

            }

            btnCancel.setOnClickListener{
                parentFragmentManager.popBackStack()
            }

        }



    }
}