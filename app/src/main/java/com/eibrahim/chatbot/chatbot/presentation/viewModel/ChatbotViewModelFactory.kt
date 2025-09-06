package com.eibrahim.chatbot.chatbot.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eibrahim.chatbot.chatbot.domain.usecase.GetChatResponseUseCase

class ChatbotViewModelFactory(

    private val getChatResponseUseCase: GetChatResponseUseCase,

) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(ChatbotViewModel::class.java))
            return ChatbotViewModel(getChatResponseUseCase) as T
        else
            throw IllegalArgumentException("Unknown view model")

    }

}