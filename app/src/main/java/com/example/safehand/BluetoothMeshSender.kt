package com.example.safehand

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import javax.inject.Inject

class BluetoothMeshSender @Inject constructor(
    private val locationProvider : com.example.safehand.LocationProvider,
    private val meshManager : MeshManager,
    private val encryptionUtils : EncryptionUtils
) {


}

class LocationProvider @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val fusedLocationClient =
        LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission") // make sure you request permissions before!
    fun getLastKnownLocation(onResult: (String?) -> Unit) {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    onResult("${location.latitude},${location.longitude}")
                } else {
                    onResult(null) // no location available
                }
            }
            .addOnFailureListener {
                onResult(null)
            }
    }
}



class EncryptionUtils @Inject constructor() {

    private val secretKeySpec: SecretKeySpec

    init {
        // 16-byte key for AES (must be kept safe!)
        val key = "mySuperSecretKey".toByteArray().copyOf(16)
        secretKeySpec = SecretKeySpec(key, "AES")
    }

    fun encrypt(message: String): ByteArray {
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec)
        return cipher.doFinal(message.toByteArray())
    }

    fun decrypt(encrypted: ByteArray): String {
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec)
        return String(cipher.doFinal(encrypted))
    }
}



class MeshManager @Inject constructor() {
    fun send(data: ByteArray) {
        // TODO: Use Nordic Mesh SDK or custom Bluetooth logic
        println("📡 Mesh message sent: ${data.size} bytes")
    }
}
