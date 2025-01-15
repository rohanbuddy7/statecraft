package com.tribe7.statecraft.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/*
Use cases:
- Empty or retry screens
- Error messages
- No network or permissions screens
- Maintenance or "Coming Soon" notices
- Generic placeholders for dynamic content
*/

@Composable
fun EmptyScreen(
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    iconSize: Dp = 80.dp,
    iconTint: Color = Color.Gray,
    title: String = "",
    titleStyle: TextStyle = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black),
    message: String = "",
    messageStyle: TextStyle = TextStyle(fontSize = 16.sp, color = Color.Gray),
    buttonText: String? = null,
    onButtonClick: (() -> Unit)? = null
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        icon?.let {
            Icon(
                imageVector = it,
                contentDescription = "",
                modifier = Modifier.size(iconSize),
                tint = iconTint
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        if (title.isNotEmpty()) {
            Text(
                text = title,
                style = titleStyle
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        if (message.isNotEmpty()) {
            Text(
                text = message,
                style = messageStyle,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Button
        if (!buttonText.isNullOrEmpty() && onButtonClick != null) {
            Button(onClick = onButtonClick) {
                Text(text = buttonText)
            }
        }
    }
}
