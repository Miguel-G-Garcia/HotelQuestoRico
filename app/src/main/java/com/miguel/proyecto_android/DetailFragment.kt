package com.miguel.proyecto_android

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.miguel.proyecto_android.databinding.FragmentDetailBinding
import com.miguel.proyecto_android.model.Reserva

class DetailFragment : Fragment(R.layout.fragment_detail) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentDetailBinding.bind(view).apply {
            val reserva = arguments?.getParcelable<Reserva>("reserva")
            fechainicio.setText(reserva?.fechaInicio)
            fechafinal.setText(reserva?.fechaFinal)
            ocupantes.setText(reserva?.ocupantes.toString())
            Glide.with(picture)
                .load(reserva?.foto)
                .into(picture)

            btnReturn.setOnClickListener{
                parentFragmentManager.popBackStack()
            }
        }

    }
    fun call(sTelefono: String) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$sTelefono"))
        startActivity(intent)
    }

    fun sendEmail(sEmail: String) {
        val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$sEmail"))
        startActivity(intent)
    }
}