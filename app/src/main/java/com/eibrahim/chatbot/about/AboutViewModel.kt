package com.eibrahim.chatbot.about

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AboutViewModel : ViewModel() {

    private val _aboutUsContent = MutableStateFlow(
        """
            <b>eChai</b> is your <font color='#0077FF'>all-in-one AI chat companion</font>, offering seamless access to multiple advanced AI models in a single, beautifully designed application.<br><br>

            Chat effortlessly with <b>specialized AI assistants</b> tailored for coding, writing, language learning, and everyday productivity. Whether you prefer to <b>type, speak, or send images</b>, eChai makes interaction simple and intuitive.<br><br>

            Enjoy <font color='#0077FF'><b>voice-to-text</b></font>, <font color='#0077FF'><b>AI image understanding</b></font>, and <font color='#0077FF'><b>customizable AI personas</b></font> that match your style and goals. eChai also adapts to your preferences, learning from your chats to deliver <b>smarter, more personalized conversations</b> over time.<br><br>

            From <b>students and developers</b> to <b>creatives and casual users</b>, eChai is designed for everyone—empowering you to learn, create, and connect through the power of AI.<br><br>

            <b>Simple, smart, and always evolving—this is the future of AI chat.</b>
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
                Log.e("AboutUS-ViewModel", "Error loading AboutUs content")
                // Fallback to default content or error message if needed
            }
        }
    }

}