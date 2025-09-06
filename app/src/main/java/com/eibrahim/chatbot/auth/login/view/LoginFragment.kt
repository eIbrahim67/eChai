package com.eibrahim.chatbot.auth.login.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.eibrahim.chatbot.R
import com.eibrahim.chatbot.auth.AuthPreferences
import com.eibrahim.chatbot.auth.login.viewModel.LoginState
import com.eibrahim.chatbot.auth.login.viewModel.LoginViewModel
import com.eibrahim.chatbot.main.MainActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()
    private lateinit var authPreferences: AuthPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_login, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authPreferences = AuthPreferences(requireContext())

        val emailLayout = view.findViewById<TextInputLayout>(R.id.email_sign_in)
        val passLayout = view.findViewById<TextInputLayout>(R.id.pass_sign_in)
        val emailInput = view.findViewById<TextInputEditText>(R.id.emailLogin)
        val passInput = view.findViewById<TextInputEditText>(R.id.passLogin)

        val loginButton = view.findViewById<MaterialButton>(R.id.btn_signin)
        val progressBar = view.findViewById<View>(R.id.loadingLogin)

        val forgotPassword = view.findViewById<View>(R.id.tv_forget_password)
        val navigateSignup = view.findViewById<View>(R.id.btn_signup2)

        forgotPassword.setOnClickListener {
            findNavController().navigate(R.id.nav_forget_password)
        }

        navigateSignup.setOnClickListener {
            findNavController().navigate(R.id.nav_signup)
        }

        loginButton.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val password = passInput.text.toString()

            emailLayout.error = null
            passLayout.error = null

            var valid = true
            if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailLayout.error = getString(R.string.error_invalid_email)
                valid = false
            }

            if (password.isEmpty()) {
                passLayout.error = getString(R.string.error_empty_password)
                valid = false
            }

            if (valid) {
                viewModel.login(email, password, authPreferences)
            }
        }

        viewModel.loginState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is LoginState.Loading -> {
                    loginButton.isEnabled = false
                    progressBar.visibility = View.VISIBLE
                }
                is LoginState.Success -> {
                    loginButton.isEnabled = true
                    progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), "Login Successful!", Toast.LENGTH_SHORT).show()

                    startActivity(Intent(requireContext(), MainActivity::class.java))
                    requireActivity().finish()
                }
                is LoginState.Error -> {
                    loginButton.isEnabled = true
                    progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
                }
            }
        }

        arguments?.let { args ->
            args.getString("googleIdToken")?.let { idToken ->
                viewModel.loginWithGoogle(idToken, authPreferences)
            }
            args.getString("error")?.let { error ->
                Toast.makeText(requireContext(), "Google Sign-In failed: $error", Toast.LENGTH_LONG).show()
            }
            arguments = null
        }
    }
}
