package com.eibrahim.chatbot.chatbot.presentation.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eibrahim.chatbot.chatbot.domain.model.ChatMessage
import com.eibrahim.chatbot.chatbot.domain.model.ChatPayload
import com.eibrahim.chatbot.chatbot.domain.model.ChatUiState
import com.eibrahim.chatbot.chatbot.domain.model.ChatbotMessage
import com.eibrahim.chatbot.chatbot.domain.model.ChatbotViewModelConst
import com.eibrahim.chatbot.chatbot.domain.usecase.GetChatResponseUseCase
import com.eibrahim.chatbot.core.response.ResponseEI
import com.eibrahim.chatbot.ocr.ApiOcrClient
import com.eibrahim.chatbot.vsr.ApiVsrClient
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

/**
 * ViewModel for managing chatbot interactions, including conversation history,
 * property search, image OCR, and audio transcription.
 */
class ChatbotViewModel(
    private val getChatResponseUseCase: GetChatResponseUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData<ChatUiState>()
    val uiState: LiveData<ChatUiState> = _uiState

    private val conversationHistory = mutableListOf<ChatMessage>()
    private val conversationChatbot = mutableListOf<ChatbotMessage>()
    private val gson = Gson()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e(
            ChatbotViewModelConst.TAG,
            "Unhandled Coroutine Exception: ${exception.localizedMessage}",
            exception
        )
        updateUiState { copy(errorMessage = "Unexpected error: ${exception.localizedMessage}") }
    }


    init {
        _uiState.value = ChatUiState()
    }

    /**
     * Initiates a chat session with a user message.
     */
    fun startChat(userMessage: String) {
        if (userMessage.isBlank()) return
        addUserMessage(userMessage)
        ensureSystemPrompt()
        val payload = buildChatPayload()
        fetchChatResponse(payload)
    }

    /**
     * Processes an uploaded image for OCR and adds the extracted text as a user message.
     */
    fun processImage(file: File) {
        viewModelScope.launch(coroutineExceptionHandler) {
            try {
                val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                val filePart = MultipartBody.Part.createFormData("file", file.name, requestFile)
                val response = ApiOcrClient.ocrApiService.performOcr(filePart, language = "eng")

                val extractedText = if (response.isSuccessful) {
                    response.body()?.let { ocrResponse ->
                        if (ocrResponse.status == "success" && ocrResponse.extracted_text != null) {
                            ocrResponse.extracted_text
                        } else {
                            ocrResponse.error ?: "Unknown OCR error"
                        }
                    } ?: "API error: No response body"
                } else {
                    "API error: ${response.message()}"
                }

                updateUiState {
                    copy(
                        errorMessage = if (extractedText.contains(
                                "error",
                                true
                            )
                        ) extractedText else null
                    )
                }
                if (!extractedText.contains("error", true)) {
                    startChat(extractedText)
                }
            } catch (e: Exception) {
                Log.e(ChatbotViewModelConst.TAG, "Image processing failed", e)
                updateUiState { copy(errorMessage = "Image processing failed: ${e.message}") }
            } finally {
                file.delete()
            }
        }
    }

    /**
     * Processes an audio file for transcription and adds the transcribed text as a user message.
     */
    fun processAudio(file: File, maxRetries: Int = 3) {
        viewModelScope.launch(coroutineExceptionHandler) {
            withContext(Dispatchers.IO) {

                if (!file.exists()) {
                    withContext(Dispatchers.Main) {
                        updateUiState { copy(errorMessage = "Audio file not found: ${file.absolutePath}") }
                    }
                    return@withContext
                }

                repeat(maxRetries) { attempt ->
                    try {
                        val requestFile = file.asRequestBody("audio/*".toMediaTypeOrNull())
                        val filePart =
                            MultipartBody.Part.createFormData("file", file.name, requestFile)
                        val response = ApiVsrClient.vsrApiService.transcribeAudio(filePart)

                        val transcribedText = if (response.isSuccessful) {
                            response.body()?.let { transcription ->
                                if (transcription.status == "success" && transcription.transcribed_text != null) {
                                    transcription.transcribed_text.toString()
                                } else {
                                    transcription.error ?: "Unknown transcription error"
                                }
                            } ?: "API error: No response body"
                        } else {
                            "API error: ${response.message()}"
                        }

                        withContext(Dispatchers.Main) {
                            updateUiState {
                                copy(
                                    errorMessage = if (transcribedText.contains(
                                            "error",
                                            true
                                        )
                                    ) transcribedText else null
                                )
                            }
                            if (!transcribedText.contains("error", true)) {
                                startChat(transcribedText)
                            }
                        }
                        Log.d(
                            "ChatbotViewModel",
                            "Attempting transcription #${attempt + 1} on file: ${file.absolutePath}"
                        )
                        return@withContext
                    } catch (e: Exception) {
                        Log.e(ChatbotViewModelConst.TAG, "Transcription attempt $attempt failed", e)
                        if (attempt == maxRetries - 1) {
                            withContext(Dispatchers.Main) {
                                updateUiState { copy(errorMessage = "Transcription failed after $maxRetries attempts: ${e.message}") }
                                Log.e(
                                    ChatbotViewModelConst.TAG,
                                    "Transcription failed after $maxRetries attempts: ${e.message}"
                                )
                            }
                        }
                        delay(1000L * (attempt + 1))
                    }
                }
            }
            file.delete()
        }
    }


    /**
     * Toggles the recording state.
     */
    fun setRecordingState(isRecording: Boolean) {
        updateUiState { copy(isRecording = isRecording) }
    }

    /**
     * Updates the send button visibility based on input text.
     */
    fun updateSendButtonVisibility(text: String) {
        updateUiState { copy(isSendButtonVisible = text.isNotBlank()) }
    }

    /**
     * Adds a user message to the conversation history.
     */
    private fun addUserMessage(message: String) {
        conversationHistory.add(ChatMessage(content = message, role = "user", isFromUser = true))
        conversationChatbot.add(ChatbotMessage(content = message, role = "user", isFromUser = true))
        updateUiState { copy(messages = conversationHistory.toList()) }
    }

    /**
     * Ensures the system prompt is present at the start of the conversation.
     */
    private fun ensureSystemPrompt() {
        if (conversationHistory.none { it.role == "system" }) {
            conversationHistory.add(
                0,
                ChatMessage(role = "system", content = ChatbotViewModelConst.SYSTEM_PROMPT)
            )
            updateUiState { copy(messages = conversationHistory.toList()) }
        }
        if (conversationChatbot.none { it.role == "system" }) {
            conversationChatbot.add(
                0,
                ChatbotMessage(role = "system", content = ChatbotViewModelConst.SYSTEM_PROMPT)
            )
        }
    }

    /**
     * Builds the chat payload with conversation history and function definitions.
     */
    private fun buildChatPayload(): String {
        val payload = ChatPayload(
            messages = conversationChatbot
        )
        return gson.toJson(payload)
    }

    fun cleanResponse(text: String): String {
        return text
            .replace(Regex("^`{1,3}[a-zA-Z]*\\n?"), "") // Remove ```json or similar at start
            .replace(Regex("`{1,3}$"), "")              // Remove ``` at end
            .trim()
    }

    /**
     * Fetches the chat response and updates the UI state.
     */
    private fun fetchChatResponse(jsonPayload: String) {
        viewModelScope.launch(coroutineExceptionHandler) {
            getChatResponseUseCase.execute(jsonPayload).collect { response ->
                when (response) {
                    is ResponseEI.Loading -> updateUiState { copy(errorMessage = null) }
//                        handleSuccessResponse(response.data)
                    is ResponseEI.Success -> {

                        conversationHistory.add(
                            ChatMessage(
                                content = response.data.content
                            )
                        )
                        conversationChatbot.add(
                            ChatbotMessage(
                                content = response.data.content
                            )
                        )

                        updateUiState {
                        copy(
                            messages = conversationHistory.toList(),
                            errorMessage = null
                        )
                    }

                    }

                    is ResponseEI.Failure -> {
                        Log.e(
                            ChatbotViewModelConst.TAG,
                            "Chat response failed: ${response.reason.toString()}"
                        )
                        updateUiState { copy(errorMessage = response.reason.toString()) }
                    }
                }
            }
        }
    }

    /**
     * Handles a successful chat response by adding the bot's message to the history.
     */
//    private fun handleSuccessResponse(chatResponse: ChatMessage) {
//        if (chatResponse.content.isNullOrBlank()) {
//
//            Log.e(ChatbotViewModelConst.TAG, "Chat response content is null or blank")
//            updateUiState { copy(errorMessage = "Invalid response: Content is empty") }
//            return
//        }
//
//        runCatching {
//            // Parse the content as a JSON object to extract the message
//            val parsedResponse = gson.fromJson(chatResponse.content, ChatResponse::class.java)
//            parsedResponse
//        }.fold(
//            onSuccess = { parsedResponse ->
//                if (parsedResponse?.message != null) {
//                    conversationHistory.add(
//                        ChatMessage(
//                            content = parsedResponse.message
//                        )
//                    )
//                    conversationChatbot.add(
//                        ChatbotMessage(
//                            content = parsedResponse.message
//                        )
//                    )
//                    updateUiState {
//                        copy(
//                            messages = conversationHistory.toList(),
//                            errorMessage = null
//                        )
//                    }
//                } else {
//                    Log.e(ChatbotViewModelConst.TAG, "Parsed response or message is null")
//                    updateUiState { copy(errorMessage = "Invalid response: Message is missing") }
//                }
//            },
//            onFailure = { exception ->
//
//                Log.e(ChatbotViewModelConst.TAG, "Error parsing chat response", exception)
//                updateUiState { copy(errorMessage = "Error parsing response: ${exception.message}") }
//            }
//        )
//    }

    /**
     * Updates the UI state with the provided transformation.
     */
    private fun updateUiState(transform: ChatUiState.() -> ChatUiState) {
        // apply the transform
        val newState = _uiState.value?.run(transform) ?: ChatUiState()
        // but override messages to strip out system prompts
        _uiState.value = newState.copy(messages = getDisplayMessages())
    }

    private fun getDisplayMessages(): List<ChatMessage> =
        conversationHistory.filter { it.role != "system" }

}