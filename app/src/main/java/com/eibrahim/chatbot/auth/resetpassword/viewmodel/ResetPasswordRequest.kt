package com.eibrahim.chatbot.auth.resetpassword.viewmodel

data class ResetPasswordRequest(
    val email: String,
    val newPassword: String,
    val confirmPassword: String
)