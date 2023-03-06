package com.example.test_compose.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.test_compose.ChatViewModel
import com.example.test_compose.R
import com.example.test_compose.data.Message


// Переписать на constraint

@Preview
@Composable
fun ChatScreen(chatViewModel: ChatViewModel) {
    val messages = chatViewModel.messageList

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (lazy_list, input_field) = createRefs()
        if (messages.isNotEmpty()) {
            LazyColumn(modifier = Modifier
                .fillMaxHeight()
                .padding(top = 60.dp, start = 10.dp, end = 10.dp, bottom = 20.dp)
                .constrainAs(lazy_list) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(input_field.top, margin = 30.dp)
                }
            ) {
                items(items = messages) {msg ->
                    ChatViewItem(msg = msg)
                    Spacer(modifier = Modifier.padding(5.dp))
                }
            }
        }

        Box(
            modifier = Modifier
                .padding(10.dp)
                .constrainAs(input_field) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
            contentAlignment = Alignment.BottomCenter
        ) {
            InputField(chatViewModel)
        }
    }
}

@Composable
fun InputField(chatViewModel: ChatViewModel) {
    var input by rememberSaveable { mutableStateOf("") }
    Row(modifier = Modifier.fillMaxWidth()) {
        TextField(modifier = Modifier
            .requiredWidth(300.dp)
            .requiredHeight(50.dp),
            value = input,
            onValueChange = { input= it },
            label = { Text("Текст") })
        
        Spacer(modifier = Modifier.padding(start = 5.dp))

        Box (modifier = Modifier.background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    MaterialTheme.colors.primarySurface,
                    MaterialTheme.colors.primaryVariant
                )
            ), shape = RoundedCornerShape(5.dp))
        ) {
            Image(painter = painterResource(id = R.drawable.ic_chat_send),
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        chatViewModel.sendMessage(input)
                        Log.i("CHAT", input)
                        input = ""
                    })
        }
    }
}

@Composable
fun ChatViewItem(msg: Message) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .border(
            width = 2.dp,
            color = Color.Black,
            RoundedCornerShape(20.dp)
        )
        .padding(10.dp)
        .wrapContentHeight()
    ) {
        Column {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "profile icon",
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
            )

            Text(modifier = Modifier
                .padding(top = 5.dp), text = msg.userName)
        }
        
        Spacer(modifier = Modifier.width(10.dp))
        
        Text( text = msg.content)

        Spacer(modifier = Modifier.width(10.dp))
    }
}