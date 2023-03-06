package com.example.test_compose

import android.app.Application
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class App: Application() {

    companion object {
        lateinit var chatFireBase: FirebaseDatabase
    }

    override fun onCreate() {
        super.onCreate()
        chatFireBase = Firebase.database
    }
}