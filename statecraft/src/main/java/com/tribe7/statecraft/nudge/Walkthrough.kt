package com.tribe7.statecraft.nudge

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@Composable
fun Walkthrough(
    items: List<WalkthroughItem>,
    modifier: Modifier = Modifier.fillMaxSize(),
    onFinish: () -> Unit
) {
    var currentStep by remember { mutableStateOf(0) }

    if (currentStep >= items.size) {
        onFinish()
        return
    }

    val currentItem = items[currentStep]

    Box(modifier = modifier) {
        val density = LocalDensity.current
        val configuration = LocalConfiguration.current

        // Screen dimensions in pixels
        val screenWidthPx = with(density) { (configuration.screenWidthDp.dp - 100.dp).toPx() }
        val screenHeightPx = with(density) { (configuration.screenHeightDp.dp - 100.dp).toPx() }

        // Chat box dimensions
        val chatBoxWidthPx = with(density) { 200.dp.toPx() }
        val chatBoxHeightPx = with(density) { 200.dp.toPx() }

        // Pointer offset (circle center)
        val pointerX = currentItem.itemCenter.x + 50
        val pointerY = currentItem.itemCenter.y + 50

        // Calculate position categories
        val isTop = pointerY < screenHeightPx / 3
        val isBottom = pointerY > (2 * screenHeightPx) / 3
        val isLeft = pointerX < screenWidthPx / 3
        val isRight = pointerX > (2 * screenWidthPx) / 3

        // Determine offsets based on position
        var xOffset: Float
        var yOffset: Float

        when {
            isTop && isLeft -> { // Top-left
                xOffset = pointerX + with(density) { 50.dp.toPx() }
                yOffset = pointerY + chatBoxHeightPx //+ with(density) { -50.dp.toPx() }
            }
            isTop && isRight -> { // Top-right
                xOffset = pointerX - chatBoxWidthPx - with(density) { 50.dp.toPx() }
                yOffset = pointerY + chatBoxHeightPx //+ with(density) { 50.dp.toPx() }
            }
            isBottom && isLeft -> { // Bottom-left
                xOffset = pointerX + with(density) { 50.dp.toPx() }
                yOffset = pointerY - chatBoxHeightPx - with(density) { 50.dp.toPx() }
            }
            isBottom && isRight -> { // Bottom-right
                xOffset = pointerX - chatBoxWidthPx - with(density) { 50.dp.toPx() }
                yOffset = pointerY - chatBoxHeightPx - with(density) { 50.dp.toPx() }
            }
            else -> { // Center or near-center
                xOffset = if (pointerX > screenWidthPx / 2) {
                    pointerX - chatBoxWidthPx - with(density) { 50.dp.toPx() }
                } else {
                    pointerX + with(density) { 50.dp.toPx() }
                }
                yOffset = if (pointerY > screenHeightPx / 2) {
                    pointerY - chatBoxHeightPx - with(density) { 50.dp.toPx() }
                } else {
                    pointerY + chatBoxHeightPx + with(density) { 50.dp.toPx() }
                }
            }
        }

        // Clamp offsets within screen bounds
        xOffset = xOffset.coerceIn(with(density) { 16.dp.toPx() }, screenWidthPx - chatBoxWidthPx - with(density) { 16.dp.toPx() })
        yOffset = yOffset.coerceIn(with(density) { 16.dp.toPx() }, screenHeightPx - chatBoxHeightPx - with(density) { 16.dp.toPx() })

        // Draw arrow (connecting to closest edge of chat box)
        Canvas(modifier = Modifier.fillMaxSize()) {
            val arrowTip = Offset(pointerX, pointerY)

            // Find the closest edge of the chat box
            val chatBoxLeft = xOffset
            val chatBoxRight = xOffset + chatBoxWidthPx
            val chatBoxTop = yOffset
            val chatBoxBottom = yOffset + chatBoxHeightPx

            val closestX = when {
                pointerX < chatBoxLeft -> chatBoxLeft + 50 // left of the box
                pointerX > chatBoxRight -> chatBoxRight // right of the box
                else -> pointerX // inside the box horizontally
            }

            val closestY = when {
                pointerY < chatBoxTop -> chatBoxTop // above the box
                pointerY > chatBoxBottom -> chatBoxBottom - 100 // below the box
                else -> pointerY // inside the box vertically
            }

            // Arrow base point (closest point on chatbox edge)
            val arrowBase = Offset(closestX, closestY)

            // Triangle base width
            val baseWidth = 40f

            // Calculate base points of the triangle
            val baseLeft = Offset(
                x = arrowBase.x - baseWidth / 2,
                y = arrowBase.y
            )
            val baseRight = Offset(
                x = arrowBase.x + baseWidth / 2,
                y = arrowBase.y
            )

            // Draw triangle from circle center to chat box
            drawPath(
                path = Path().apply {
                    moveTo(arrowTip.x, arrowTip.y) // Triangle tip
                    lineTo(baseLeft.x, baseLeft.y) // Left base point
                    lineTo(baseRight.x, baseRight.y) // Right base point
                    close() // Close the path to form a triangle
                },
                color = currentItem.cardColor,

            )
        }


        // Chat box UI
        Column(
            modifier = Modifier
                //.fillMaxSize()
                //.padding(16.dp)
                .offset(x = with(density) { xOffset.toDp() }, y = with(density) { yOffset.toDp() }),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .background(currentItem.cardColor, shape = RoundedCornerShape(12.dp))
                    //.border(2.dp, Color.Gray, shape = RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {

                    val processedTextDescription = if (currentItem.description.length > 20) {
                        currentItem.description.split(" ").chunked(6).joinToString("\n"){ it.joinToString(" ") } // Split into chunks of 10 characters and join with newlines
                    } else {
                        currentItem.description
                    }

                    Text(
                        text = currentItem.title,
                        style = MaterialTheme.typography.headlineMedium,
                        color = currentItem.textColor,
                        textAlign = TextAlign.Start,
                    )
                    Text(
                        text = processedTextDescription,
                        style = MaterialTheme.typography.bodyMedium,
                        color = currentItem.textColor,
                        textAlign = TextAlign.Start,
                    )
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = {
                                if (currentStep > 0) {
                                    currentStep--
                                } else {
                                    onFinish()
                                }
                            },
                            enabled = currentStep > 0
                        ) {
                            Text(text = "Prev")
                        }
                        Spacer(modifier=Modifier.width(width = 50.dp))
                        Button(
                            onClick = {
                                if (currentStep < items.size - 1) {
                                    currentStep++
                                } else {
                                    onFinish()
                                }
                            }
                        ) {
                            Text(text = if (currentStep < items.size - 1) "Next" else "Finish")
                        }
                    }
                }
            }
        }
    }
}


data class WalkthroughItem(
    val title: String,
    val description: String,
    var itemCenter: Offset,
    val textColor: Color = Color.Black,
    val cardColor: Color = Color.Cyan
)
