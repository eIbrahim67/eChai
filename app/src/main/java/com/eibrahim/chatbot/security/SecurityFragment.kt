package com.eibrahim.chatbot.security

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.eibrahim.chatbot.R
import com.eibrahim.chatbot.databinding.FragmentSecurityBinding
import kotlinx.coroutines.launch

class SecurityFragment : Fragment() {

    companion object {
        fun newInstance() = SecurityFragment()
    }

    private val viewModel: SecurityViewModel by viewModels()

    private var _binding: FragmentSecurityBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecurityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadAboutUsContent()

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.securityBack.setOnClickListener {
            findNavController().popBackStack()
        }

        setupUI(view)
        observeViewModel()
    }

    private fun setupUI(view: View) {
        try {
            // Set up description TextView
            view.findViewById<TextView>(R.id.appSecurity)?.apply {
                // Initial placeholder text
                text = Html.fromHtml(
                    getString(R.string.about_us_placeholder),
                    Html.FROM_HTML_MODE_COMPACT
                )
            } ?: Log.e("AboutUS", "Description TextView not found")
        } catch (e: Exception) {
            Log.e("AboutUS", "Error setting up UI in AboutUsFragment")
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.aboutUsContent.collect { content ->
                    view?.findViewById<TextView>(R.id.appSecurity)?.apply {
                        text = Html.fromHtml(content, Html.FROM_HTML_MODE_COMPACT)
                    } ?: Log.e("AboutUS", "Description TextView not found during content update")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
