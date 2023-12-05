package com.miguel.proyecto_android

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.miguel.proyecto_android.databinding.FragmentClientListBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ClientListFragment : Fragment(R.layout.fragment_client_list) {
    private lateinit var binding: FragmentClientListBinding

    private val adapter = ClientAdapter(this@ClientListFragment){ client ->
        findNavController().navigate(R.id.action_clientListFragment_to_detailFragment,
            bundleOf("client" to client)
        ) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding = FragmentClientListBinding.bind(view).apply {
            if (adapter.itemCount == 0){
                loadItems()
            }
            recyclerView.adapter = adapter

            addClient.setOnClickListener{
                findNavController().navigate(R.id.action_clientListFragment_to_modifyFragment,
                    bundleOf(ModifyFragment.POS to -1))
            }
        }

    }

    private fun loadItems() {
        GlobalScope.launch(Dispatchers.Main) {
            binding.progressBar.visibility =View.VISIBLE
            val clients = withContext(Dispatchers.IO){ ClientManager.getClients() }
            adapter.clients = clients
            adapter.notifyDataSetChanged()
            binding.progressBar.visibility = View.GONE
        }
    }

    fun onBorrar(posicion: Int) {
        ClientManager.deleteCLient(posicion)
        binding.recyclerView.adapter?.notifyItemRemoved(posicion)
        loadItems()

    }

    fun onModificar(posicion: Int) {
        findNavController().navigate(
            R.id.action_clientListFragment_to_modifyFragment,
            bundleOf(ModifyFragment.POS to posicion)
        )

    }

}