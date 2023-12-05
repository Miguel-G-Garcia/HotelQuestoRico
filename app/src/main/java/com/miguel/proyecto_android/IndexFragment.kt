package com.miguel.proyecto_android

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.miguel.proyecto_android.databinding.FragmentIndexBinding


class IndexFragment : Fragment(R.layout.fragment_index) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentIndexBinding.bind(view).apply{
            btnListar.setOnClickListener{
                findNavController().navigate(R.id.action_indexFragment_to_clientListFragment)
            }
        }
    }
}