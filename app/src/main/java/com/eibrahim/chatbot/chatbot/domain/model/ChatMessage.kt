package com.eibrahim.chatbot.chatbot.domain.model

data class ChatMessage(
    val content: String,
    val role: String = "assistant",
    val isFromUser: Boolean = false
)
