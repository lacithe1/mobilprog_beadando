package com.example.beadando.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.beadando.StargazingApp
import com.example.beadando.R
import com.example.beadando.data.Star
import com.example.beadando.databinding.FragmentStarDetailBinding
import com.example.beadando.viewModel.StarListViewModel
import com.example.beadando.viewModel.StarViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class StarDetailFragment : Fragment() {

    private val navigationArgs: StarDetailFragmentArgs by navArgs()
    lateinit var star: Star

    private val viewModel: StarListViewModel by activityViewModels{
        StarViewModelFactory(
            (activity?.application as StargazingApp).database.starDao()
        )
    }

    private var _binding: FragmentStarDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStarDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun bind(star: Star) {
        binding.apply {
            starName.text = star.starName
            towerNumber.text = star.watchTower.toString()
            starComment.text = star.starComment
            deleteStar.setOnClickListener { showConfirmationDialog() }
            editStar.setOnClickListener { editStar() }
        }
    }

    private fun showConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage("Are you sure you want to delete it?")
            .setCancelable(false)
            .setNegativeButton("No") {_, _ -> }
            .setPositiveButton("Yes") {_, _ ->
                deleteStar()
            }
            .show()
    }

    private fun editStar() {
        val action = StarDetailFragmentDirections.actionStarDetailFragmentToNewStarFragment(
            getString(R.string.edit_star),
            star.id
        )
        this.findNavController().navigate(action)
    }

    private fun deleteStar() {
        viewModel.deleteStar(star)
        findNavController().navigateUp()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.starId
        viewModel.retrieveStar(id).observe(this.viewLifecycleOwner) {
            selectStar -> star = selectStar
            bind(star)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}