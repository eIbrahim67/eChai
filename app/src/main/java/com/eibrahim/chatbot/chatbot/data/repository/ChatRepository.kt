package com.eibrahim.chatbot.chatbot.data.repository

import com.eibrahim.chatbot.chatbot.domain.model.ChatMessage
import com.eibrahim.chatbot.core.response.ResponseEI
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    suspend fun getChatResponse(jsonPayload: String): Flow<ResponseEI<ChatMessage>>
}