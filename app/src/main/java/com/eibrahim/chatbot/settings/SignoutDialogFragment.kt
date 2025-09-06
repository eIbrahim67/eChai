package com.eibrahim.chatbot.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.eibrahim.chatbot.auth.AuthActivity
import com.eibrahim.chatbot.auth.AuthPreferences
import com.eibrahim.chatbot.databinding.FragmentSignoutDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignoutDialogFragment(

) : BottomSheetDialogFragment() {

    private var _binding: FragmentSignoutDialogBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSignoutDialogBinding.inflate(inflater, container, false)


        binding.btnSignOut.setOnClickListener {
            Toast.makeText(requireContext(), "Sign Out Successfully", Toast.LENGTH_SHORT).show()

            CoroutineScope(Dispatchers.IO).launch {
                try {

                    val authPreferences = AuthPreferences(requireContext())
                    authPreferences.clearToken()
                    startActivity(Intent(requireContext(), AuthActivity::class.java))
                    requireActivity().finish()


                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }.let {

                dismiss()

            }



        }

        return binding.root

    }


}