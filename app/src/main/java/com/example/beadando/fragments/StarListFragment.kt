package com.example.beadando.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.beadando.StargazingApp
import com.example.beadando.R
import com.example.beadando.adapter.StarAdapter
import com.example.beadando.databinding.FragmentStarListBinding
import com.example.beadando.viewModel.StarListViewModel
import com.example.beadando.viewModel.StarViewModelFactory


class StarListFragment : Fragment() {


    private val viewModel: StarListViewModel by activityViewModels {
        StarViewModelFactory(
            (activity?.application as StargazingApp).database.starDao()
        )
    }

    private var _binding: FragmentStarListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStarListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = StarAdapter {
            val action = StarListFragmentDirections.actionStarListFragmentToStarDetailFragment(it.id)
            this.findNavController().navigate(action)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
        binding.recyclerView.adapter = adapter

        viewModel.fullList.observe(this.viewLifecycleOwner) {
            stars -> stars.let {
                adapter.submitList(it)
        }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun backHome() {
        findNavController().navigate(R.id.action_starListFragment_to_startFragment)
    }
}