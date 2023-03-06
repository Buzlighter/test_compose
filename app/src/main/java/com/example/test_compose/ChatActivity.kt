package com.example.test_compose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.test_compose.App.Companion.chatFireBase
import com.example.test_compose.data.Message
import com.example.test_compose.ui.ChatScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class ChatActivity : ComponentActivity() {

    val chatViewModel by viewModels<ChatViewModel>()
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatScreen(chatViewModel)
        }
        auth = Firebase.auth

        Log.w("CHAT", intent.getStringExtra("name")?: "null name")

        val firebaseChatRef = chatFireBase.getReference("chat")
//        val childRef = firebaseChatRef.child( intent.getStringExtra("name") ?: "unknown")
//
//        childRef.setValue("hello")


        chatViewModel.currentMessage.observe(this) { msg ->
            firebaseChatRef
                .child(firebaseChatRef.push().key ?: "not found")
                .setValue(Message(auth.currentUser?.displayName ?: "", msg))
        }


        firebaseChatRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val displayList = ArrayList<Message>()
                snapshot.children.forEach {
                    val msg = it.getValue(Message::class.java)
                    if (msg != null) {
                        displayList.add(msg)
                    }
                }
                Log.w("CHAT", "CHAT LIST FROM FB $displayList")
                chatViewModel.updateChat(displayList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("CHAT", "Failed to read value.", error.toException())
            }
        })

    }
}

