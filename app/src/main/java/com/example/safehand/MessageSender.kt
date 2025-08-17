package com.example.safehand

import android.content.Context
import android.telephony.SmsManager
import android.widget.Toast
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface MessageSender {
    suspend fun send()
}

class InternetSmsSender @Inject constructor(
    @ApplicationContext private val context: Context
    ): MessageSender {

    override suspend fun send() {
        if(ConnectivityUtils.hasInternetOrSMS(context)) {

        }else if(ConnectivityUtils.hasSimReady(context)) {

        }else{
            Toast.makeText(context,"No internet or SMS service",Toast.LENGTH_LONG).show()
        }
    }

    private fun sendViaInternet() {
        // TODO: Replace with actual HTTP logic (e.g. Retrofit)
        println("🌐 Sending SOS via Internet (placeholder)")
        Toast.makeText(context, "SOS sent via Internet", Toast.LENGTH_SHORT).show()
    }
    private fun sendViaSms() {
        val phoneNumber = "1234567890" // TODO: Replace with actual emergency contact
        val message = "🚨 SOS! I need help. Location: [lat,long]"

        try {
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(phoneNumber, null, message, null, null)
            Toast.makeText(context, "📩 SOS sent via SMS", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, "❌ Failed to send SMS: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}