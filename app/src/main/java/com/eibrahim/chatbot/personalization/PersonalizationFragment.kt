package com.eibrahim.chatbot.personalization

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.eibrahim.chatbot.databinding.FragmentPersonalizationBinding

class PersonalizationFragment : Fragment() {

    companion object {
        fun newInstance() = PersonalizationFragment()
    }

    private val viewModel: PersonalizationViewModel by viewModels()

    private var _binding: FragmentPersonalizationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPersonalizationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.personalizationBack.setOnClickListener {
            findNavController().popBackStack()
        }

        // TODO: Use the ViewModel
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
