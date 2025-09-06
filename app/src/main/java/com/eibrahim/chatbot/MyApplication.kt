package com.eibrahim.chatbot

import android.app.Application
import com.eibrahim.chatbot.auth.api.RetrofitClient
import com.stripe.android.PaymentConfiguration

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        RetrofitClient.initAuthPreferences(this)

        PaymentConfiguration.init(
            applicationContext,
            "pk_test_51RXPxIRHB8EWOr5UtIiT6dVRn7j68SFzmO6kKiJpfLoh58o9kj5h3kb8QTpvnhaqLOVPB5ladvNBHHrQrR2Q9XdH00Q2gtWAQ5" // Replace with your real publishable key
        )

    }
}
