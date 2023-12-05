package com.miguel.proyecto_android

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.miguel.proyecto_android.databinding.ViewClientBinding

class ClientAdapter (
    val context: ClientListFragment,
    val listener: (Client) -> Unit)
    : RecyclerView.Adapter<ClientAdapter.ViewHolder>() {

    var clients = emptyList<Client>()

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
            val binding = ViewClientBinding.bind(view)
            fun bind(client: Client){
                with(binding) {
                    nombre.text = client.nombre
                    Glide.with(imageView)
                        .load(client.foto)
                        .into(imageView)
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_client, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = clients.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(clients[position])
        holder.itemView.setOnClickListener { listener(clients[position]) }
        holder.binding.btnBorrar.setOnClickListener { context.onBorrar(position)}
        holder.binding.btnModificar.setOnClickListener { context.onModificar(position) }
    }

}