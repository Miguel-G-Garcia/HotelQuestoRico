package com.miguel.proyecto_android

object ReservaManager {

    private val reservas = mutableListOf<Reserva>(
        Reserva(
            "Reserva 1",
            "https://picsum.photos/300?random=1"
        ),Reserva(
            "Reserva 2",
            "https://picsum.photos/300?random=2"
        ),Reserva(
            "Reserva 3",
            "https://picsum.photos/300?random=3"
        ),Reserva(
            "Reserva 4",
            "https://picsum.photos/300?random=4"
        ),Reserva(
            "Reserva 5",
            "https://picsum.photos/300?random=5"
        ),Reserva(
            "Reserva 6",
            "https://picsum.photos/300?random=6"
        ),
    )



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

}