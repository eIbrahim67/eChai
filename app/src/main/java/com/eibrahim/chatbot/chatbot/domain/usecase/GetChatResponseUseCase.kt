package com.eibrahim.chatbot.chatbot.domain.usecase

import com.eibrahim.chatbot.chatbot.data.repository.ChatRepository
import com.eibrahim.chatbot.chatbot.domain.model.ChatMessage
import com.eibrahim.chatbot.core.response.ResponseEI
import kotlinx.coroutines.flow.Flow

class GetChatResponseUseCase(private val repository: ChatRepository) {
    suspend fun execute(jsonPayload: String): Flow<ResponseEI<ChatMessage>> {
        // You could add validation or other business rules here.
        return repository.getChatResponse(jsonPayload)
    }
}