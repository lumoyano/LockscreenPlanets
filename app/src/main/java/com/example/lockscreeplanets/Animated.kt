package com.example.lockscreeplanets

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

// Animated canvas area
@Composable
fun AnimatedCanvas(
    modifier: Modifier = Modifier,
    selectedCircle: String = "Circle #1",
    selectedColor: String = "Red",
    selectedSize: String = "M",
) {
    // Map color and size to actual values
    fun mapColor(color: String) = when (color) {
        "Red" -> Color.Red
        "Green" -> Color.Green
        "Blue" -> Color.Blue
        "Yellow" -> Color.Yellow
        else -> Color.Gray
    }
    fun mapRadius(size: String, density: Float): Float = when (size) {
        "S" -> 20f * density
        "M" -> 30f * density
        "L" -> 45f * density
        else -> 30f * density
    }

    val density = LocalDensity.current
    val densityFactor = with(density) { 1.dp.toPx() }

    // Animation state
    var angleFast by remember { mutableStateOf(0f) }
    var angleSlow by remember { mutableStateOf(0f) }

    LaunchedEffect(Unit) {
        while (true) {
            angleFast += 0.0006f
            angleSlow += 0.001f
            delay(16L)
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .aspectRatio(9f / 16f)
                .fillMaxWidth()
                .background(Color.Black)
        ) {
            val c = center
            val baseOrbit = min(size.width, size.height) / 3f

            val posFast = Offset(
                x = c.x + cos(angleFast) * baseOrbit,
                y = c.y + sin(angleFast) * baseOrbit
            )
            val posSlow = Offset(
                x = c.x + cos(angleSlow) * baseOrbit * 0.6f,
                y = c.y + sin(angleSlow) * baseOrbit * 0.6f
            )

            // Defaults
            var centerColor: Color? = Color.Gray
            var centerRadius = 30f * densityFactor

            var fastColor: Color? = Color.Cyan
            var fastRadius = 24f * densityFactor

            var slowColor: Color? = Color.Magenta
            var slowRadius = 15f * densityFactor

            // Apply user selection - needs to be revisited because it rebuilds on click lol
            when (selectedCircle) {
                "Circle #1" -> {
                    centerColor = if (selectedColor == "None") null else mapColor(selectedColor)
                    centerRadius = mapRadius(selectedSize, densityFactor)
                }
                "Circle #2" -> {
                    fastColor = if (selectedColor == "None") null else mapColor(selectedColor)
                    fastRadius = mapRadius(selectedSize, densityFactor)
                }
                "Circle #3" -> {
                    slowColor = if (selectedColor == "None") null else mapColor(selectedColor)
                    slowRadius = mapRadius(selectedSize, densityFactor)
                }
            }

            // Draw only if not null
            centerColor?.let { drawCircle(color = it, radius = centerRadius, center = c) }
            fastColor?.let { drawCircle(color = it, radius = fastRadius, center = posFast) }
            slowColor?.let { drawCircle(color = it, radius = slowRadius, center = posSlow) }
        }
    }
}

//
@Composable
fun WidgetControls(
    modifier: Modifier = Modifier,
    onSelectionChange: (String, String, String) -> Unit = { _, _, _ -> }
) {
    var selectedCircle by remember { mutableStateOf("Circle #1") }
    var circleExpanded by remember { mutableStateOf(false) }

    var selectedColor by remember { mutableStateOf("Red") }
    var colorExpanded by remember { mutableStateOf(false) }

    var selectedSize by remember { mutableStateOf("M") }
    var sizeExpanded by remember { mutableStateOf(false) }

    // propagate values upward
    LaunchedEffect(selectedCircle, selectedColor, selectedSize) {
        onSelectionChange(selectedCircle, selectedColor, selectedSize)
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(14.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            // Circle selector dropdown
            Box {
                Button(onClick = { circleExpanded = true }) {
                    Text(selectedCircle)
                }
                DropdownMenu(
                    expanded = circleExpanded,
                    onDismissRequest = { circleExpanded = false }
                ) {
                    listOf("Circle #1", "Circle #2", "Circle #3").forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                selectedCircle = option
                                circleExpanded = false
                            }
                        )
                    }
                }
            }

            // Color selector dropdown
            Box {
                Button(onClick = { colorExpanded = true }) {
                    Text(selectedColor)
                }
                DropdownMenu(
                    expanded = colorExpanded,
                    onDismissRequest = { colorExpanded = false }
                ) {
                    listOf("Red", "Green", "Blue", "Yellow", "None").forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                selectedColor = option
                                colorExpanded = false
                            }
                        )
                    }
                }
            }

            // Size selector dropdown
            Box {
                Button(onClick = { sizeExpanded = true }) {
                    Text("Size: $selectedSize")
                }
                DropdownMenu(
                    expanded = sizeExpanded,
                    onDismissRequest = { sizeExpanded = false }
                ) {
                    listOf("S", "M", "L").forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                selectedSize = option
                                sizeExpanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}

// ===================== SCREEN LAYOUT ======================
@Composable
fun AnimatedScreen() {
    var selectedCircle by remember { mutableStateOf("Circle #1") }
    var selectedColor by remember { mutableStateOf("Red") }
    var selectedSize by remember { mutableStateOf("M") }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // top area takes ~70% using weight
        AnimatedCanvas(
            modifier = Modifier
                .weight(0.7f)
                .fillMaxWidth(),
            selectedCircle = selectedCircle,
            selectedColor = selectedColor,
            selectedSize = selectedSize
        )

        Box(
            modifier = Modifier
                .weight(0.3f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            WidgetControls { circle, color, size ->
                selectedCircle = circle
                selectedColor = color
                selectedSize = size
            }
        }
    }
}

// ===================== PREVIEWS ======================
@Preview(showBackground = true)
@Composable
fun CanvasPreview() {
    AnimatedCanvas()
}

@Preview(showBackground = true)
@Composable
fun ControlsPreview() {
    WidgetControls()
}

@Preview(showBackground = true)
@Composable
fun FullScreenPreview() {
    AnimatedScreen()
}
