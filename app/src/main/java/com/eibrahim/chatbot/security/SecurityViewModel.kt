package com.eibrahim.chatbot.security

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SecurityViewModel : ViewModel() {
    private val _aboutUsContent = MutableStateFlow(
        """
            <b>🔒 Privacy and Security You Can Trust</b><br><br>

            At <b>eChai</b>, your privacy is our top priority. We use <font color='#0077FF'><b>end-to-end encryption</b></font> to ensure that your conversations stay <b>private and secure</b>, with no unauthorized access.<br><br>

            Your data is <b>never sold or shared</b> with third parties. With features like <font color='#0077FF'><b>secure local storage</b></font>, <b>anonymous chat options</b>, and <font color='#0077FF'><b>on-device AI processing</b></font> (for select models), you are always in control.<br><br>

            We are committed to transparency by offering <b>clear privacy settings</b> and giving you full access to manage or delete your chat history anytime.<br><br>

            <b>With eChai, your information stays yours—safe, secure, and protected.</b>

        """.trimIndent()
    )
    val aboutUsContent: StateFlow<String> = _aboutUsContent

    fun loadAboutUsContent() {
        viewModelScope.launch {
            try {
                // Simulate fetching data (e.g., from a repository or remote source)
                // For now, the content is static as provided
                _aboutUsContent.value = aboutUsContent.value
            } catch (e: Exception) {
                Log.e("Security-ViewModel", "Error loading Security content")
                // Fallback to default content or error message if needed
            }
        }
    }
}