package com.example.test_compose.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}


@Composable
fun TEXTING(name: String) {
    Column {
        Text(text = "hi $name",
            style = MaterialTheme.typography.caption, fontSize = 20.sp)

        Text(text = "there")

        Button(onClick = { /*TODO*/ }) {

        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Greeting("Android")
}

@Preview
@Composable
fun TextingPreview() {
    Scaffold {
        it.calculateBottomPadding()
        TEXTING(name = "me")
    }
}

@Composable
fun InputText()  {
    var input  by rememberSaveable { mutableStateOf("") }
    TextField(value = input, onValueChange = { input = it},  label = { Text("input") })
}

@Preview
@Composable
fun InputTextPreview()  {
    InputText()
}

@Composable
fun ItemsList() {
    LazyColumn {
        items(listOf("A", "B", "C", "D", "E", "F")) {
            Text(text = "item $it")
        }
    }
}

@Preview
@Composable
fun ItemsListPreview() {
    ItemsList()
}

@Composable
fun Constraint() {
    ConstraintLayout {
        val (button, text) = createRefs()

        Button(
            onClick = { /* Do something */ },
            modifier = Modifier.constrainAs(button) {
                top.linkTo(parent.top, margin = 16.dp)
            }
        ) {
            Text("Button")
        }
        Text("Text", Modifier.constrainAs(text) {
            top.linkTo(button.bottom, margin = 16.dp)
        })
    }
}

@Preview
@Composable
fun ConstraintPreview() {
    Constraint()
}