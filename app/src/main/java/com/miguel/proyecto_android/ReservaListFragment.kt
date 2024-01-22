package com.miguel.proyecto_android

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
                        import androidx.navigation.fragment.findNavController
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding = FragmentReservaListBinding.bind(view).apply {
            if (adapter.itemCount == 0){
                loadItems()
            }
            recyclerView.adapter = adapter

            addReserva.setOnClickListener{
                findNavController().navigate(R.id.action_reservaListFragment_to_modifyFragment,
                    bundleOf(ModifyFragment.POS to -1))
            }
        }

    }

    private fun loadItems() {
        GlobalScope.launch(Dispatchers.Main) {
            binding.progressBar.visibility =View.VISIBLE
            val reservas = withContext(Dispatchers.IO){ ReservaManager.getReservas() }
            adapter.reservas = reservas
            adapter.notifyDataSetChanged()
            binding.progressBar.visibility = View.GONE
        }
    }

    fun onBorrar(posicion: Int) {
        ReservaManager.deleteReserva(posicion)
        binding.recyclerView.adapter?.notifyItemRemoved(posicion)
        loadItems()

    }

    fun onModificar(posicion: Int) {
        findNavController().navigate(
            R.id.action_reservaListFragment_to_modifyFragment,
            bundleOf(ModifyFragment.POS to posicion)
        )

    }

}