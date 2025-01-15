package com.tribe7.statecraft.nudge

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun CoachMark(
    items: List<CoachMarkItem>,
    modifier: Modifier = Modifier.fillMaxSize(),
    onFinish: () -> Unit
) {
    var currentStep by remember { mutableStateOf(0) }

    if (currentStep >= items.size) {
        onFinish()
        return
    }

    val currentItem = items[currentStep]
    val adjustedOffset = currentItem.circleCenter.copy(
        x = currentItem.circleCenter.x + 50,
        y = currentItem.circleCenter.y + 50
    )

    Box(modifier = modifier) {

        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                center = adjustedOffset,
                color = currentItem.circleColor.copy(alpha = 1f),
                radius = currentItem.radius.dp.toPx(),
                style = Stroke(width = currentItem.borderThickness)
            )
            drawCircle(
                center = adjustedOffset,
                color = currentItem.circleColor.copy(alpha = 0.4f),
                radius = with(density) { ((currentItem.radius + 152).dp.toPx()) },
                style = Stroke(width = 150f)
            )
        }

        val density = LocalDensity.current
        val configuration = LocalConfiguration.current
        val screenHeightPx = with(density) { configuration.screenHeightDp.dp.toPx() }
        val screenWidthPx = with(density) { configuration.screenWidthDp.dp.toPx() }

        val screenMiddleVertically = with(density) { screenHeightPx / 2 }
        val screenMiddleHorizontal = with(density) { screenWidthPx / 2 }

        val isTop = currentItem.circleCenter.y < screenMiddleVertically
        val isLeft = currentItem.circleCenter.x < screenMiddleHorizontal

        val nearbyThreshold = 200f
        val isNearbyMiddleVertically = currentItem.circleCenter.y in (screenMiddleVertically - nearbyThreshold)..(screenMiddleVertically + nearbyThreshold)
        val isNearbyMiddleHorizontal = currentItem.circleCenter.x in (screenMiddleHorizontal - nearbyThreshold)..(screenMiddleHorizontal + nearbyThreshold)
        val isNearbyMiddle = isNearbyMiddleVertically && isNearbyMiddleHorizontal


        var xOffset = with(density) { (currentItem.circleCenter.x + -100).toDp() }
        var yOffset = with(density) { (currentItem.circleCenter.y + 50).toDp() }

        if(!isTop){ //bottom
            yOffset = with(density) { (currentItem.circleCenter.y - 600).toDp() }
        }

        if(!isLeft){ //right
            xOffset = with(density) { (currentItem.circleCenter.x - 800).toDp() }
        }

        if(isNearbyMiddleVertically){
            yOffset = with(density) { (currentItem.circleCenter.y + 200).toDp() }
        }

        if(isNearbyMiddleHorizontal){
            xOffset = with(density) { (currentItem.circleCenter.x - 300).toDp() }
            if(isTop) {
                yOffset = with(density) { (currentItem.circleCenter.y + 200).toDp() }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .offset(x = xOffset, y = yOffset),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column (
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){

                val processedTextDescription = if (currentItem.description.length > 20) {
                    currentItem.description.split(" ").chunked(6).joinToString("\n"){ it.joinToString(" ") } // Split into chunks of 10 characters and join with newlines
                } else {
                    currentItem.description
                }

                Text(
                    text = currentItem.title,
                    style = MaterialTheme.typography.headlineMedium,
                    color = currentItem.textColor,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = processedTextDescription,
                    style = MaterialTheme.typography.bodyMedium,
                    color = currentItem.textColor,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(){
                    Button(
                        onClick = {
                            if (currentStep > 0 && currentStep < items.size ) {
                                currentStep--
                            } else {
                                onFinish()
                            }
                        },
                    ) {
                        Text(
                            text = if (currentStep > 0) "Prev" else "-",
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            if (currentStep < items.size - 1) {
                                currentStep++
                            } else {
                                onFinish()
                            }
                        },
                    ) {
                        Text(
                            text = if (currentStep < items.size - 1) "Next" else "Finish",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

        }
    }
}

data class CoachMarkItem(
    val title: String,
    val description: String,
    var circleCenter: Offset,
    val radius: Float = 200f,
    val borderThickness: Float = 700f,
    val textColor: Color = Color.Black,
    val circleColor: Color = Color.Cyan
)
