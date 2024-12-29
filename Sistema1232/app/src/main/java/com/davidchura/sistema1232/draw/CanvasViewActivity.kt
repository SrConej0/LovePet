package com.davidchura.sistema1232.draw

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

class CanvasViewActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val dataPoints = intArrayOf(
            intent.getIntExtra("canvasNumero1", 0),
            intent.getIntExtra("canvasNumero2", 0),
            intent.getIntExtra("canvasNumero3", 0),
            intent.getIntExtra("canvasNumero4", 0),
            intent.getIntExtra("canvasNumero5", 0),
            intent.getIntExtra("canvasNumero6", 0)
        )

        val canvasNombre = intent.getStringExtra("canvasNombre") ?: "Canvas"

        setContent {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    TopAppBar(
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color(generateRandomColor()),
                            titleContentColor = Color.White
                        ),
                        title = { Text(canvasNombre) },
                        navigationIcon = {
                            IconButton(onClick = { finish() }) {
                                Icon(
                                    Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Volver",
                                    tint = Color.White
                                )
                            }
                        }
                    )
                }
            ) { paddingValues ->
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                ) {
                    ChartContainer(dataPoints)

                    Spacer(modifier = Modifier.height(32.dp))

                    Text(
                        text = "Análisis del Mes",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }

    @Composable
    fun ChartContainer(dataPoints: IntArray) {
        val vibrateColors = listOf(
            Color(0xFFFF6B6B),  // Rojo coral
            Color(0xFF4ECDC4),  // Turquesa
            Color(0xFF45B7D1),  // Azul claro
            Color(0xFFF7D794),  // Amarillo pastel
            Color(0xFF9055A2),  // Púrpura
            Color(0xFFFF8ED4)   // Rosa pastel
        )

        val total = dataPoints.sum().toFloat()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentAlignment = Alignment.Center
            ) {
                DonutChart(dataPoints, vibrateColors, total)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp) // Aumenta la altura a 500.dp
            ) {
                BarChart(dataPoints, vibrateColors, total)
            }
        }
    }

    @Composable
    fun DonutChart(dataPoints: IntArray, colors: List<Color>, total: Float) {
        val animationProgress = remember { Animatable(0f) }

        LaunchedEffect(Unit) {
            animationProgress.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 2000)
            )
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.size(250.dp)) {
                val strokeWidth = size.width / 6
                val radius = size.width / 2 - strokeWidth / 2
                val gapSize = 2f

                var startAngle = -90f
                dataPoints.forEachIndexed { index, value ->
                    val sweepAngle = (value / total) * 360f * animationProgress.value

                    drawArc(
                        color = colors[index % colors.size],
                        startAngle = startAngle + gapSize / 2,
                        sweepAngle = sweepAngle - gapSize,
                        useCenter = false,
                        topLeft = Offset(strokeWidth / 2, strokeWidth / 2),
                        size = Size(size.width - strokeWidth, size.height - strokeWidth),
                        style = Stroke(width = strokeWidth, cap = StrokeCap.Round, join = StrokeJoin.Round)
                    )
                    startAngle += sweepAngle
                }
            }
        }
    }

    @Composable
    fun BarChart(dataPoints: IntArray, colors: List<Color>, total: Float) {
        val animationProgress = remember { Animatable(0f) }

        LaunchedEffect(Unit) {
            animationProgress.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 2000)
            )
        }

        Canvas(modifier = Modifier.fillMaxSize()) {
            val chartWidth = size.width
            val chartHeight = size.height
            val barWidth = chartWidth / (dataPoints.size * 2)
            val spaceBetweenBars = barWidth
            val labels = listOf(
                "Semana 1", "Semana 2", "Semana 3",
                "Semana 4", "Semana 5", "Semana 6"
            )

            dataPoints.forEachIndexed { index, value ->
                val barHeight = (value / total) * chartHeight * 1.5f * animationProgress.value
                val barLeft = index * (barWidth + spaceBetweenBars) + spaceBetweenBars / 2

                // Dibujar la barra
                drawRect(
                    color = colors[index % colors.size].copy(alpha = 0.7f),
                    topLeft = Offset(barLeft, chartHeight - barHeight - 120),
                    size = Size(barWidth, barHeight)
                )

                val percentage = (value / total * 100).toInt()
                drawContext.canvas.nativeCanvas.drawText(
                    "$percentage%",
                    barLeft + barWidth / 2,
                    chartHeight - barHeight - 130,
                    android.graphics.Paint().apply {
                        color = android.graphics.Color.BLACK
                        textSize = 30f
                        textAlign = android.graphics.Paint.Align.CENTER
                    }
                )

                // Rotar el texto de las semanas
                drawContext.canvas.save()
                drawContext.canvas.rotate(-90f, barLeft + barWidth / 2, chartHeight - 20)
                drawContext.canvas.nativeCanvas.drawText(
                    labels.getOrElse(index) { "Semana ${index + 1}" },
                    barLeft + barWidth / 2,
                    chartHeight - 10,
                    android.graphics.Paint().apply {
                        color = android.graphics.Color.BLACK
                        textSize = 30f
                        textAlign = android.graphics.Paint.Align.CENTER
                    }
                )
                drawContext.canvas.restore()
            }
        }
    }

    private fun generateRandomColor(): Int {
        val random = Random
        return android.graphics.Color.rgb(
            random.nextInt(256),
            random.nextInt(256),
            random.nextInt(256)
        )
    }
}