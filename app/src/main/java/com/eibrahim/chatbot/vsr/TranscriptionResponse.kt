package com.eibrahim.chatbot.vsr

data class TranscriptionResponse(
    val status: String,
    val transcribed_text: String?,
    val error: String?
)