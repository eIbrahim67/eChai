package com.eibrahim.chatbot.settings

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.eibrahim.chatbot.databinding.FragmentDataControlDialogBinding

class DataControlDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentDataControlDialogBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDataControlDialogBinding.inflate(inflater, container, false)

        // 🟢 Click Listeners with Toasts
        binding.clearChats.setOnClickListener {
            Toast.makeText(requireContext(), "Chat history cleared!", Toast.LENGTH_SHORT).show()
            dismiss()
        }

        binding.deleteAccount.setOnClickListener {
            Toast.makeText(requireContext(), "Account deleted!", Toast.LENGTH_SHORT).show()

        }


        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}