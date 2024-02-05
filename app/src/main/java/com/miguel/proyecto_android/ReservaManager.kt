package com.miguel.proyecto_android

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

object ReservaManager {
    private val reservas = mutableListOf<Reserva>()



    fun addReserva(reserva: Reserva){
        reservas.add(reserva)
    }
    fun deleteReserva(position: Int){
        reservas.removeAt(position)
    }
    fun modifyReserva(position: Int, reserva: Reserva){
        reservas[position] = reserva
    }
    fun findReserva(position: Int):Reserva{
        return reservas[position]
    }
    
    fun getReservas():List<Reserva>{
        return reservas
    }
    
    fun isReservasEmpty():Boolean{
        return reservas.isEmpty()
    }
    
    fun setReservas(reservas2: List<Reserva>){
       for(reserva in reservas2){
           Log.i("MANAGER_DATA", "reserva"+reserva)
           this.reservas.add(reserva)
       }
    }
    
}