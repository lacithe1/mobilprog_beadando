package com.example.beadando.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.beadando.R
import com.example.beadando.databinding.FragmentStartBinding

class StartFragment : Fragment() {

    private var binding: FragmentStartBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentStartBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.startFragment = this
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    fun newStar() {
        findNavController().navigate(R.id.action_startFragment_to_newStarFragment)
    }

    fun starList() {
        findNavController().navigate(R.id.action_startFragment_to_starListFragment)
    }

    fun starCamera() {
        findNavController().navigate(R.id.action_startFragment_to_cameraFragment)
    }

    fun randomMarsPhotos() {
        findNavController().navigate(R.id.action_startFragment_to_overviewFragment2)
    }
}