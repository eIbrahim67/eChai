package com.eibrahim.chatbot.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.eibrahim.chatbot.R
import com.eibrahim.chatbot.auth.AuthActivity
import com.eibrahim.chatbot.auth.AuthPreferences
import com.eibrahim.chatbot.databinding.FragmentSettingsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingsFragment : Fragment() {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    private val viewModel: SettingsViewModel by viewModels()

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Back button listener
        binding.settingsBack.setOnClickListener {
            findNavController().popBackStack()
        }

        val settingsList = listOf(
            SettingItem(R.drawable.ic_profile, "Ibrahim", null),
            SettingItem(R.drawable.ic_email, "ibrahim.mohamed.ibrahim@gmail.com", null),
            SettingItem(R.drawable.ic_upgrade, "Upgrade to Plus", R.id.nav_upgrade),
            SettingItem(R.drawable.ic_instructions, "Custom Instructions", R.id.nav_instructions),
            SettingItem(R.drawable.ic_data_control, "Data Control", -1),
            SettingItem(R.drawable.ic_security, "Security", R.id.nav_security),
            SettingItem(R.drawable.ic_about, "About", R.id.nav_about),
        )

        val adapter = SettingsAdapter(
            settingsList,
            { destination -> goToPage(destination) },
            { showBottomSheet() }
        )
        binding.rvSettings.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSettings.adapter = adapter

        val dialog = SignoutDialogFragment()
        // 🟢 Click Listeners with Toasts
        binding.signOutBtn.setOnClickListener {
            dialog.show(parentFragmentManager, "DataControlDialog")
        }

    }




    private fun goToPage(destination: Int) {
        findNavController().navigate(destination)
    }

    private fun showBottomSheet() {
        val dialog = DataControlDialogFragment()
        dialog.show(requireActivity().supportFragmentManager, "DataControlDialog")

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
