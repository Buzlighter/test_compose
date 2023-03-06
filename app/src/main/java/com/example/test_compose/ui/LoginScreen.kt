package com.example.test_compose.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Preview
@Composable
fun LoginScreen(authWithGoogle: ()-> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var account = rememberSaveable { "" }
        var pass = rememberSaveable { "" }
        TextField(
            value = account,
            label = { Text(text = "login") },
            onValueChange = {
                account = it
            })

        Spacer(modifier = Modifier.padding(5.dp))

        TextField(
            value = pass,
            label = { Text(text = "password") },
            onValueChange = {
                pass = it
            })

        Spacer(modifier = Modifier.padding(5.dp))

        Button(onClick = {
            authWithGoogle.invoke()
        }) {
            Text(text = "Авторизация")
        }
    }

}