package com.example.beadando.fragments

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.beadando.StargazingApp
import com.example.beadando.data.Star
import com.example.beadando.databinding.FragmentNewStarBinding
import com.example.beadando.viewModel.StarListViewModel
import com.example.beadando.viewModel.StarViewModelFactory

class NewStarFragment : Fragment() {

    private var _binding: FragmentNewStarBinding? = null
    private val binding get() = _binding!!

    private val viewModel: StarListViewModel by activityViewModels {
        StarViewModelFactory(
            (activity?.application as StargazingApp).database
                .starDao()
        )
    }

    lateinit var star: Star
    private val navigationArgs: StarDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewStarBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            binding.starName.text.toString(),
            binding.towerNumber.text.toString(),
        )
    }

    private fun addStar() {
        if (isEntryValid()) {
            viewModel.addStar(
                binding.starName.text.toString(),
                binding.towerNumber.text.toString(),
                binding.starComment.text.toString()
            )
            val action = NewStarFragmentDirections.actionNewStarFragmentToStarListFragment()
            findNavController().navigate(action)
        }
    }

    private fun updateStar() {
        if (isEntryValid()) {
            viewModel.updateStar(
                this.navigationArgs.starId,
                this.binding.starName.text.toString(),
                this.binding.towerNumber.text.toString(),
                this.binding.starComment.text.toString()
            )
            val action = NewStarFragmentDirections.actionNewStarFragmentToStarListFragment()
            findNavController().navigate(action)
        }
    }

    private fun bind(star: Star) {
        binding.apply {
            starName.setText(star.starName, TextView.BufferType.SPANNABLE)
            towerNumber.setText(star.watchTower.toString(), TextView.BufferType.SPANNABLE)
            saveButton.setOnClickListener { updateStar() }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = navigationArgs.starId
        if (id > 0) {
            viewModel.retrieveStar(id).observe(this.viewLifecycleOwner) { selectedStar ->
                star = selectedStar
                bind(star)
            }
        } else {
            binding.saveButton.setOnClickListener {
                addStar()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        val inputMethodManager =
            requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        _binding = null
    }

}