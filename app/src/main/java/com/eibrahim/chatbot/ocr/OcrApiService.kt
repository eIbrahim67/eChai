package com.eibrahim.chatbot.ocr

import com.eibrahim.chatbot.ocr.OcrResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query
import retrofit2.Response

interface OcrApiService {
    @Multipart
    @POST("/ocr")
    suspend fun performOcr(
        @Part file: MultipartBody.Part,
        @Query("language") language: String = "eng"
    ): Response<OcrResponse>
}