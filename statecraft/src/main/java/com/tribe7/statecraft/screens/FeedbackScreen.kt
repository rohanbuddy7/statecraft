package com.tribe7.statecraft.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import com.tribe7.statecraft.R

enum class FeedbackState {
    SUCCESS,
    ERROR,
    CUSTOM
}

@Composable
fun FeedbackScreen(
    state: FeedbackState,
    customLottie: Int? = null,
    isPlaying: Boolean = true,
    iterations: Int =  LottieConstants.IterateForever,
    backgroundColor: Color = Color.White,
    title: String = "Notification",
    message: String = "Your action was successful.",
    titleStyle: TextStyle = TextStyle.Default,
    messageStyle: TextStyle = TextStyle.Default,
    buttonText: String = "OK",
    onClick: () -> Unit
) {
    var isDialogVisible by remember { mutableStateOf(true) }

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(getLottieAnimationForState(state, customLottie)))
    val progress by animateLottieCompositionAsState(
        composition,
        isPlaying = isPlaying,
        iterations = iterations
    )

    if (isDialogVisible) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(16.dp)
        ) {
            LottieAnimation(
                composition = composition,
                progress = progress,
                modifier = Modifier.size(90.dp)
            )
            Spacer(modifier = Modifier.height(0.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(30.dp))
            Button(
                content = {
                    Text(buttonText)
                },
                onClick = {
                    onClick()
                }
            )
        }
    }
}

@Composable
fun getLottieAnimationForState(state: FeedbackState, customLottie: Int?): Int {
    return when (state) {
        FeedbackState.SUCCESS -> R.raw.suck
        FeedbackState.ERROR -> R.raw.err
        FeedbackState.CUSTOM -> customLottie ?: 0
    }
}



