package com.eibrahim.chatbot.ocr

data class OcrResponse(
    val status: String,
    val extracted_text: String?,
    val error: String?
)