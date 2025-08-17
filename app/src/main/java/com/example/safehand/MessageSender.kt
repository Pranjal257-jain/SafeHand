package com.example.safehand

import android.content.Context
import android.telephony.SmsManager
import android.widget.Toast
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface MessageSender {
    suspend fun send()
}

