package com.tribe7.statecraft.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp


@Composable
fun InputField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Input",
    isError: Boolean = false,
    isErrorColor: Color = Color.Red,
    isFocusedColor: Color = MaterialTheme.colorScheme.primary,
    errorMessage: String = "Invalid input",
    keyboardType: KeyboardType = KeyboardType.Text,
    maxLines: Int = 1,
    shape: Shape = RoundedCornerShape(8.dp)
) {

    var isFocused by remember { mutableStateOf(false) }
    val borderColor = when {
        isError -> isErrorColor
        isFocused -> isFocusedColor
        else -> Color.Gray
    }

    Column(modifier = modifier) {
        OutlinedTextField(
            value = value,
            placeholder = { Text("Enter text") },
            onValueChange = {
                onValueChange(it)
            },
            label = { Text(label) },
            isError = isError,
            singleLine = maxLines == 1,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState -> isFocused = focusState.isFocused },
            shape = shape
        )
        if (isError) {
            Text(
                text = errorMessage,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 8.dp, top = 4.dp)
            )
        }
    }
}