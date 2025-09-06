package com.eibrahim.chatbot.auth.verify.viewModel

data class VerifyRequest(
    val email: String,
    val otp: String
)