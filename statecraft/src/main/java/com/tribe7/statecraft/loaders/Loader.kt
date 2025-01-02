package com.tribe7.statecraft.loaders

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/*
Use cases:
 - API loading indicators (Circular or Linear)
 - Page or section placeholders (Skeleton or Shimmer)
 - Content loading feedback
 - Enhancing UX during data fetch or processing delays
*/

@Composable
fun Loader(
    type: LoaderType,
    modifier: Modifier = Modifier,
    size: Dp = 50.dp,
    height: Dp = 100.dp,
    color: Color = MaterialTheme.colorScheme.primary ,  //except shimmer
    shape: Shape = RoundedCornerShape(8.dp)
) {
    when (type) {
        LoaderType.CIRCULAR -> {
            CircularProgressIndicator(
                modifier = modifier.size(size),
                color = color
            )
        }
        LoaderType.LINEAR -> {
            LinearProgressIndicator(
                modifier = modifier
                    .fillMaxWidth()
                    .height(4.dp),
                color = color
            )
        }
        LoaderType.SKELETON -> {
            Box(
                modifier = modifier
                    .clip(shape)
                    .background(color ?: Color.Gray.copy(alpha = 0.2f))
                    .fillMaxWidth()
                    .height(height)
            )
        }
        LoaderType.SHIMMER -> {
            val shimmerColors = listOf(
                Color.LightGray.copy(alpha = 0.6f),
                Color.Gray.copy(alpha = 0.3f),
                Color.LightGray.copy(alpha = 0.6f)
            )

            val transition = rememberInfiniteTransition()
            val translateAnim = transition.animateFloat(
                initialValue = -200f,
                targetValue = 1000f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 1000, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                ), label = ""
            )

            val brush = Brush.linearGradient(
                colors = shimmerColors,
                start = Offset(x = translateAnim.value, y = 0f),
                end = Offset(x = translateAnim.value + 300f, y = 0f)
            )

            Box(
                modifier = modifier
                    .clip(shape)
                    .background(brush = brush)
                    .fillMaxWidth()
                    .height(height)
            )
        }
    }
}

enum class LoaderType {
    CIRCULAR,
    LINEAR,
    SKELETON,
    SHIMMER
}
