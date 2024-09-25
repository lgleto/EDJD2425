package ipca.examples.helloworld


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ipca.examples.helloworld.ui.theme.HelloWorldTheme
import ipca.examples.helloworld.ui.theme.Pink80

@Composable
fun Greeting( modifier: Modifier = Modifier) {

    var name by remember { mutableStateOf("") }
    var greet by remember { mutableStateOf("") }

    val buttonClick : ()->Unit = {greet = "Hello $name!"}

    var backButtonColor = ButtonDefaults.run { buttonColors(Pink80) }

    Column(modifier = modifier
        .padding(16.dp)
        .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            name,
            onValueChange = { name = it }
        )
        Row {
            Button(onClick = buttonClick,
                colors = backButtonColor,
                content = { Text(text = "Greet") }
            )
            Spacer(modifier = Modifier.padding(12.dp))
            Button(onClick = buttonClick,
                content = { Text(text = "Cumprimentar") }
            )
        }
        Text(
            text = greet
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HelloWorldTheme {
        Greeting()
    }
}