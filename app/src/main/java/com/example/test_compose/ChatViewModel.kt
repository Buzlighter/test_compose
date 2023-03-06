package com.example.test_compose

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test_compose.data.Message
import kotlinx.coroutines.launch

class ChatViewModel: ViewModel() {
    val _messageList = mutableStateListOf<Message>()
    val messageList: List<Message> = _messageList

    val currentMessage = MutableLiveData<String>()


    fun sendMessage(input: String) {
        currentMessage.value = input
        Log.w("MY_VM", currentMessage.value ?: "empty string")
    }

    fun updateChat(newList: ArrayList<Message>) {
        viewModelScope.launch {
            _messageList.addAll(newList)
        }
    }
}