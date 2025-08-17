package com.example.safehand

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SafeHandViewModel @Inject constructor(

    private val connectivityChecker: ConnectivityChecker,
    private val internetSmsSender: InternetSmsSender,
    private val bluetoothMeshSender: BluetoothMeshSender
) : ViewModel() {



}