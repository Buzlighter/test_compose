package com.example.test_compose

import android.content.Intent
import androidx.activity.ComponentActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import com.example.test_compose.ui.LoginScreen
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    lateinit var launcher: ActivityResultLauncher<Intent>

    private lateinit var signInClient: GoogleSignInClient
    private lateinit var signInRequest: BeginSignInRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginScreen(this@LoginActivity::signWithGoogle)
        }

        auth = Firebase.auth
        setGoogleClient()

        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            try {
                val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
                val account = task.result
                when {
                    account != null -> {
                        // Got an ID token from Google. Use it to authenticate
                        // with Firebase.
                        authWithGoogle(account.idToken!!)

                        val intent = Intent(this, ChatActivity::class.java).apply {
                            putExtras(bundleOf("name" to account.givenName))
                        }
                        startActivity(intent)
                        Log.w("MY_AUTH", "Got ID token")
                    }
                    else -> {
                        // Shouldn't happen.
                        Log.w("MY_AUTH", "No ID token!")
                    }
                }
            } catch (e: ApiException) {
                Log.w("MY_AUTH", "${e.status}")
            }
        }

    }

    private fun setGoogleClient() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        signInClient = GoogleSignIn.getClient(this , gso)
    }

    fun signWithGoogle() {
        launcher.launch(signInClient.signInIntent)
    }

    fun authWithGoogle(idToken: String) {
        val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(firebaseCredential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.w("MY_AUTH", "sign in done")
                } else {
                    Log.w("MY_AUTH", "sign in error")
                }

            }
    }
}