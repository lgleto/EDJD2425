package ipca.examples.calulator

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ipca.examples.calulator.ui.components.CalcButton
import ipca.examples.calulator.ui.theme.CalulatorTheme
import ipca.examples.calulator.ui.theme.Orange


@Composable
fun CalculatorScreen(modifier: Modifier = Modifier) {

    var displayText by remember { mutableStateOf("0") }
    var operand by remember { mutableStateOf(0.0) }
    var operator by remember { mutableStateOf("") }
    var userIsInTheMiddleOfIntroduction by remember {
        mutableStateOf(true)
    }

    fun getDisplay() : Double {
        return displayText.toDouble()
    }

    fun setDisplay (value : Double) {
        if (value % 1 == 0.0) {
            displayText = value.toInt().toString()
        }else {
            displayText = value.toString()
        }
    }

    val onNumPressed : (String) -> Unit = { num ->
        if (userIsInTheMiddleOfIntroduction) {
            if (displayText == "0") {
                if (num == ".") {
                    displayText = "0."
                } else {
                    displayText = num
                }
            } else {

                if (!displayText.contains('.') || num != ".") {
                    displayText += num
                }
            }
        }else {
            displayText = num
        }

        userIsInTheMiddleOfIntroduction = true
    }



    val onOperatorPressed : (String) -> Unit = { op ->
        if (operator.isNotEmpty()) {
            when(operator) {
                "+" -> operand += displayText.toDouble()
                "-" -> operand -= displayText.toDouble()
                "*" -> operand *= displayText.toDouble()
                "/" -> operand /= displayText.toDouble()
                "=" -> operator = ""
            }
            setDisplay(operand)
        }

        operand = getDisplay()
        operator = op

        userIsInTheMiddleOfIntroduction = false
    }


    Column(modifier = modifier
        .padding(16.dp)) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            textAlign = TextAlign.Right,
            text = displayText,
            style = MaterialTheme.typography.displayLarge)
        Row(modifier = Modifier
            .weight(1f)
        ) {
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "7",
                onClick = onNumPressed
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "8",
                onClick = onNumPressed
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "9",
                onClick = onNumPressed
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "+",
                isOperation = true,
                onClick = onOperatorPressed
            )
        }
        Row(modifier = Modifier
            .weight(1f)
        ) {
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "4",
                onClick = onNumPressed
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "5",
                onClick = onNumPressed
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "6",
                onClick = onNumPressed
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "-",
                isOperation = true,
                onClick = onOperatorPressed
            )
        }
        Row(modifier = Modifier
            .weight(1f)
        ) {
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "1",
                onClick = onNumPressed
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "2",
                onClick = onNumPressed
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "3",
                onClick = onNumPressed
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "/",
                isOperation = true,
                onClick = onOperatorPressed
            )
        }
        Row(modifier = Modifier
            .weight(1f)
        ) {
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "0",
                onClick = onNumPressed
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = ".",
                onClick = onNumPressed
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "=",
                isOperation = true,
                onClick = onOperatorPressed
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "*",
                isOperation = true,
                onClick = onOperatorPressed
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorScreenPreview() {
    CalulatorTheme {
        CalculatorScreen()
    }
}