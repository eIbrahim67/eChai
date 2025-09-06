package com.eibrahim.chatbot.LLMModel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.eibrahim.chatbot.R
import com.eibrahim.chatbot.databinding.FragmentLLMModelBinding

class LLMModelFragment : Fragment() {

    private var _binding: FragmentLLMModelBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLLMModelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Back Button Listener
        binding.llmModelBack.setOnClickListener {
            findNavController().popBackStack()
        }

        val models = listOf(
            LlmModel("Llama 3.2", "Fast and balanced", R.drawable.ic_model),
            LlmModel("Deepseek", "Deep search optimized", R.drawable.ic_model),
            LlmModel("Llama 3.1", "Stable release", R.drawable.ic_model),
            LlmModel("Gemini", "Multimodal intelligence", R.drawable.ic_model)
        )

        val adapter = LlmModelAdapter(models) { selectedModel ->
            Toast.makeText(requireContext(), "Selected: ${selectedModel.name}", Toast.LENGTH_SHORT).show()
        }

        binding.modelsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.modelsRecyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
