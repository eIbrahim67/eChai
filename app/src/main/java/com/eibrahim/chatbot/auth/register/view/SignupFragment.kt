package com.eibrahim.chatbot.auth

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.eibrahim.chatbot.R
import com.eibrahim.chatbot.auth.register.viewModel.SignupViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class SignupFragment : Fragment() {

    private val viewModel: SignupViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_signup, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fullNameLayout = view.findViewById<TextInputLayout>(R.id.full_name_sign_up)
        val emailLayout = view.findViewById<TextInputLayout>(R.id.email_sign_up)
        val passwordLayout = view.findViewById<TextInputLayout>(R.id.pass_sign_up)
        val rePasswordLayout = view.findViewById<TextInputLayout>(R.id.re_pass_sign_up)

        val fullNameInput = view.findViewById<TextInputEditText>(R.id.nameSignUp)
        val emailInput = view.findViewById<TextInputEditText>(R.id.emailSignUp)
        val passwordInput = view.findViewById<TextInputEditText>(R.id.passLogin)
        val rePasswordInput = view.findViewById<TextInputEditText>(R.id.rePassLogin)

        val signUpButton = view.findViewById<MaterialButton>(R.id.btn_sign_up)

        // Navigate to Sign In screen
        view.findViewById<TextView>(R.id.btn_signup2).setOnClickListener {
            findNavController().navigate(R.id.nav_login)
        }

        signUpButton.setOnClickListener {
            val fullName = fullNameInput.text.toString().trim()
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString()
            val confirmPassword = rePasswordInput.text.toString()

            var valid = true
            fullNameLayout.error = null
            emailLayout.error = null
            passwordLayout.error = null
            rePasswordLayout.error = null

            if (fullName.isEmpty()) {
                fullNameLayout.error = getString(R.string.error_first_name_required)
                valid = false
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailLayout.error = getString(R.string.error_invalid_email)
                valid = false
            }

            if (password.length < 8) {
                passwordLayout.error = getString(R.string.error_password_short)
                valid = false
            }

            if (password != confirmPassword) {
                rePasswordLayout.error = getString(R.string.error_password_mismatch)
                valid = false
            }

            if (valid) {
                Toast.makeText(requireContext(), "Registering...", Toast.LENGTH_SHORT).show()
                // Call your ViewModel here for actual signup logic
                Log.d("SignupFragment", "Signup valid - Ready to proceed")
            } else {
                Log.d("SignupFragment", "Signup failed validation")
            }
        }
    }
}
